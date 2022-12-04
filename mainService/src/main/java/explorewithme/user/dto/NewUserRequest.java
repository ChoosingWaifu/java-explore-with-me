package explorewithme.user.dto;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@Valid
public class NewUserRequest {

    @NotNull
    private String name;
    @NotNull
    @Email
    private String email;
}
