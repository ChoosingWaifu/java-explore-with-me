package explorewithme.event.info;

import explorewithme.event.dto.EventFullDto;
import explorewithme.event.dto.EventShortDto;
import explorewithme.event.interaction.EventClient;
import explorewithme.event.interaction.NewHit;
import explorewithme.utility.DateTimeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/events")
@RequiredArgsConstructor
public class EventInfoController {

    private final EventInfoService service;

    private final EventClient client;

    @Value("${app.name}")
    private String appName;

    @GetMapping
    public List<EventShortDto> getEvents(HttpServletRequest servletRequest,
                                         @RequestParam(required = false) String text,
                                         @RequestParam(required = false) List<Long> categories,
                                         @RequestParam(required = false) Boolean paid,
                                         @RequestParam(required = false) String rangeStart, @RequestParam(required = false) String rangeEnd,
                                         @RequestParam(required = false) Boolean onlyAvailable,
                                         @RequestParam(required = false) String sort,
                                         @RequestParam(defaultValue = "0") Integer size, @RequestParam(defaultValue = "20") Integer from) {
        log.info("public, get");
        String uri = servletRequest.getRequestURI();
        String ip = servletRequest.getRemoteAddr();
        client.sendHitToStats(new NewHit(appName, uri, ip));
        return service.getEvents(text, categories, paid, DateTimeMapper.toLocalDateTime(rangeStart), DateTimeMapper.toLocalDateTime(rangeEnd), onlyAvailable, sort, size, from);
    }

    @GetMapping("/{id}")
    public EventFullDto getEventById(@PathVariable Long id, HttpServletRequest servletRequest) {
        log.info("public, get event by Id {}", id);
        String uri = servletRequest.getRequestURI();
        String ip = servletRequest.getRemoteAddr();
        client.sendHitToStats(new NewHit(appName, uri, ip));
        return service.getById(id);
    }

}
