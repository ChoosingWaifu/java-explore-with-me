package explorewithme.user;

import explorewithme.exceptions.NotFoundException;
import explorewithme.pagination.PageFromRequest;
import explorewithme.user.dto.NewUserRequest;
import explorewithme.user.dto.UserDto;
import explorewithme.user.dto.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public UserDto addNewUser(NewUserRequest userRequest) {
        User user = UserMapper.toUser(userRequest);
        log.info("service create new user {}", user);
        return UserMapper.toUserDto(repository.save(user));
    }

    @Override
    public List<UserDto> getUsers(List<Long> ids, Integer from, Integer size) {
        if (ids != null) {
            return UserMapper.toUserDtoList(repository.findByIdIn(ids));
        }
        Pageable pageable = PageFromRequest.of(from, size);
        return UserMapper.toUserDtoList(repository.findAll(pageable).stream().collect(Collectors.toList()));
    }

    @Override
    public UserDto getById(Long userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new NotFoundException("user not found"));
        return UserMapper.toUserDto(user);
    }

    @Override
    public void deleteUser(Long userId) {
        repository.deleteById(userId);
    }
}
