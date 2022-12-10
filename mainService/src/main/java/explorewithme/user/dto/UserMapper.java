package explorewithme.user.dto;

import explorewithme.user.User;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static UserInfoDto toUserInfoDto(User user) {
        return new UserInfoDto(
                user.getName(),
                user.getEmail(),
               null,
               null,
                null
        );
    }

    public static UserShortDto toUserShortDto(User user) {
        return new UserShortDto(
                user.getName(),
                user.getEmail()
        );
    }

    public static User toUser(NewUserRequest userRequest) {
        return new User(
                null,
                userRequest.getName(),
                userRequest.getEmail(),
                null,
                null
        );
    }

    public static User toUser(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getName(),
                userDto.getEmail(),
                null,
                null
        );
    }

    public static UserDto toUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    public static List<UserDto> toUserDtoList(List<User> userList) {
        List<UserDto> result = new ArrayList<>();
        for (User user: userList) {
            result.add(UserMapper.toUserDto(user));
        }
        return result;
    }

}

