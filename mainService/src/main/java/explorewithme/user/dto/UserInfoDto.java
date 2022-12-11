package explorewithme.user.dto;

import explorewithme.event.Event;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class UserInfoDto {

    private String name;

    private String email;

    private Long rating;

    private Set<Event> events;

    private Long views;
}
