package explorewithme.event.dto;

import explorewithme.category.Category;
import explorewithme.category.CategoryRepository;
import explorewithme.category.dto.CategoryDto;
import explorewithme.category.dto.CategoryMapper;
import explorewithme.event.Event;
import explorewithme.event.location.Location;
import explorewithme.exceptions.NotFoundException;
import explorewithme.user.User;
import explorewithme.user.UserRepository;
import explorewithme.user.dto.UserMapper;
import explorewithme.user.dto.UserShortDto;
import explorewithme.utility.DateTimeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RepoEventMapper {

    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;

    public EventFullDto toEventFullDto(Event event) {
        Long views = null;
        User user = userRepository.findById(event.getInitiator())
                .orElseThrow(() -> new NotFoundException("user not found"));
        Category category = categoryRepository.findById(event.getCategory())
                .orElseThrow(() -> new NotFoundException("category not found"));
        UserShortDto userDto = UserMapper.toUserShortDto(user);
        CategoryDto categoryDto = CategoryMapper.toCategoryDto(category);
        String publishedOn = event.getPublishedOn() == null ? null : event.getPublishedOn().format(DateTimeMapper.format());
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

    public EventShortDto toEventShortDto(Event event) {
        Long views = null;
        Category category = categoryRepository.findById(event.getCategory())
                .orElseThrow(() -> new NotFoundException("category not found"));
        User user = userRepository.findById(event.getInitiator())
                .orElseThrow(() -> new NotFoundException("user not found"));
        UserShortDto userDto = UserMapper.toUserShortDto(user);
        CategoryDto categoryDto = CategoryMapper.toCategoryDto(category);
        return new EventShortDto(
                event.getAnnotation(),
                categoryDto,
                event.getConfirmedRequests(),
                event.getEventDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                event.getId(),
                userDto,
                event.getPaid(),
                event.getTitle(),
                views
        );
    }

    public List<EventFullDto> toListEventFullDto(List<Event> events) {
        return events.stream().map(this::toEventFullDto).collect(Collectors.toList());
    }

    public List<EventShortDto> toListEventShortDto(List<Event> events) {
        return events.stream().map(this::toEventShortDto).collect(Collectors.toList());
    }

    public List<EventShortDto> toListEventShortDto(Set<Event> events) {
        return events.stream().map(this::toEventShortDto).collect(Collectors.toList());
    }

}
