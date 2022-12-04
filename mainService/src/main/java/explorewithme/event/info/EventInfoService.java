package explorewithme.event.info;

import explorewithme.event.dto.EventFullDto;
import explorewithme.event.dto.EventShortDto;

import java.time.LocalDateTime;
import java.util.List;

public interface EventInfoService {

    List<EventShortDto> getEvents(String text,
                                  List<Long> categories,
                                  Boolean paid,
                                  LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                  Boolean onlyAvailable,
                                  String sort,
                                  Integer size, Integer from);

    EventFullDto getById(Long eventId);
}
