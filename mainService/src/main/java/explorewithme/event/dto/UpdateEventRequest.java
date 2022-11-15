package explorewithme.event.dto;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateEventRequest {

    private String annotation;
    private Long category;
    private String description;
    private String eventDate;
    private Long eventId;
    private Boolean paid;
    private Long participantLimit;
    private String title;
}
