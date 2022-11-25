package explorewithme.category;

import explorewithme.category.dto.CategoryDto;
import explorewithme.category.dto.CategoryMapper;
import explorewithme.exceptions.NotFoundException;
import explorewithme.pagination.PageFromRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryInfoServiceImpl implements CategoryInfoService {

    private final CategoryRepository repository;


    @Override
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        Pageable pageable = PageFromRequest.of(from, size);
        return repository.findAll(pageable).stream().map(CategoryMapper::toCategoryDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(Long categoryId) {
        return CategoryMapper.toCategoryDto(repository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("category not found")));
    }
}
