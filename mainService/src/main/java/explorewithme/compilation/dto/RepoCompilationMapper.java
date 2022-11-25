package explorewithme.compilation.dto;

import explorewithme.compilation.Compilation;
import explorewithme.event.dto.RepoEventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RepoCompilationMapper {

    private final RepoEventMapper mapper;

    public CompilationDto toCompilationDto(Compilation comp) {
        return new CompilationDto(
                comp.getId(),
                comp.getPinned(),
                comp.getTitle(),
                mapper.toListEventShortDto(comp.getEvents())
        );
    }
}
