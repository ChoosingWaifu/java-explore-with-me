package explorewithme.utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeMapper {

    public static DateTimeFormatter format() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    public static LocalDateTime toLocalDateTime(String datetime) {
        if (datetime != null) {
            return LocalDateTime.parse(datetime, format());
        } else {
            return null;
        }
    }
}