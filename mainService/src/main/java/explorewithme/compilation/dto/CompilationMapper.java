package explorewithme.compilation.dto;

import explorewithme.compilation.Compilation;
import explorewithme.event.dto.EventMapper;

public class CompilationMapper {

    public static CompilationDto toCompilationDto(Compilation comp) {
        return new CompilationDto(
                comp.getId(),
                comp.getPinned(),
                comp.getTitle(),
                EventMapper.toListEventShortDto(comp.getEvents())
        );
    }

    public static Compilation toCompilation(NewCompilationDto dto) {
        return new Compilation(
                null,
                dto.getPinned(),
                dto.getTitle(),
                null
        );
    }
}
