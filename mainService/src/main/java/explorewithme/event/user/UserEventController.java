package explorewithme.event.user;

import explorewithme.event.dto.EventFullDto;
import explorewithme.event.dto.EventShortDto;
import explorewithme.event.dto.NewEventDto;
import explorewithme.event.dto.UpdateEventRequest;
import explorewithme.request.dto.ParticipationRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/users/{userId}/events")
@RequiredArgsConstructor
public class UserEventController {

    private final UserEventService service;

    @GetMapping
    public List<EventShortDto> getEvents(@PathVariable Long userId,
                                         @RequestParam(defaultValue = "0") Integer from,
                                         @RequestParam(defaultValue = "20") Integer size) {
        log.info("private, get events, userId {}", userId);
        return service.getEvents(userId, from, size);
    }

    @PatchMapping
    public EventFullDto updateEvent(@RequestBody UpdateEventRequest updateEventRequest,
                                    @PathVariable Long userId) {
        log.info("private, update event {}, userId {}", updateEventRequest.getEventId(), userId);
        return service.updateEvent(userId, updateEventRequest);
    }

    @PostMapping
    public EventFullDto addEvent(@RequestBody @Valid NewEventDto newEventDto,
                                 @PathVariable Long userId) {
        log.info("private, add event {}, userId {}", newEventDto, userId);
        return service.addEvent(userId, newEventDto);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getFullInfoById(@PathVariable Long userId,
                                        @PathVariable Long eventId) {
        log.info("private, get full event info {}, userId {}", eventId, userId);
        return service.getById(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto cancelEvent(@PathVariable Long userId,
                                    @PathVariable Long eventId) {
        log.info("private, cancel event {}, userId {}", eventId, userId);
        return service.cancelEvent(userId, eventId);
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getRequests(@PathVariable Long userId,
                                                     @PathVariable Long eventId) {
        log.info("private, get requests for event {}, userId {}", eventId, userId);
        return service.getRequests(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmRequest(@PathVariable Long userId,
                                                  @PathVariable Long eventId,
                                                  @PathVariable Long reqId) {
        log.info("private, confirm request {} for event {}, userId {}", reqId, eventId, userId);
        return service.confirmRequest(userId, eventId, reqId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectRequest(@PathVariable Long userId,
                                                 @PathVariable Long eventId,
                                                 @PathVariable Long reqId) {
        log.info("private, reject request {} for ru.event {}, userId {}", reqId, eventId, userId);
        return service.rejectRequest(userId, eventId, reqId);
    }
}

