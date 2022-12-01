package explorewithme.event.info;

import explorewithme.event.Event;
import explorewithme.event.dto.EventFullDto;
import explorewithme.event.dto.EventMapper;
import explorewithme.event.dto.EventShortDto;
import explorewithme.event.interaction.EventClient;
import explorewithme.event.repository.EventRepository;
import explorewithme.exceptions.NotFoundException;
import explorewithme.request.RequestRepository;
import explorewithme.request.dto.RequestStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventInfoServiceImpl implements EventInfoService {

    private final EventRepository repository;

    private final RequestRepository requestRepository;

    private final EventClient client;

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
        List<EventShortDto> result = EventMapper.toListEventShortDto(events);
        for (EventShortDto event: result) {
            event.setConfirmedRequests(requestRepository.countByEventIsAndStatusIs(event.getId(), RequestStatus.CONFIRMED));
        }
        result = client.addViewsList(result);
        if (sort != null) {
            result = result.stream()
                    .sorted(Comparator.comparing(EventShortDto::getViews)
                    .reversed()).collect(Collectors.toList());
        }
        return result;
    }

    @Override
    public EventFullDto getById(Long eventId) {
        Event event = repository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("event not found"));
        log.info("info, get event by id  {}", eventId);
        EventFullDto result = EventMapper.toEventFullDto(event);
        Long requests = requestRepository.countByEventIsAndStatusIs(eventId, RequestStatus.CONFIRMED);
        log.info("requests {}", requests);
        result.setConfirmedRequests(requests);
        result.setViews(client.addViews(event));
        return result;
    }

}
