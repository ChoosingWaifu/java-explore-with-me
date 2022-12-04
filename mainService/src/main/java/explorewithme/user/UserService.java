package explorewithme.user;

import explorewithme.user.dto.NewUserRequest;
import explorewithme.user.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto addNewUser(NewUserRequest request);

    List<UserDto> getUsers(List<Long> ids, Integer from, Integer size);

    UserDto getById(Long userId);

    void deleteUser(Long userId);
}
