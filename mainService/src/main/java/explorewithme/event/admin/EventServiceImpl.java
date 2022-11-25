package explorewithme.event.admin;

import explorewithme.event.Event;
import explorewithme.event.EventRepository;
import explorewithme.event.dto.*;
import explorewithme.exceptions.InsufficientRightsException;
import explorewithme.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository repository;

    private final RepoEventMapper mapper;

    @Override
    public List<EventFullDto> getEvents(List<Long> users,
                                        List<String> states,
                                        List<Long> categories,
                                        LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                        Integer size, Integer from) {
        if (users != null) {
            return mapper.toListEventFullDto(repository.findByIdIn(users));
        }
        return mapper.toListEventFullDto(repository.privateFindEventsBy(states, categories, rangeStart, rangeEnd, size, from)
                .stream().collect(Collectors.toList()));
    }

    @Override
    public EventFullDto updateEvent(Long eventId, AdminUpdateEventRequest updateRequest) {
        Event event = repository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("event not found"));
        if (event.getState().equals(EventState.CANCELED)) {
            throw new InsufficientRightsException("event is canceled");
        }
        Event result = EventMapper.adminUpdateEvent(event, updateRequest);
        return mapper.toEventFullDto(repository.save(result));
    }

    @Override
    public EventFullDto publishEvent(Long eventId) {
        Event event = repository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("event not found"));
        if (!event.getState().equals(EventState.PENDING)) {
            throw new InsufficientRightsException("event must be in pending state");
        }
        if (event.getEventDate().minusHours(1).isBefore(LocalDateTime.now())) {
            throw new InsufficientRightsException("too late to publish");
        }
        event.setState(EventState.PUBLISHED);
        event.setPublishedOn(LocalDateTime.now());
        return mapper.toEventFullDto(repository.save(event));
    }

    @Override
    public EventFullDto rejectEvent(Long eventId) {
        Event event = repository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("event not found"));
        if (!event.getState().equals(EventState.PENDING)) {
            throw new InsufficientRightsException("event must be in pending state");
        }
        event.setState(EventState.REJECTED);
        return mapper.toEventFullDto(repository.save(event));
    }
}
