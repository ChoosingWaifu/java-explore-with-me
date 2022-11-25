package explorewithme.user;

import explorewithme.user.dto.NewUserRequest;
import explorewithme.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/admin/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    public List<UserDto> getUsers(@RequestParam(required = false) List<Long> ids,
                                  @RequestParam(defaultValue = "0") Integer from,
                                  @RequestParam(defaultValue = "20") Integer size) {
        return service.getUsers(ids, from, size);
    }

    @PostMapping
    public UserDto addUser(@RequestBody @Valid NewUserRequest newUser) {
        log.info("create new user {}", newUser);
        return service.addNewUser(newUser);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        log.info("delete user {}", userId);
        service.deleteUser(userId);
    }
}
