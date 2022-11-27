package explorewithme.event.repository;

import explorewithme.event.Event;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CustomEventRepository {

    List<Event> infoFindEventsBy(String text,
                                 List<Long> categories,
                                 Boolean paid,
                                 LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                 Boolean onlyAvailable,
                                 String sort,
                                 Integer size, Integer from);

    List<Event> adminFindEventsBy(List<String> states,
                                    List<Long> categories,
                                    LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                    Integer size, Integer from);
}
