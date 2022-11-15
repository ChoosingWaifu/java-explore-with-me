package explorewithme.user.dto;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class UserShortDto {

    private Integer id;
    private String name;
    private String email;
}
