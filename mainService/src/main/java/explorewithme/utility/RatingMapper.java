package explorewithme.utility;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class RatingMapper {

    public static List<Rating> mapRating(List<Object[]> top) {
        List<Rating> ratings = new ArrayList<>();
        for (Object[] object: top) {
            BigInteger id = (BigInteger) object[0];
            BigInteger rating = (BigInteger) object[1];
            ratings.add(new Rating(id.longValue(), rating.longValue()));
        }
        return ratings;
    }
}
