package explorewithme.event.user;

import explorewithme.event.Event;
import explorewithme.event.EventRepository;
import explorewithme.event.dto.*;
import explorewithme.exceptions.NotFoundException;
import explorewithme.pagination.PageFromRequest;
import explorewithme.request.ParticipationRequest;
import explorewithme.request.RequestRepository;
import explorewithme.request.dto.ParticipationRequestDto;
import explorewithme.request.dto.RequestMapper;
import explorewithme.request.dto.RequestStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserEventServiceImpl implements UserEventService {

    private final EventRepository repository;

    private final RequestRepository requestRepository;

    private final RepoEventMapper mapper;

    @Override
    public List<EventShortDto> getEvents(Long userId, Integer from, Integer size) {
        Pageable pageable = PageFromRequest.of(from, size);
        return EventMapper.toListEventShortDto(repository.findByInitiatorIs(userId, pageable));
    }

    @Override
    public EventFullDto addEvent(Long userId, NewEventDto dto) {
        Event event = EventMapper.newEvent(dto, userId);
        log.info("private service, add event {}", event);
        return mapper.toEventFullDto(repository.save(event));
    }

    @Override
    public EventFullDto updateEvent(Long userId, UpdateEventRequest updateRequest) {
        Event event = repository.findById(updateRequest.getEventId())
                .orElseThrow(() -> new NotFoundException("event not found"));
        log.info("user, update event {}, event {}", updateRequest.getEventId(), event);
        Event result = EventMapper.updateEvent(event, updateRequest);
        return mapper.toEventFullDto(repository.save(result));
    }

    @Override
    public EventFullDto getById(Long userId, Long eventId) {
        Event event = repository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("event not found"));
        return mapper.toEventFullDto(event);
    }

    @Override
    public EventFullDto cancelEvent(Long userId, Long eventId) {
        Event event = repository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("event not found"));
        event.setState(EventState.CANCELED);
        repository.save(event);
        return mapper.toEventFullDto(event);
    }

    @Override
    public List<ParticipationRequestDto> getRequests(Long userId, Long eventId) {
        List<ParticipationRequest> requests = requestRepository.findByEventIs(eventId);
        return RequestMapper.toRequestDtoList(requests);
    }

    @Override
    public ParticipationRequestDto confirmRequest(Long userId, Long eventId, Long requestId) {
        ParticipationRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("request not found"));
        request.setStatus(RequestStatus.CONFIRMED);
        requestRepository.save(request);
        return RequestMapper.toRequestDto(request);
    }

    @Override
    public ParticipationRequestDto rejectRequest(Long userId, Long eventId, Long requestId) {
        ParticipationRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("request not found"));
        request.setStatus(RequestStatus.REJECTED);
        requestRepository.save(request);
        return RequestMapper.toRequestDto(request);
    }
}
