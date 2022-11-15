package explorewithme.user.dto;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {

    private Integer id;
    private String name;
    private String email;
}
