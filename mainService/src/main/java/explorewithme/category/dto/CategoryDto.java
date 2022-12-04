package explorewithme.category.dto;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Valid
@ToString
public class CategoryDto {

    @NotNull
    private Long id;
    @NotNull
    private String name;
}
