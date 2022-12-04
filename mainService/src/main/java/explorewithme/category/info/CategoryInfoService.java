package explorewithme.category.info;

import explorewithme.category.dto.CategoryDto;

import java.util.List;

public interface CategoryInfoService {

    List<CategoryDto> getCategories(Integer from, Integer size);

    CategoryDto getCategoryById(Long categoryId);
}
