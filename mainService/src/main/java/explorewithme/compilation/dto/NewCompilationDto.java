package explorewithme.compilation.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class NewCompilationDto {

    @NotNull
    private String title;
    private Boolean pinned;//default false

    private List<Long> events;
}
