package explorewithme.event.admin;

import explorewithme.event.dto.AdminUpdateEventRequest;
import explorewithme.event.dto.EventFullDto;
import explorewithme.utility.DateTimeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/admin/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService service;

    @GetMapping
    public List<EventFullDto> getEvents(@RequestParam(required = false) List<Long> users,
                                        @RequestParam(required = false) List<String> states,
                                        @RequestParam(required = false) List<Long> categories,
                                        @RequestParam(required = false) String rangeStart,
                                        @RequestParam(required = false) String rangeEnd,
                                        @RequestParam(defaultValue = "0") Integer from,
                                        @RequestParam(defaultValue = "20") Integer size) {
        log.info("get events");
        return service.getEvents(users, states, categories, DateTimeMapper.toLocalDateTime(rangeStart), DateTimeMapper.toLocalDateTime(rangeEnd), from, size);
    }

    @PutMapping("/{eventId}")
    public EventFullDto updateEvent(@RequestBody AdminUpdateEventRequest updateEventRequest,
                                    @PathVariable Long eventId) {
        log.info("update event {} with {}", eventId, updateEventRequest);
        return service.updateEvent(eventId, updateEventRequest);
    }

    @PatchMapping("/{eventId}/publish")
    public EventFullDto publishEvent(@PathVariable Long eventId) {
        log.info("publish event {}", eventId);
        return service.publishEvent(eventId);
    }

    @PatchMapping("/{eventId}/reject")
    public EventFullDto rejectEvent(@PathVariable Long eventId) {
        log.info("publish event {}", eventId);
        return service.rejectEvent(eventId);
    }
}
