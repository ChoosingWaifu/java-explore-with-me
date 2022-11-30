package explorewithme.repository;

import explorewithme.dto.ViewStats;
import explorewithme.model.Hit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<Hit, Long> {

    @Query("select new explorewithme.dto.ViewStats(hit.app, hit.uri, count(distinct hit.ip))"
            + " from Hit hit"
            + " where hit.timestamp between ?1 and ?2"
            + " and hit.uri in (?3)"
            + " group by hit.app, hit.uri")
    List<ViewStats> getWithUrisUnique(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("select new explorewithme.dto.ViewStats(hit.app, hit.uri, count(hit.ip))"
            + " from Hit hit"
            + " where hit.timestamp between ?1 and ?2"
            + " and hit.uri in (?3)"
            + " group by hit.app, hit.uri")
    List<ViewStats> getWithUris(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("select new explorewithme.dto.ViewStats(hit.app, hit.uri, count(distinct hit.ip))"
            + " from Hit hit"
            + " where hit.timestamp between ?1 and ?2"
            + " group by hit.app, hit.uri")
    List<ViewStats> getWithoutUrisUnique(LocalDateTime start, LocalDateTime end);

    @Query("select new explorewithme.dto.ViewStats(hit.app, hit.uri, count(hit.ip))"
            + " from Hit hit"
            + " where hit.timestamp between ?1 and ?2"
            + " group by hit.app, hit.uri")
    List<ViewStats> getWithoutUris(LocalDateTime start, LocalDateTime end);


}