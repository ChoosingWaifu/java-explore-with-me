package explorewithme.event.admin;

import explorewithme.category.Category;
import explorewithme.category.CategoryRepository;
import explorewithme.event.Event;
import explorewithme.event.dto.AdminUpdateEventRequest;
import explorewithme.event.dto.EventFullDto;
import explorewithme.event.dto.EventMapper;
import explorewithme.event.dto.EventState;
import explorewithme.event.repository.EventRepository;
import explorewithme.exceptions.InsufficientRightsException;
import explorewithme.exceptions.notfound.CategoryNotFoundException;
import explorewithme.exceptions.notfound.EventNotFoundException;
import explorewithme.request.RequestRepository;
import explorewithme.request.dto.RequestStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository repository;

    private final RequestRepository requestRepository;

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<EventFullDto> getEvents(List<Long> users,
                                        List<String> states,
                                        List<Long> categories,
                                        LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                        Integer size, Integer from) {
        log.info("admin service, get events {}, {}, {}, {}, {}, {}, {}", users, states, categories, rangeStart, rangeEnd, size, from);
        List<EventFullDto> result;
        if (users != null) {
            log.info("find by initiator id in {}", users);
            result = EventMapper.toListEventFullDto(repository.findByInitiatorIdIn(users));
        } else {
            log.info("admin find events by params");
            result = EventMapper.toListEventFullDto(repository.adminFindEventsBy(states, categories, rangeStart, rangeEnd, size, from));
        }
        for (EventFullDto event: result) {
            event.setConfirmedRequests(requestRepository.countByEventIsAndStatusIs(event.getId(), RequestStatus.CONFIRMED));
        }
        return result;
    }

    @Override
    @Transactional
    public EventFullDto updateEvent(Long eventId, AdminUpdateEventRequest updateRequest) {
        Event event = repository.findById(eventId)
                .orElseThrow(EventNotFoundException::new);
        if (event.getState().equals(EventState.CANCELED)) {
            throw new InsufficientRightsException("event is canceled");
        }
        Event result = EventMapper.adminUpdateEvent(event, updateRequest);
        Long categoryId = updateRequest.getCategory() == null ? event.getCategory().getId() : updateRequest.getCategory();
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(CategoryNotFoundException::new);
        result.setCategory(category);
        log.info("update event {}", result);
        return EventMapper.toEventFullDto(repository.save(result));
    }

    @Override
    public EventFullDto publishEvent(Long eventId) {
        Event event = repository.findById(eventId)
                .orElseThrow(EventNotFoundException::new);
        if (!event.getState().equals(EventState.PENDING)) {
            throw new InsufficientRightsException("event must be in pending state");
        }
        if (event.getEventDate().minusHours(1).isBefore(LocalDateTime.now())) {
            throw new InsufficientRightsException("too late to publish");
        }
        event.setState(EventState.PUBLISHED);
        event.setPublishedOn(LocalDateTime.now());
        log.info("publish event {}", event);
        return EventMapper.toEventFullDto(repository.save(event));
    }

    @Override
    public EventFullDto rejectEvent(Long eventId) {
        Event event = repository.findById(eventId)
                .orElseThrow(EventNotFoundException::new);
        if (!event.getState().equals(EventState.PENDING)) {
            throw new InsufficientRightsException("event must be in pending state");
        }
        event.setState(EventState.CANCELED);
        log.info("reject event {}", event);
        return EventMapper.toEventFullDto(repository.save(event));
    }

}
