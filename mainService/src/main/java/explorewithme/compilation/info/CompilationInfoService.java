package explorewithme.compilation.info;

import explorewithme.compilation.dto.CompilationDto;

import java.util.List;

public interface CompilationInfoService {

    List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size);

    CompilationDto getCompilationById(Long compId);
}
