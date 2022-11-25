package explorewithme.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long>, CustomEventRepository {

    @Override
    Page<Event> infoFindEventsBy(String text,
                                 List<Long> categories,
                                 Boolean paid,
                                 LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                 Boolean onlyAvailable,
                                 String sort,
                                 Integer size, Integer from);

    List<Event> findByCategoryIs(Long category);

    List<Event> findByInitiatorIs(Long userId, Pageable pageable);

    List<Event> findByIdIn(@Param("ids") List<Long> ids);
}
