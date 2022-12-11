package explorewithme.event.info;

import explorewithme.event.Event;
import explorewithme.event.dto.EventFullDto;
import explorewithme.event.dto.EventMapper;
import explorewithme.event.dto.EventShortDto;
import explorewithme.event.repository.EventRepository;
import explorewithme.exceptions.InsufficientRightsException;
import explorewithme.exceptions.notfound.EventNotFoundException;
import explorewithme.exceptions.notfound.UserNotFoundException;
import explorewithme.likes.Like;
import explorewithme.likes.LikeMapper;
import explorewithme.likes.LikeRepository;
import explorewithme.request.RequestRepository;
import explorewithme.request.dto.RequestStatus;
import explorewithme.user.User;
import explorewithme.user.repository.UserRepository;
import explorewithme.utility.Rating;
import explorewithme.utility.RatingMapper;
import explorewithme.utility.interaction.ClientImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventInfoServiceImpl implements EventInfoService {

    private final EventRepository repository;

    private final UserRepository userRepository;

    private final RequestRepository requestRepository;

    private final LikeRepository likeRepository;

    private final ClientImpl client;

    @Override
    public List<EventShortDto> getEvents(String text,
                                         List<Long> categories,
                                         Boolean paid,
                                         LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                         Boolean onlyAvailable,
                                         String sort,
                                         Integer size, Integer from) {
        List<Event> events = repository.infoFindEventsBy(text, categories, paid,
                                                         rangeStart, rangeEnd, onlyAvailable, sort, size, from);
        log.info("info, get events by params");
        List<EventShortDto> result = EventMapper.toListEventShortDto(events);
        for (EventShortDto event: result) {
            event.setConfirmedRequests(requestRepository.countByEventIsAndStatusIs(event.getId(), RequestStatus.CONFIRMED));
        }
        result = client.addViewsEventList(result);
        if (sort != null) {
            result = result.stream()
                    .sorted(Comparator.comparing(EventShortDto::getViews)
                    .reversed()).collect(Collectors.toList());
        }
        return result;
    }

    @Override
    public EventFullDto getById(Long eventId) {
        Event event = repository.findById(eventId)
                .orElseThrow(EventNotFoundException::new);
        log.info("info, get event by id  {}", eventId);
        EventFullDto result = EventMapper.toEventFullDto(event);
        Long requests = requestRepository.countByEventIsAndStatusIs(eventId, RequestStatus.CONFIRMED);
        log.info("requests {}", requests);
        result.setConfirmedRequests(requests);
        result.setViews(client.addViewsEvent(event));
        if (event.getRatingVisibility()) {
            Long rating = LikeMapper.getRating(event.getLikes());
            result.setRating(rating);
        }
        return result;
    }

    @Override
    public List<EventFullDto> getTopEvents() {
        List<Object[]> topEvents = repository.topEvents();
        List<Rating> ratings = RatingMapper.mapRating(topEvents);
        log.info("top events {}", topEvents);
        log.info("ratings {}", ratings);
        List<EventFullDto> result = toEventDtoWithRating(ratings);
        log.info("result {}", result);
        return result.stream().sorted(Comparator.comparing(EventFullDto::getRating).reversed()).collect(Collectors.toList());
    }

    @Override
    public void likeEvent(Long liker, Long liked, Boolean type) {
        log.info("service, like from user {} to event {}, type {}", liker, liked, type);
        User userLiker = userRepository.findById(liker)
                .orElseThrow(UserNotFoundException::new);
        Event eventLiked = repository.findById(liked)
                .orElseThrow(EventNotFoundException::new);

        Set<Like> likes = eventLiked.getLikes();
        Optional<Like> findLike = likes.stream().filter(o -> o.getLiker().getId().equals(liker)).findFirst();
        if (findLike.isPresent()) {
            if (findLike.get().getType().equals(type)) {
                throw new InsufficientRightsException("like already exists");
            } else  {
                Like newTypeLike = findLike.get();
                newTypeLike.setType(type);
                likes.remove(findLike.get());
                likes.add(newTypeLike);
            }
        } else {
            Boolean checkLike = requestRepository.existsByEventInAndRequesterIsAndStatusIs(List.of(liked), liker, RequestStatus.CONFIRMED);
            /*if (!checkLike) {
                throw new InsufficientRightsException("can't like/dislike user without users event participations");
            }*/
            Like like = LikeMapper.newLike(userLiker, type);
            Like resultLike = likeRepository.save(like);
            likes.add(resultLike);
        }

        eventLiked.setLikes(likes);
        repository.save(eventLiked);
        Set<Like> check = repository.findById(eventLiked.getId())
                .orElseThrow(UserNotFoundException::new).getLikes();
        log.info("check {}, {}", check.size(), check);
    }

    @Override
    public void removeLike(Long liker, Long liked) {
        log.info("service, remove like from user {} to event {}", liker, liked);
        userRepository.findById(liker)
                .orElseThrow(UserNotFoundException::new);
        Event eventLiked = repository.findById(liked)
                .orElseThrow(EventNotFoundException::new);

        Set<Like> likes = eventLiked.getLikes();
        Optional<Like> like = likes.stream().filter(o -> o.getLiker().getId().equals(liker)).findFirst();
        like.ifPresent(likes::remove);
        like.ifPresent(likeRepository::delete);
        repository.save(eventLiked);
    }

    private List<EventFullDto> toEventDtoWithRating(List<Rating> ratings) {
        List<Long> ids = ratings.stream().map(Rating::getId).collect(Collectors.toList());
        List<Event> events = repository.findByIdIn(ids);
        List<EventFullDto> result = new ArrayList<>();
        for (Event event: events) {
            for (Rating rating: ratings) {
                if (event.getId().equals(rating.getId())) {
                    EventFullDto fullDto = EventMapper.toEventFullDto(event);
                    if (event.getRatingVisibility()) {
                        fullDto.setRating(rating.getRating());
                    }
                    result.add(fullDto);
                }
            }
        }
        return result;
    }

}
