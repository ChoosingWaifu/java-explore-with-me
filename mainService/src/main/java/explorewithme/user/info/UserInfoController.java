package explorewithme.user.info;

import explorewithme.event.interaction.EventClient;
import explorewithme.event.interaction.NewHit;
import explorewithme.user.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/info/users")
@RequiredArgsConstructor
public class UserInfoController {

    private final EventClient client;

    private final UserInfoService service;

    @Value("${app.name}")
    private String appName;

    @GetMapping
    public List<UserInfoDto> topUsers(HttpServletRequest httpRequest,
                                      Integer from,
                                      Integer size) {
        log.info("controller, get top users {}, {}", from, size);
        String uri = httpRequest.getRequestURI();
        String ip = httpRequest.getRemoteAddr();
        client.sendHitToStats(new NewHit(appName, uri, ip));
        return service.getTopUsers(from, size);
    }

    @GetMapping("/{userId}")
    public UserInfoDto getUserInfo(HttpServletRequest httpRequest,
                                   @PathVariable Long userId) {
        log.info("controller, get user info {}", userId);
        String uri = httpRequest.getRequestURI();
        String ip = httpRequest.getRemoteAddr();
        client.sendHitToStats(new NewHit(appName, uri, ip));
        return service.getUserInfo(userId);
    }

    @PatchMapping("/{userId}")
    public void likeUser(@RequestHeader("X-Explorer-User-Id") Long liker,
                         @PathVariable Long userId,
                         @RequestParam Boolean type) {
        log.info("controller users, user {} liked {}, like {}", liker, userId, type);
        service.likeUser(liker, userId, type);
    }

    @DeleteMapping("/{userId}")
    public void removeLike(@RequestHeader("X-Explorer-User-Id") Long liker,
                           @PathVariable Long userId) {
        log.info("controller users, user {} removed like from {}", liker, userId);
        service.removeLike(liker, userId);
    }

}
