package explorewithme.compilation.dto;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Valid
@ToString
public class NewCompilationDto {

    @NotNull
    private String title;
    private Boolean pinned;

    private List<Long> events;
}
