package explorewithme.category;

import explorewithme.category.dto.CategoryDto;
import explorewithme.category.dto.CategoryMapper;
import explorewithme.category.dto.NewCategoryDto;
import explorewithme.event.EventRepository;
import explorewithme.exceptions.InsufficientRightsException;
import explorewithme.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    private final EventRepository eventRepository;

    @Override
    public CategoryDto addCategory(NewCategoryDto dto) {
        Category category = CategoryMapper.toCategory(dto);
        return CategoryMapper.toCategoryDto(repository.save(category));
    }

    @Override
    public CategoryDto patchCategory(CategoryDto dto) {
        repository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundException("category not found"));
        Category category = CategoryMapper.toCategory(dto);
        return CategoryMapper.toCategoryDto(repository.save(category));
    }

    @Override
    public void deleteCategory(Long catId) {
        repository.findById(catId)
                .orElseThrow(() -> new NotFoundException("category not found"));
        if (!eventRepository.findByCategoryIs(catId).isEmpty()) {
            throw new InsufficientRightsException("can't delete category with events");
        }
        repository.deleteById(catId);
    }
}
