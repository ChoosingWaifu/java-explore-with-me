package explorewithme.user.info;

import explorewithme.event.Event;
import explorewithme.event.dto.EventState;
import explorewithme.event.repository.EventRepository;
import explorewithme.exceptions.InsufficientRightsException;
import explorewithme.exceptions.notfound.UserNotFoundException;
import explorewithme.likes.Like;
import explorewithme.likes.LikeMapper;
import explorewithme.likes.LikeRepository;
import explorewithme.request.RequestRepository;
import explorewithme.request.dto.RequestStatus;
import explorewithme.user.User;
import explorewithme.user.dto.UserInfoDto;
import explorewithme.user.dto.UserMapper;
import explorewithme.user.repository.UserRepository;
import explorewithme.utility.Rating;
import explorewithme.utility.RatingMapper;
import explorewithme.utility.interaction.ClientImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final UserRepository userRepository;

    private final EventRepository eventRepository;

    private final LikeRepository likeRepository;

    private final RequestRepository requestRepository;

    private final ClientImpl client;

    @Override
    public UserInfoDto getUserInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        UserInfoDto result = UserMapper.toUserInfoDto(user);

        List<Event> events = eventRepository.findByInitiatorIdIs(userId);
        result.setEvents(new HashSet<>(events));

        Long rating = LikeMapper.getRating(user.getLikes());
        result.setRating(rating);
        Long views = client.addViewsUser(userId);
        result.setViews(views);
        return result;
    }

    @Override
    public List<UserInfoDto> getTopUsers() {
        log.info("service, get top Users");
        List<Object[]> topUsers = userRepository.topUsers();
        List<Rating> ratings = RatingMapper.mapRating(topUsers);
        log.info("top Users size {}", topUsers.size());
        log.info("ratings {}", ratings);
        return toUserInfoList(ratings);
    }

    @Override
    public void likeUser(Long liker, Long liked, Boolean type) {
        log.info("service, like from user {} to user {}, type {}", liker, liked, type);
        User userLiker = userRepository.findById(liker)
                .orElseThrow(UserNotFoundException::new);
        User userLiked = userRepository.findById(liked)
                .orElseThrow(UserNotFoundException::new);
        Set<Like> likes = userLiked.getLikes();
        Optional<Like> findLike = likes.stream().filter(o -> o.getLiker().getId().equals(liker)).findFirst();
        if (findLike.isPresent()) {
            if (findLike.get().getType().equals(type)) {
                log.info("like with the same type exists");
                throw new InsufficientRightsException("like already exists");
            } else  {
                log.info("like with another type exists");
                Like newTypeLike = findLike.get();
                newTypeLike.setType(type);
                likes.remove(findLike.get());
                likes.add(newTypeLike);
            }
        } else {
            log.info("first like");
            List<Event> events = eventRepository.adminFindEventsBy(List.of(EventState.PUBLISHED.toString()),
                    null,
                    null,
                    null,
                    0, 1000);
            log.info("events {}", events.size());
            List<Long> eventsIds = events.stream().map(Event::getId).collect(Collectors.toList());
            Boolean checkLike = requestRepository.existsByEventInAndRequesterIsAndStatusIs(eventsIds, liker, RequestStatus.CONFIRMED);
            log.info("check like {}", checkLike);
            /*if (!checkLike) {
                throw new InsufficientRightsException("can't like/dislike user without users event participations");
            }*/
            log.info("check like not thrown");
            Like like = LikeMapper.newLike(userLiker, type);
            Like resultLike = likeRepository.save(like);
            likes.add(resultLike);
        }

        userLiked.setLikes(likes);
        userRepository.save(userLiked);
        Set<Like> check = userRepository.findById(userLiked.getId())
                .orElseThrow(UserNotFoundException::new).getLikes();
        log.info("check {}, {}", check.size(), check);
    }

    @Override
    public void removeLike(Long liker, Long liked) {
        log.info("service, remove like from user {} to user {}", liker, liked);
        userRepository.findById(liker)
                .orElseThrow(UserNotFoundException::new);
        User userLiked = userRepository.findById(liked)
                .orElseThrow(UserNotFoundException::new);

        Set<Like> likes = userLiked.getLikes();
        Optional<Like> like = likes.stream().filter(o -> o.getLiker().getId().equals(liker)).findFirst();
        log.info("likes size {}", likes.size());
        like.ifPresent(likes::remove);
        like.ifPresent(likeRepository::delete);
        log.info("likes size {}", likes.size());
        userRepository.save(userLiked);
    }

    private List<UserInfoDto> toUserInfoList(List<Rating> uRatings) {
        List<Long> ids = uRatings.stream().map(Rating::getId).collect(Collectors.toList());
        List<User> users = userRepository.findByIdIn(ids);
        client.addViewsUserList(users);
        List<UserInfoDto> result = new ArrayList<>();
        for (User user: users) {
            for (Rating rating: uRatings) {
                if (user.getId().equals(rating.getId())) {
                    List<Event> events = eventRepository.findByInitiatorIdIs(user.getId());
                    result.add(new UserInfoDto(user.getName(),
                                               user.getEmail(),
                                               rating.getRating(),
                                               new HashSet<>(events),
                                               user.getViews()));
                }
            }
        }
        return result.stream().sorted(Comparator.comparing(UserInfoDto::getRating).reversed()).collect(Collectors.toList());
    }

}
