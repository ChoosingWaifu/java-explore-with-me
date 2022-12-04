package explorewithme.errors;

import lombok.*;

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
    private String status;
    private String timestamp;

}
