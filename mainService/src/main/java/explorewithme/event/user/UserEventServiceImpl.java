package explorewithme.event.user;

import explorewithme.category.Category;
import explorewithme.category.CategoryRepository;
import explorewithme.event.Event;
import explorewithme.event.interaction.EventClient;
import explorewithme.event.repository.EventRepository;
import explorewithme.event.dto.*;
import explorewithme.exceptions.InsufficientRightsException;
import explorewithme.exceptions.notfound.*;
import explorewithme.pagination.PageFromRequest;
import explorewithme.request.ParticipationRequest;
import explorewithme.request.RequestRepository;
import explorewithme.request.dto.ParticipationRequestDto;
import explorewithme.request.dto.RequestMapper;
import explorewithme.request.dto.RequestStatus;
import explorewithme.user.User;
import explorewithme.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserEventServiceImpl implements UserEventService {

    private final EventRepository repository;

    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;

    private final RequestRepository requestRepository;

    private final EventClient client;

    @Override
    @Transactional(readOnly = true)
    public List<EventShortDto> getEvents(Long userId, Integer from, Integer size) {
        Pageable pageable = PageFromRequest.of(from, size);
        log.info("private service, get events {}, {}, {}", userId, size, from);
        List<EventShortDto> result = EventMapper.toListEventShortDto(repository.findByInitiatorIdIs(userId, pageable));
        for (EventShortDto event: result) {
            event.setConfirmedRequests(requestRepository.countByEventIsAndStatusIs(event.getId(), RequestStatus.CONFIRMED));
        }
        return result;
    }

    @Override
    public EventFullDto addEvent(Long userId, NewEventDto dto) {
        Event event = EventMapper.newEvent(dto);
        if (event.getEventDate().minusHours(2).isBefore(LocalDateTime.now())) {
            throw new InsufficientRightsException("invalid date, need at least 2h before event date");
        }
        Category category = categoryRepository.findById(dto.getCategory())
                .orElseThrow(CategoryNotFoundException::new);
        event.setCategory(category);
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        event.setInitiator(user);
        log.info("private service, add event {}", event);
        return EventMapper.toEventFullDto(repository.save(event));
    }

    @Override
    @Transactional
    public EventFullDto updateEvent(Long userId, UpdateEventRequest updateRequest) {
        Event event = repository.findById(updateRequest.getEventId())
                .orElseThrow(EventNotFoundException::new);
        if (!event.getInitiator().getId().equals(userId)) {
            throw new InsufficientRightsException("can't patch other's events");
        }
        if (event.getEventDate().minusHours(2).isBefore(LocalDateTime.now())) {
            throw new InsufficientRightsException("too late to patch event info (2h)");
        }
        if (event.getState().equals(EventState.PUBLISHED)) {
            throw new InsufficientRightsException("can't patch published events");
        }
        Event result = EventMapper.updateEvent(event, updateRequest);
        if (event.getState().equals(EventState.CANCELED)) {
            result.setState(EventState.PENDING);
        }
        Long categoryId = updateRequest.getCategory() == null ? event.getCategory().getId() : updateRequest.getCategory();
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(CategoryNotFoundException::new);
        result.setCategory(category);
        log.info("private service, update event with {}", updateRequest);
        return EventMapper.toEventFullDto(repository.save(result));
    }

    @Override
    public EventFullDto getById(Long userId, Long eventId) {
        Event event = repository.findById(eventId)
                .orElseThrow(EventNotFoundException::new);
        log.info("private service, get by id {}", eventId);
        EventFullDto result = EventMapper.toEventFullDto(event);
        result.setConfirmedRequests(requestRepository.countByEventIsAndStatusIs(eventId, RequestStatus.CONFIRMED));
        result.setViews(client.addViews(event));
        return result;
    }

    @Override
    public EventFullDto cancelEvent(Long userId, Long eventId) {
        Event event = repository.findById(eventId)
                .orElseThrow(EventNotFoundException::new);
        if (!event.getInitiator().getId().equals(userId)) {
            throw new InsufficientRightsException("can't cancel other's events");
        }
        if (!event.getState().equals(EventState.PENDING)) {
            throw new InsufficientRightsException("only events in pending state could be cancelled");
        }
        event.setState(EventState.CANCELED);
        repository.save(event);
        log.info("private service, cancel event {}", eventId);
        return EventMapper.toEventFullDto(event);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParticipationRequestDto> getRequests(Long userId, Long eventId) {
        List<ParticipationRequest> requests = requestRepository.findByEventIs(eventId);
        log.info("private service, get requests {}", eventId);
        return RequestMapper.toRequestDtoList(requests);
    }

    @Override
    public ParticipationRequestDto confirmRequest(Long userId, Long eventId, Long requestId) {
        Event event = repository.findById(eventId)
                .orElseThrow(EventNotFoundException::new);
        ParticipationRequest request = requestRepository.findById(requestId)
                .orElseThrow(RequestNotFoundException::new);
        request.setStatus(RequestStatus.CONFIRMED);
        if (event.getParticipantLimit() <= requestRepository.findByEventIs(eventId).size()) {
            throw new InsufficientRightsException("too many requests to confirm");
        }
        requestRepository.save(request);
        log.info("private service, confirm request {}", requestId);
        return RequestMapper.toRequestDto(request);
    }

    @Override
    public ParticipationRequestDto rejectRequest(Long userId, Long eventId, Long requestId) {
        ParticipationRequest request = requestRepository.findById(requestId)
                .orElseThrow(RequestNotFoundException::new);
        request.setStatus(RequestStatus.REJECTED);
        requestRepository.save(request);
        log.info("private service, reject request {}", requestId);
        return RequestMapper.toRequestDto(request);
    }
}
