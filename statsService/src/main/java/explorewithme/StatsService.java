package explorewithme;

import explorewithme.dto.HitDto;
import explorewithme.dto.NewHit;
import explorewithme.dto.ViewStats;

import java.util.List;

public interface StatsService {

    HitDto addHit(NewHit newHit);

    List<ViewStats> getStats(List<String> uris,
                             Boolean unique,
                             String start,
                             String end);

}
