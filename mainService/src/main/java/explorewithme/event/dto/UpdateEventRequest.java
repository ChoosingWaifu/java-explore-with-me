package explorewithme.event.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateEventRequest {

    @Min(20)
    @Max(2000)
    private String annotation;
    private Long category;
    @Min(20)
    @Max(7000)
    private String description;
    private String eventDate;
    private Long eventId;
    private Boolean paid;
    private Long participantLimit;
    @Min(3)
    @Max(120)
    private String title;
}
