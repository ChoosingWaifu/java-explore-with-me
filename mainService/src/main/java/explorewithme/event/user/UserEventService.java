package explorewithme.event.user;

import explorewithme.event.dto.EventFullDto;
import explorewithme.event.dto.EventShortDto;
import explorewithme.event.dto.NewEventDto;
import explorewithme.event.dto.UpdateEventRequest;
import explorewithme.request.dto.ParticipationRequestDto;

import java.util.List;

public interface UserEventService {

    List<EventShortDto> getEvents(Long userId, Integer from, Integer size);

    EventFullDto addEvent(Long userId, NewEventDto dto);

    EventFullDto updateEvent(Long userId, UpdateEventRequest updateRequest);

    EventFullDto getById(Long userId, Long eventId);

    EventFullDto cancelEvent(Long userId, Long eventId);

    List<ParticipationRequestDto> getRequests(Long userId, Long eventId);

    ParticipationRequestDto confirmRequest(Long userId, Long eventId, Long requestId);

    ParticipationRequestDto rejectRequest(Long userId, Long eventId, Long requestId);

    Boolean changeRatingVisibility(Long userId, Long eventId);

}
