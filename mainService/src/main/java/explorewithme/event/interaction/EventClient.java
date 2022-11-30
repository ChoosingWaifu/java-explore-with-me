package explorewithme.event.interaction;

import explorewithme.event.Event;
import explorewithme.event.dto.EventFullDto;
import explorewithme.event.dto.EventShortDto;
import explorewithme.utility.BaseClient;
import explorewithme.utility.DateTimeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class EventClient extends BaseClient {

    @Autowired
    public EventClient(@Value("${ewm-stats.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build()
        );
    }
    public void sendHitToStats(NewHit newHit) {
       post("/hit", null, null, newHit);
    }

    public ResponseEntity<Object> getStats(String start, String end, List<String> uris, Boolean unique) {
        log.info("start {}", start);
        log.info("end {}", end);
        String startEncoded = URLEncoder.encode(start, StandardCharsets.UTF_8);
        String endEncoded = URLEncoder.encode(start, StandardCharsets.UTF_8);
        Map<String, Object> parameters = Map.of(
                "start", startEncoded,
                "end", endEncoded,
                "uris", uris,
                "unique", unique
        );
        return get("/stats" + "?start={start}&end={end}&uris={uris}&unique={unique}",null, parameters);
    }

    public Long getViews(Event event) {
        Long eventId = event.getId();
        LocalDateTime start = event.getPublishedOn() == null ? event.getCreatedOn() : event.getPublishedOn();
        ResponseEntity<Object> response = getStats(
                start.format(DateTimeMapper.format()),
                LocalDateTime.now().format(DateTimeMapper.format()),
                List.of("/events/" + eventId),
                true);
        log.info("response get body");
        List<ViewStats> stats = (List<ViewStats>) response.getBody();
        log.info("stats {}", stats);
        if (stats != null && stats.size() != 0) {
            return stats.get(0).getHits();
        }
        return 0L;
    }

    public List<EventFullDto> addViews(List<EventFullDto> events) {
        List<String> uris = new ArrayList<>();
        for (EventFullDto event : events) {
            uris.add("/events/" + event.getId());
        }
        ResponseEntity<Object> response = getStats(
                LocalDateTime.now().minusYears(3).format(DateTimeMapper.format()),
                LocalDateTime.now().format(DateTimeMapper.format()),
                uris,
                true);
        if (response.getStatusCode().is2xxSuccessful()) {
            List<ViewStats> stats;
            stats = (List<ViewStats>) response.getBody();
            for (EventFullDto event : events) {
                assert stats != null;
                for (ViewStats stat : stats) {
                    String check = "/events/" + event.getId();
                    if (check.equals(stat.getUri())) {
                        event.setViews(stat.getHits());
                    }
                }
            }
        }
        return events;
    }

    public List<EventShortDto> addViewsShort(List<EventShortDto> events) {
        List<String> uris = new ArrayList<>();
        for (EventShortDto event : events) {
            uris.add("/events/" + event.getId());
        }
        ResponseEntity<Object> response = getStats(
                LocalDateTime.now().minusYears(3).format(DateTimeMapper.format()),
                LocalDateTime.now().format(DateTimeMapper.format()),
                uris,
                true);
        if (response.getStatusCode().is2xxSuccessful()) {
            List<ViewStats> stats;
            stats = (List<ViewStats>) response.getBody();
            for (EventShortDto event : events) {
                assert stats != null;
                for (ViewStats stat : stats) {
                    String check = "/events/" + event.getId();
                    if (check.equals(stat.getUri())) {
                        event.setViews(stat.getHits());
                    }
                }
            }
        }
        return events;
    }
}




