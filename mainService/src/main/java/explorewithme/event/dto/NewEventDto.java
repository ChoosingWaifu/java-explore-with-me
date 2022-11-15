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
    private String eventDate;//"yyyy-MM-dd HH:mm:ss"
    @NotNull
    private Location location;
    private Boolean paid;//default false
    private Long participantLimit;//default(0)
    private Boolean requestModeration;//default??
    @NotNull
    @Min(3)
    @Max(120)
    private String title;
}
