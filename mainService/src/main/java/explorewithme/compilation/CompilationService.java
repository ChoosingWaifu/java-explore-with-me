package explorewithme.compilation;

import explorewithme.compilation.dto.CompilationDto;
import explorewithme.compilation.dto.NewCompilationDto;

public interface CompilationService {

    CompilationDto addCompilation(NewCompilationDto dto);

    void deleteCompilation(Long compId);

    void addEvent(Long compId, Long eventId);

    void deleteEvent(Long compId, Long eventId);

    void pinCompilation(Long compId);

    void unpinCompilation(Long compId);
}
