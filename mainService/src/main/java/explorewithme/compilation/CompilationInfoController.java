package explorewithme.compilation;

import explorewithme.compilation.dto.CompilationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/compilations")
@RequiredArgsConstructor
public class CompilationInfoController {

    @GetMapping
    public List<CompilationDto> getCompilations() {
        log.info("public, get compilations");
        return null;
    }

    @GetMapping("/{compId}")
    public CompilationDto getCompilationById(@PathVariable Long compId) {
        log.info("public, get ru.compilation by Id {}", compId);
        return null;
    }

}

