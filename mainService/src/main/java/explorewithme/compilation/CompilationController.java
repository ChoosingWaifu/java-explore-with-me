package explorewithme.compilation;

import explorewithme.compilation.dto.CompilationDto;
import explorewithme.compilation.dto.NewCompilationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping(path = "/admin/compilations")
@RequiredArgsConstructor
public class CompilationController {

    @PostMapping
    public CompilationDto addCompilation(@RequestBody NewCompilationDto newCompilation) {
        log.info("create new ru.compilation {}",newCompilation);
        return null;
    }

    @DeleteMapping("/{compId}")
    public void deleteCompilation(@PathVariable Long compId) {
        log.info("delete ru.compilation {}", compId);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public CompilationDto addEvent(@PathVariable Long compId, @PathVariable Long eventId) {
        log.info("add ru.event {} to ru.compilation {}", eventId, compId);
        return null;
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public void deleteEvent(@PathVariable Long compId, @PathVariable Long eventId) {
        log.info("delete ru.event {} from ru.compilation {}", eventId, compId);
    }

    @PatchMapping("/{compId}/pin")
    public void pinCompilation(@PathVariable Long compId) {
        log.info("pin ru.compilation {}", compId);
    }

    @DeleteMapping("/{compId}/pin")
    public void unpinCompilation(@PathVariable Long compId) {
        log.info("unpin ru.compilation {}", compId);
    }
}


