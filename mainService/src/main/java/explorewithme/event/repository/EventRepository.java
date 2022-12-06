package explorewithme.event.repository;

import explorewithme.event.Event;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long>, CustomEventRepository {

    @Override
    List<Event> infoFindEventsBy(String text,
                                 List<Long> categories,
                                 Boolean paid,
                                 LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                 Boolean onlyAvailable,
                                 String sort,
                                 Integer size, Integer from);

    @Override
    List<Event> adminFindEventsBy(List<String> states,
                                  List<Long> categories,
                                  LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                  Integer size, Integer from);

    List<Event> findByCategoryIdIs(Long categoryId);

    List<Event> findByInitiatorIdIs(Long userId, Pageable pageable);

    List<Event> findByInitiatorIdIs(Long userId);

    List<Event> findByInitiatorIdIn(@Param("ids") List<Long> ids);

    List<Event> findByIdIn(@Param("ids") List<Long> ids);
}
