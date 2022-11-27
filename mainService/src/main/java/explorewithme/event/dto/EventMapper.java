package explorewithme.event.dto;

import explorewithme.category.dto.CategoryDto;
import explorewithme.category.dto.CategoryMapper;
import explorewithme.event.Event;
import explorewithme.event.dto.location.Location;
import explorewithme.user.dto.UserMapper;
import explorewithme.user.dto.UserShortDto;
import explorewithme.utility.DateTimeMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EventMapper {

    public static EventFullDto toEventFullDto(Event event) {
        UserShortDto userDto = UserMapper.toUserShortDto(event.getInitiator());
        CategoryDto categoryDto = CategoryMapper.toCategoryDto(event.getCategory());
        String publishedOn = event.getPublishedOn() == null ? null : event.getPublishedOn().format(DateTimeMapper.format());
        Long views = null;
        return new EventFullDto(
                event.getAnnotation(),
                categoryDto,
                event.getConfirmedRequests(),
                event.getCreatedOn().format(DateTimeMapper.format()),
                event.getDescription(),
                event.getEventDate().format(DateTimeMapper.format()),
                event.getId(),
                userDto,
                new Location(event.getLat(), event.getLon()),
                event.getPaid(),
                event.getParticipantLimit(),
                publishedOn,
                event.getRequestModeration(),
                event.getState(),
                event.getTitle(),
                views
        );
    }

    public static EventShortDto toEventShortDto(Event event) {
        UserShortDto userDto = UserMapper.toUserShortDto(event.getInitiator());
        CategoryDto categoryDto = CategoryMapper.toCategoryDto(event.getCategory());
        Long views = null;
        return new EventShortDto(
                event.getAnnotation(),
                categoryDto,
                event.getConfirmedRequests(),
                event.getEventDate().format(DateTimeMapper.format()),
                event.getId(),
                userDto,
                event.getPaid(),
                event.getTitle(),
                views
        );
    }

    public static List<EventFullDto> toListEventFullDto(List<Event> events) {
        return events.stream().map(EventMapper::toEventFullDto).collect(Collectors.toList());
    }

    public static List<EventShortDto> toListEventShortDto(List<Event> events) {
        return events.stream().map(EventMapper::toEventShortDto).collect(Collectors.toList());
    }

    public static List<EventShortDto> toListEventShortDto(Set<Event> events) {
        return events.stream().map(EventMapper::toEventShortDto).collect(Collectors.toList());
    }


    public static Event newEvent(NewEventDto dto) {
        Boolean paid = dto.getPaid() != null && dto.getPaid();
        Long participantLimit = dto.getParticipantLimit() == null ? 0 : dto.getParticipantLimit();
        Boolean requestModeration = dto.getRequestModeration() != null && dto.getRequestModeration();
        return new Event(
                null,
                dto.getAnnotation(),
                null,
                null,
                null,
                dto.getDescription(),
                DateTimeMapper.toLocalDateTime(dto.getEventDate()),
                null,
                dto.getLocation().getLat(),
                dto.getLocation().getLon(),
                paid,
                participantLimit,
                null,
                requestModeration,
                EventState.PENDING,
                dto.getTitle()
        );
    }

    public static Event updateEvent(Event event, UpdateEventRequest request) {
        String annotation = request.getAnnotation() == null ? event.getAnnotation() : request.getAnnotation();
        String description = request.getDescription() == null ? event.getDescription() : request.getDescription();
        LocalDateTime eventDate = request.getEventDate() == null ? event.getEventDate() : DateTimeMapper.toLocalDateTime(request.getEventDate());
        Boolean paid = request.getPaid() == null ? event.getPaid() : request.getPaid();
        Long participantLimit = request.getParticipantLimit() == null ? event.getParticipantLimit() : request.getParticipantLimit();
        String title = request.getTitle() == null ? event.getTitle() : request.getTitle();
        return new Event(
                event.getId(),
                annotation,
                null,
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
                title
        );
    }

    public static Event adminUpdateEvent(Event event, AdminUpdateEventRequest request) {
        Float lat;
        Float lon;
        LocalDateTime eventDate = request.getEventDate() == null ? event.getEventDate() : DateTimeMapper.toLocalDateTime(request.getEventDate());
        String annotation = request.getAnnotation() == null ? event.getAnnotation() : request.getAnnotation();
        String description = request.getDescription() == null ? event.getDescription() : request.getDescription();
        if (request.getLocation() == null) {
        lat = event.getLat();
        lon = event.getLon();
        } else {
        lat = request.getLocation().getLat();
        lon = request.getLocation().getLon();
        }
        Boolean paid = request.getPaid() == null ? event.getPaid() : request.getPaid();
        Long participantLimit = request.getParticipantLimit() == null ? event.getParticipantLimit() : request.getParticipantLimit();
        Boolean requestModeration = request.getRequestModeration() == null ? event.getRequestModeration() : request.getRequestModeration();
        String title = request.getTitle() == null ? event.getTitle() : request.getTitle();
        return new Event(
                event.getId(),
                annotation,
                null,
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
                title
        );
    }

}
