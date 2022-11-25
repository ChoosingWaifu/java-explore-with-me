package explorewithme.category;

import explorewithme.category.dto.CategoryDto;
import explorewithme.category.dto.NewCategoryDto;

public interface CategoryService {

    CategoryDto addCategory(NewCategoryDto dto);

    CategoryDto patchCategory(CategoryDto dto);

    void deleteCategory(Long catId);
}
