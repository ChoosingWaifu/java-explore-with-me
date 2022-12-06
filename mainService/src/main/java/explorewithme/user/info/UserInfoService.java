package explorewithme.user.info;

import explorewithme.user.dto.UserInfoDto;

import java.util.List;

public interface UserInfoService {

    UserInfoDto getUserInfo(Long userId);

    List<UserInfoDto> getTopUsers(Integer from, Integer size);

    void likeUser(Long liker, Long liked, Boolean type);

    void removeLike(Long liker, Long liked);



}
