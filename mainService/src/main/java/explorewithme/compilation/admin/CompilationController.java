package explorewithme.compilation.admin;

import explorewithme.compilation.dto.CompilationDto;
import explorewithme.compilation.dto.NewCompilationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping(path = "/admin/compilations")
@RequiredArgsConstructor
public class CompilationController {

    private final CompilationService service;

    @PostMapping
    public CompilationDto addCompilation(@RequestBody @Valid NewCompilationDto newCompilation) {
        log.info("create new compilation {}", newCompilation);
        return service.addCompilation(newCompilation);
    }

    @DeleteMapping("/{compId}")
    public void deleteCompilation(@PathVariable Long compId) {
        log.info("delete compilation {}", compId);
        service.deleteCompilation(compId);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public void addEvent(@PathVariable Long compId, @PathVariable Long eventId) {
        log.info("add ru.event {} to compilation {}", eventId, compId);
        service.addEvent(compId, eventId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public void deleteEvent(@PathVariable Long compId, @PathVariable Long eventId) {
        log.info("delete ru.event {} from compilation {}", eventId, compId);
        service.deleteEvent(compId, eventId);
    }

    @PatchMapping("/{compId}/pin")
    public void pinCompilation(@PathVariable Long compId) {
        log.info("pin compilation {}", compId);
        service.pinCompilation(compId);
    }

    @DeleteMapping("/{compId}/pin")
    public void unpinCompilation(@PathVariable Long compId) {
        log.info("unpin compilation {}", compId);
        service.unpinCompilation(compId);
    }
}


