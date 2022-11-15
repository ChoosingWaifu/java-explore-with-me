package explorewithme.event.dto;

import explorewithme.user.dto.UserShortDto;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class EventShortDto {

    private String annotation;
    private Long category;
    private Long confirmedRequests;
    private String eventDate;
    private Long id;
    private UserShortDto initiator;
    private Boolean paid;
    private String title;
    private Long views;
}
