package explorewithme.event.info;

import explorewithme.event.Event;
import explorewithme.event.EventRepository;
import explorewithme.event.dto.EventShortDto;
import explorewithme.event.dto.RepoEventMapper;
import explorewithme.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventInfoServiceImpl implements EventInfoService {

    private final EventRepository repository;

    private final RepoEventMapper mapper;

    @Override
    public List<EventShortDto> getEvents(String text,
                                         List<Long> categories,
                                         Boolean paid,
                                         LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                         Boolean onlyAvailable,
                                         String sort,
                                         Integer size, Integer from) {
        Page<Event> events = repository.infoFindEventsBy(text, categories, paid,
                                                         rangeStart, rangeEnd, onlyAvailable, sort, size, from);
        return mapper.toListEventShortDto(events.stream().collect(Collectors.toList()));
    }

    @Override
    public EventShortDto getById(Long eventId) {
        Event event = repository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("event not found"));
        return mapper.toEventShortDto(event);
    }
}
