package explorewithme.category.dto;

import explorewithme.category.Category;

public class CategoryMapper {

    public static CategoryDto toCategoryDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName());
    }

    public static Category toCategory(CategoryDto dto) {
        return new Category(
                dto.getId(),
                dto.getName());
    }

    public static Category toCategory(NewCategoryDto dto) {
        return new Category(
                null,
                dto.getName());
    }
}
