package explorewithme.errors;

import explorewithme.utility.DateTimeMapper;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiError {

    private List<String> errors = new ArrayList<>();
    private String message;
    private String reason;
    private HttpStatus status;
    private String timestamp;

    public ApiError(String message, String reason, HttpStatus status) {
        this.message = message;
        this.reason = reason;
        this.status = status;
        this.timestamp = LocalDateTime.now().format(DateTimeMapper.format());
    }

}
