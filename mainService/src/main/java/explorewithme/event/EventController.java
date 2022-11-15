package explorewithme.event;

import explorewithme.event.dto.AdminUpdateEventRequest;
import explorewithme.event.dto.EventFullDto;
import explorewithme.user.dto.UserShortDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/admin/events")
@RequiredArgsConstructor
public class EventController {

    @GetMapping
    public List<EventFullDto> getEvents() {
        log.info("get events");
        return null;
    }

    @PutMapping("/{eventId}")
    public UserShortDto updateEvent(@RequestBody AdminUpdateEventRequest updateEventRequest,
                                    @PathVariable Integer eventId) {
        log.info("update ru.event {} with {}", eventId, updateEventRequest);
        return null;
    }

    @PatchMapping("/{eventId}/publish")
    public EventFullDto publishEvent(@PathVariable Integer eventId) {
        log.info("publish ru.event {}", eventId);
        return null;
    }

    @PatchMapping("/{eventId}/reject")
    public EventFullDto rejectEvent(@PathVariable Integer eventId) {
        log.info("publish ru.event {}", eventId);
        return null;
    }
}
