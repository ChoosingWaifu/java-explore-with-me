package explorewithme.category;

import explorewithme.category.dto.CategoryDto;
import explorewithme.category.dto.NewCategoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping(path = "/admin/categories")
@RequiredArgsConstructor
public class CategoryController {

    @PostMapping
    public CategoryDto addCategory(@RequestBody NewCategoryDto newCategory) {
        log.info("create new ru.category {}", newCategory);
        return null;
    }

    @PatchMapping
    public CategoryDto patchCategory(@RequestBody CategoryDto category) {
        log.info("patch ru.category {}", category);
        return null;
    }

    @DeleteMapping("/{catId}")
    public void deleteCategory(@PathVariable Long catId) {
        log.info("delete ru.category {}", catId);
    }
}

