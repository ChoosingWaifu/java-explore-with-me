package explorewithme;

import explorewithme.dto.HitDto;
import explorewithme.dto.NewHit;
import explorewithme.dto.ViewStats;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@Slf4j
@RequestMapping
@RequiredArgsConstructor
public class StatsController {

    private final StatsService service;

    @PostMapping("/hit")
    public HitDto addHit(@RequestBody NewHit newHit) {
        log.info("controller, add hit {}", newHit);
        return service.addHit(newHit);
    }

    @GetMapping("/stats")
    public ResponseEntity<Object> getStats(@RequestParam String start,
                                           @RequestParam String end,
                                           @RequestParam(required = false) List<String> uris,
                                           @RequestParam(defaultValue = "false") Boolean unique
                                          ) {
        log.info("controller, get stats {}, {}, {}, {}", uris, unique, start, end);
        String startDecoded = URLDecoder.decode(start, StandardCharsets.UTF_8);
        String endDecoded = URLDecoder.decode(start, StandardCharsets.UTF_8);
        List<ViewStats> viewStatsList = service.getStats(uris, unique, startDecoded, endDecoded);
        return new ResponseEntity<>(viewStatsList, HttpStatus.OK);
    }
}
