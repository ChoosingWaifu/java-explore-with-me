package explorewithme.category;

import explorewithme.category.dto.CategoryDto;
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
@RequestMapping(path = "/categories")
@RequiredArgsConstructor
public class CategoryInfoController {

    @GetMapping
    public List<CategoryDto> getCategories() {
        log.info("public, get categories");
        return null;
    }

    @GetMapping("/{catId}")
    public CompilationDto getCategoryById(@PathVariable Integer catId) {
        log.info("public, get ru.category by Id {}", catId);
        return null;
    }

}
