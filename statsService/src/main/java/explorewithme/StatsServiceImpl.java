package explorewithme;

import explorewithme.dto.HitDto;
import explorewithme.dto.Mapper;
import explorewithme.dto.NewHit;
import explorewithme.dto.ViewStats;
import explorewithme.model.Hit;
import explorewithme.repository.HitRepository;
import explorewithme.utility.DateTimeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final HitRepository repository;

    @Override
    public HitDto addHit(NewHit newHit) {
        Hit hit = Mapper.toHit(newHit);
        return Mapper.toHitDto(repository.save(hit));
    }

    @Override
    public List<ViewStats> getStats(List<String> uris,
                                    Boolean unique,
                                    String start,
                                    String end) {
        log.info("stats enter param {}, {}, {}, {}", start, end, uris, unique);
        if (uris.size() != 0) {
            String[] eventsUris = uris.get(0).split(" ");
            uris = List.of(eventsUris);
        }
        start = URLDecoder.decode(start, StandardCharsets.UTF_8);
        end = URLDecoder.decode(end, StandardCharsets.UTF_8);
        LocalDateTime startTime = DateTimeMapper.toLocalDateTime(start);
        LocalDateTime endTime = DateTimeMapper.toLocalDateTime(end);
        List<ViewStats> result;
        if (uris.size() != 0) {
            if (unique) {
                result = repository.getWithUrisUnique(startTime, endTime, uris);
            } else {
                result = repository.getWithUris(startTime, endTime, uris);
            }
        } else {
            if (unique) {
                result = repository.getWithoutUrisUnique(startTime, endTime);
            } else {
                result = repository.getWithoutUris(startTime, endTime);
            }
        }
        log.info("result {}", result);
        return result;
    }
}
