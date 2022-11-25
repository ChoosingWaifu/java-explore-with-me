package explorewithme.request;

import explorewithme.request.dto.ParticipationRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/users/{userId}/requests")
@RequiredArgsConstructor
public class UserRequestController {

    private final RequestService service;

    @GetMapping
    public List<ParticipationRequestDto> getRequests(@PathVariable Long userId) {
        log.info("private, get requests, userId {}", userId);
        return service.getRequests(userId);
    }

    @PostMapping
    public ParticipationRequestDto addRequest(@PathVariable Long userId,
                                              @RequestParam Long requestId) {
        log.info("private, add request {}, userId {}", requestId, userId);
        return service.addRequest(userId, requestId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable Long userId,
                                                 @PathVariable Long requestId) {
        log.info("private, cancel request {}, userId {}", requestId, userId);
        return service.cancelRequest(userId, requestId);
    }

}

