package explorewithme.event.dto;

import explorewithme.event.Event;
import explorewithme.utility.DateTimeMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EventMapper {

    public static Event newEvent(NewEventDto dto, Long userId) {
        Boolean paid = dto.getPaid() != null && dto.getPaid();
        Long participantLimit = dto.getParticipantLimit() == null ? 0 : dto.getParticipantLimit();
        Boolean requestModeration = dto.getRequestModeration() != null && dto.getRequestModeration();
        return new Event(
                null,
                dto.getAnnotation(),
                dto.getCategory(),
                null,
                null,
                dto.getDescription(),
                DateTimeMapper.toLocalDateTime(dto.getEventDate()),
                userId,
                dto.getLocation().getLat(),
                dto.getLocation().getLon(),
                paid,
                participantLimit,
                null,
                requestModeration,
                EventState.PENDING,
                dto.getTitle(),
                null
        );
    }

    public static Event updateEvent(Event event, UpdateEventRequest request) {
        Long category = request.getCategory() == null ? event.getCategory() : request.getCategory();
        String annotation = request.getAnnotation() == null ? event.getAnnotation() : request.getAnnotation();
        String description = request.getDescription() == null ? event.getDescription() : request.getDescription();
        LocalDateTime eventDate = request.getEventDate() == null ? event.getEventDate() : DateTimeMapper.toLocalDateTime(request.getEventDate());
        Boolean paid = request.getPaid() == null ? event.getPaid() : request.getPaid();
        Long participantLimit = request.getParticipantLimit() == null ? event.getParticipantLimit() : request.getParticipantLimit();
        String title = request.getTitle() == null ? event.getTitle() : request.getTitle();
        return new Event(
                event.getId(),
                annotation,
                category,
                event.getConfirmedRequests(),
                event.getCreatedOn(),
                description,
                eventDate,
                event.getInitiator(),
                event.getLat(),
                event.getLon(),
                paid,
                participantLimit,
                event.getPublishedOn(),
                event.getRequestModeration(),
                event.getState(),
                title,
                event.getCompilations()
        );
    }

    public static Event adminUpdateEvent(Event event, AdminUpdateEventRequest request) {
        String annotation = request.getAnnotation() == null ? event.getAnnotation() : request.getAnnotation();
        Long category = request.getCategory() == null ? event.getCategory() : request.getCategory();
        String description = request.getDescription() == null ? event.getDescription() : request.getDescription();
        LocalDateTime eventDate = request.getEventDate() == null ? event.getEventDate() : DateTimeMapper.toLocalDateTime(request.getEventDate());
        Float lat = request.getLocation().getLat() == null ? event.getLat() : request.getLocation().getLat();
        Float lon = request.getLocation().getLon() == null ? event.getLon() : request.getLocation().getLon();
        Boolean paid = request.getPaid() == null ? event.getPaid() : request.getPaid();
        Long participantLimit = request.getParticipantLimit() == null ? event.getParticipantLimit() : request.getParticipantLimit();
        Boolean requestModeration = request.getRequestModeration() == null ? event.getRequestModeration() : request.getRequestModeration();
        String title = request.getTitle() == null ? event.getTitle() : request.getTitle();
        return new Event(
                event.getId(),
                annotation,
                category,
                event.getConfirmedRequests(),
                event.getCreatedOn(),
                description,
                eventDate,
                event.getInitiator(),
                lat,
                lon,
                paid,
                participantLimit,
                event.getPublishedOn(),
                requestModeration,
                event.getState(),
                title,
                event.getCompilations()
        );
    }

    public static EventShortDto toEventShortDto(Event event) {
        return new EventShortDto(


        );
    }

    public static List<EventShortDto> toListEventShortDto(Set<Event> events) {
        return events.stream().map(EventMapper::toEventShortDto).collect(Collectors.toList());
    }

    public static List<EventShortDto> toListEventShortDto(List<Event> events) {
        return events.stream().map(EventMapper::toEventShortDto).collect(Collectors.toList());
    }

}
