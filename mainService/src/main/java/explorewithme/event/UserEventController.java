package explorewithme.event;

import explorewithme.event.dto.EventFullDto;
import explorewithme.event.dto.EventShortDto;
import explorewithme.event.dto.NewEventDto;
import explorewithme.event.dto.UpdateEventRequest;
import explorewithme.request.dto.ParticipationRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/users/{userId}/events")
@RequiredArgsConstructor
public class UserEventController {

    @GetMapping
    public List<EventShortDto> getEvents(@PathVariable Integer userId) {
        log.info("private, get events, userId {}", userId);
        return null;
    }

    @PatchMapping
    public EventFullDto updateEvent(@RequestBody UpdateEventRequest updateEventRequest,
                                     @PathVariable Integer userId) {
        log.info("private, update ru.event {}, userId {}", updateEventRequest.getEventId(), userId);
        return null;
    }

    @PostMapping
    public EventFullDto addEvent(@RequestBody NewEventDto newEventDto,
                                  @PathVariable Integer userId) {
        log.info("private, add ru.event {}, userId {}", newEventDto, userId);
        return null;
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEventFullInfoById(@PathVariable Integer userId,
                                             @PathVariable Integer eventId) {
        log.info("private, get full ru.event info {}, userId {}", eventId, userId);
        return null;
    }

    @PatchMapping("/{eventId}")
    public EventFullDto cancelEvent(@PathVariable Integer userId,
                                    @PathVariable Integer eventId) {
        log.info("private, cancel ru.event {}, userId {}", eventId, userId);
        return null;
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getEventRequests(@PathVariable Integer userId,
                                                          @PathVariable Integer eventId) {
        log.info("private, get requests for ru.event {}, userId {}", eventId, userId);
        return null;
    }

    @PatchMapping("/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmRequest(@PathVariable Integer userId,
                                                        @PathVariable Integer eventId,
                                                        @PathVariable Integer reqId) {
        log.info("private, confirm ru.request {} for ru.event {}, userId {}", reqId, eventId, userId);
        return null;
    }

    @PatchMapping("/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectRequest(@PathVariable Integer userId,
                                                       @PathVariable Integer eventId,
                                                       @PathVariable String reqId) {
        log.info("private, reject ru.request {} for ru.event {}, userId {}", reqId, eventId, userId);
        return null;
    }
}

