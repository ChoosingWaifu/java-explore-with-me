package explorewithme.likes;

import explorewithme.user.User;

import java.util.Set;

public class LikeMapper {

    public static Like newLike(User liker, Boolean type) {
        return new Like(
                null,
                liker,
                type,
                null
        );
    }

    public static Long getRating(Set<Like> likes) {
        Long positive = likes.stream().filter(o -> o.getType().equals(true)).count();
        Long negative = likes.stream().filter(o -> o.getType().equals(false)).count();
        return positive - negative;
    }

}
