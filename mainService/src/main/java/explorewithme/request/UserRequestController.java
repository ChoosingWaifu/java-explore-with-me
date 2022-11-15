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

    @GetMapping
    public List<ParticipationRequestDto> getRequests(@PathVariable Integer userId) {
        log.info("private, get requests, userId {}", userId);
        return null;
    }

    @PatchMapping
    public ParticipationRequestDto addRequest(@RequestParam Integer requestId,
                                              @PathVariable Integer userId) {
        log.info("private, add ru.request {}, userId {}", requestId, userId);
        return null;
    }

    @PostMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable Integer userId,
                                                 @PathVariable Integer requestId) {
        log.info("private, cancel ru.request {}, userId {}", requestId, userId);
        return null;
    }

}

