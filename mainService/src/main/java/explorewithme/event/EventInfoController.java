package explorewithme.event;

import explorewithme.event.dto.EventShortDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/events")
@RequiredArgsConstructor
public class EventInfoController {

    @GetMapping
    public List<EventShortDto> getEvents() {
        log.info("public, get events");
        return null;
    }

    @GetMapping("/{id}")
    public EventShortDto getEventById(@PathVariable Integer id) {
        log.info("public, get ru.event by Id {}", id);
        return null;
    }

}
