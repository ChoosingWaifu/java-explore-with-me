package explorewithme.event;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CustomEventRepository {

    Page<Event> infoFindEventsBy(String text,
                                 List<Long> categories,
                                 Boolean paid,
                                 LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                 Boolean onlyAvailable,
                                 String sort,
                                 Integer size, Integer from);

    Page<Event> privateFindEventsBy(List<String> states,
                                    List<Long> categories,
                                    LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                    Integer size, Integer from);
}
