package explorewithme.category.admin;

import explorewithme.category.dto.CategoryDto;
import explorewithme.category.dto.NewCategoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping(path = "/admin/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;

    @PostMapping
    public CategoryDto addCategory(@RequestBody @Valid NewCategoryDto newCategory) {
        log.info("create new category {}", newCategory);
        return service.addCategory(newCategory);
    }

    @PatchMapping
    public CategoryDto patchCategory(@RequestBody @Valid CategoryDto category) {
        log.info("patch category {}", category);
        return service.patchCategory(category);
    }

    @DeleteMapping("/{catId}")
    public void deleteCategory(@PathVariable Long catId) {
        log.info("delete category {}", catId);
        service.deleteCategory(catId);
    }
}

