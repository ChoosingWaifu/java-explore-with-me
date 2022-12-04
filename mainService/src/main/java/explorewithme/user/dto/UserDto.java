package explorewithme.user.dto;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {

    private Long id;
    private String name;
    private String email;
}
