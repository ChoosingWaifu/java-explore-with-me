package explorewithme.event.dto;

import explorewithme.event.location.Location;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class NewEventDto {

    @NotNull
    @Min(20)
    @Max(2000)
    private String annotation;
    @NotNull
    private Long category;
    @NotNull
    @Min(20)
    @Max(7000)
    private String description;
    @NotNull
    private String eventDate;
    @NotNull
    private Location location;
    private Boolean paid;
    private Long participantLimit;
    private Boolean requestModeration;
    @NotNull
    @Min(3)
    @Max(120)
    private String title;
}
