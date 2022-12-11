package explorewithme.utility.interaction;

import explorewithme.event.Event;
import explorewithme.event.dto.EventShortDto;
import explorewithme.user.User;
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
import java.util.*;

@Slf4j
@Service
public class ClientImpl extends BaseClient {

    @Autowired
    public ClientImpl(@Value("${ewm-stats.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build()
        );
    }

    public void sendHitToStats(NewHit newHit) {
        ResponseEntity<Object> result = post("/hit", newHit);
        log.info("result {}", result);
    }

    public ResponseEntity<Object> getStats(String start, String end, String uris, Boolean unique) {
        String startEncoded = URLEncoder.encode(start, StandardCharsets.UTF_8);
        String endEncoded = URLEncoder.encode(end, StandardCharsets.UTF_8);
        if (uris == null) {
            Map<String, Object> parameters = Map.of(
                    "start", startEncoded,
                    "end", endEncoded,
                    "unique", unique
            );
            return get("/stats" + "?start={start}&end={end}&unique={unique}", parameters);

        }
        Map<String, Object> parameters = Map.of(
                "start", startEncoded,
                "end", endEncoded,
                "uris", uris,
                "unique", unique
        );
        return get("/stats" + "?start={start}&end={end}&uris={uris}&unique={unique}", parameters);
    }

    public Long addViewsUser(Long userId) {
        ResponseEntity<Object> response = getStats(
                LocalDateTime.now().minusYears(10).format(DateTimeMapper.format()),
                LocalDateTime.now().plusYears(10).format(DateTimeMapper.format()),
                "/info/users/" + userId,
                true);
        Long result = getViewsForObj(response);
        log.info("result {}", result);
        return result;
    }

    public Long addViewsEvent(Event event) {
        Long eventId = event.getId();
        LocalDateTime start = event.getPublishedOn() == null ? event.getCreatedOn() : event.getPublishedOn();
        String end = LocalDateTime.now().plusYears(10).format(DateTimeMapper.format());
        log.info("send {}, {}, {}, {}", start.format(DateTimeMapper.format()), end, eventId, false);
        ResponseEntity<Object> response = getStats(
                start.format(DateTimeMapper.format()),
                end,
                "/events/" + eventId,
                false);
        log.info("response get body");
        return getViewsForObj(response);
    }

    public List<User> addViewsUserList(List<User> users) {
        StringBuilder uris = new StringBuilder();
        for (User user : users) {
            uris.append("/info/users/").append(user.getId()).append(" ");
        }
        Map<String, Long> mapUriHits = mapViewsForList(uris);
        Set<String> urisSet = mapUriHits.keySet();
        for (User user : users) {
            String checkUri = "/info/users/" + user.getId();
            if (urisSet.contains(checkUri)) {
                user.setViews(mapUriHits.get(checkUri));
            } else {
                user.setViews(0L);
            }
        }
        log.info("add views");
        return users;
    }

    public List<EventShortDto> addViewsEventList(List<EventShortDto> events) {
        StringBuilder uris = new StringBuilder();
        for (EventShortDto event : events) {
            uris.append("/events/").append(event.getId()).append(" ");
        }
        log.info("uris {}", uris);
        Map<String, Long> mapUriHits = mapViewsForList(uris);
        Set<String> urisSet = mapUriHits.keySet();
        for (EventShortDto event : events) {
            String checkUri = "/events/" + event.getId();
            if (urisSet.contains(checkUri)) {
                event.setViews(mapUriHits.get(checkUri));
            } else {
                event.setViews(0L);
            }
        }
        log.info("add views");
        return events;
    }

    private Long getViewsForObj(ResponseEntity<Object> response) {
        List<LinkedHashMap<Object, Object>> stats;
        if (response.getStatusCode().is2xxSuccessful()) {
            stats = (List<LinkedHashMap<Object, Object>>) response.getBody();
            log.info("stats {}", stats);
            if (stats != null && stats.size() != 0) {
                return Long.parseLong(String.valueOf(stats.get(0).get("hits")));
            }
        }
        return 0L;
    }

    private Map<String, Long> mapViewsForList(StringBuilder uris) {
        ResponseEntity<Object> response = getStats(
                LocalDateTime.now().minusYears(3).format(DateTimeMapper.format()),
                LocalDateTime.now().plusYears(3).format(DateTimeMapper.format()),
                uris.toString(),
                false);
        List<LinkedHashMap<Object, Object>> stats;
        Map<String, Long> mapUriHits = new HashMap<>();
        if (response.getStatusCode().is2xxSuccessful()) {
            stats = (List<LinkedHashMap<Object, Object>>) response.getBody();
            assert stats != null;
            for (LinkedHashMap<Object, Object> stat : stats) {
                String uri = String.valueOf(stat.get("uri"));
                Long hits = Long.parseLong(String.valueOf(stat.get("hits")));
                mapUriHits.put(uri, hits);
            }
            return mapUriHits;
        }
        throw new RuntimeException("response status code unsuccessful");
    }


}




