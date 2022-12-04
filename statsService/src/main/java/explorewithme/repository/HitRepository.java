package explorewithme.repository;

import explorewithme.dto.ViewStats;
import explorewithme.model.Hit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface HitRepository extends JpaRepository<Hit, Long> {

    @Query("select new explorewithme.dto.ViewStats(hit.app, hit.uri, count(distinct hit.ip))"
            + " from Hit hit"
            + " where hit.timestamp between :start and :end"
            + " and hit.uri in :uris"
            + " group by hit.app, hit.uri")
    List<ViewStats> getWithUrisUnique(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("uris") List<String> uris);

    @Query("select new explorewithme.dto.ViewStats(hit.app, hit.uri, count(hit.ip))"
            + " from Hit hit"
            + " where hit.timestamp between :start and :end"
            + " and hit.uri in :uris"
            + " group by hit.app, hit.uri")
    List<ViewStats> getWithUris(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("uris") List<String> uris);

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