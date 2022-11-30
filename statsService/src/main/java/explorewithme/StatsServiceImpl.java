package explorewithme;

import explorewithme.dto.HitDto;
import explorewithme.dto.Mapper;
import explorewithme.dto.NewHit;
import explorewithme.dto.ViewStats;
import explorewithme.model.Hit;
import explorewithme.repository.StatsRepository;
import explorewithme.utility.DateTimeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final StatsRepository repository;

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
     /*   log.info("start coded on stats {}", start);
        start = URLDecoder.decode(start, StandardCharsets.UTF_8);
        log.info("start decoded {}", start);
        end = URLDecoder.decode(end, StandardCharsets.UTF_8);*/
        log.info("start {}", start);
        LocalDateTime startTime = DateTimeMapper.toLocalDateTime(start);
        LocalDateTime endTime = DateTimeMapper.toLocalDateTime(end);
        List<ViewStats> result;
        if (uris != null) {
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
        return result;
    }
}
