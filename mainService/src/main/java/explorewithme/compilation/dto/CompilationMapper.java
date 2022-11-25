package explorewithme.compilation.dto;

import explorewithme.compilation.Compilation;

public class CompilationMapper {

    public static Compilation toCompilation(NewCompilationDto dto) {
        return new Compilation(
                null,
                dto.getPinned(),
                dto.getTitle(),
                null
        );
    }
}
