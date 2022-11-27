package explorewithme.event.info;

import explorewithme.event.Event;
import explorewithme.event.dto.EventFullDto;
import explorewithme.event.dto.EventMapper;
import explorewithme.event.repository.EventRepository;
import explorewithme.event.dto.EventShortDto;
import explorewithme.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventInfoServiceImpl implements EventInfoService {

    private final EventRepository repository;

    @Override
    public List<EventShortDto> getEvents(String text,
                                         List<Long> categories,
                                         Boolean paid,
                                         LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                         Boolean onlyAvailable,
                                         String sort,
                                         Integer size, Integer from) {
        List<Event> events = repository.infoFindEventsBy(text, categories, paid,
                                                         rangeStart, rangeEnd, onlyAvailable, sort, size, from);
        log.info("info, get events by params");
        return EventMapper.toListEventShortDto(events);
    }

    @Override
    public EventFullDto getById(Long eventId) {
        Event event = repository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("event not found"));
        log.info("info, get event by id  {}", eventId);
        return EventMapper.toEventFullDto(event);
    }
}
