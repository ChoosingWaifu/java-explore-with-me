package explorewithme.category.info;

import explorewithme.category.Category;
import explorewithme.category.CategoryRepository;
import explorewithme.category.dto.CategoryDto;
import explorewithme.category.dto.CategoryMapper;
import explorewithme.exceptions.notfound.CategoryNotFoundException;
import explorewithme.pagination.PageFromRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryInfoServiceImpl implements CategoryInfoService {

    private final CategoryRepository repository;


    @Override
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        Pageable pageable = PageFromRequest.of(from, size);
        log.info("info, get {}, {}", from, size);
        List<Category> categories = repository.findAll(pageable).stream().collect(Collectors.toList());
        log.info("size = {}", categories.size());
        return categories.stream().map(CategoryMapper::toCategoryDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(Long categoryId) {
        log.info("info, get by id {}", categoryId);
        return CategoryMapper.toCategoryDto(repository.findById(categoryId)
                .orElseThrow(CategoryNotFoundException::new));
    }
}
