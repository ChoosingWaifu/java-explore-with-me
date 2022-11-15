package explorewithme.user;

import explorewithme.user.dto.UserShortDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import explorewithme.user.dto.NewUserRequest;
import explorewithme.user.dto.UserDto;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/admin/users")
@RequiredArgsConstructor
public class UserController {

    @GetMapping
    public List<UserDto> getUser() {
        return null;
    }

    @PostMapping
    public UserShortDto addUser(@RequestBody NewUserRequest newUser) {
        log.info("create new ru.user {}", newUser);
        return null;
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Integer userId) {
        log.info("delete ru.user {}", userId);
    }
}
