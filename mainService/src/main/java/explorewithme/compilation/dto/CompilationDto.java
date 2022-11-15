package explorewithme.compilation.dto;

import explorewithme.event.dto.EventShortDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class CompilationDto {

    private Integer id;
    private Boolean pinned;
    private String title;

    private List<EventShortDto> events;

}
