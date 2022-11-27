package explorewithme.category.admin;

import explorewithme.category.Category;
import explorewithme.category.CategoryRepository;
import explorewithme.category.dto.CategoryDto;
import explorewithme.category.dto.CategoryMapper;
import explorewithme.category.dto.NewCategoryDto;
import explorewithme.event.repository.EventRepository;
import explorewithme.exceptions.DbConflictException;
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
        checkForUnique(dto.getName());
        Category category = CategoryMapper.toCategory(dto);
        log.info("add cat {}", dto);
        return CategoryMapper.toCategoryDto(repository.save(category));
    }

    @Override
    public CategoryDto patchCategory(CategoryDto dto) {
        checkForUnique(dto.getName());
        repository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundException("category not found"));
        Category category = CategoryMapper.toCategory(dto);
        log.info("patch cat {}", dto);
        return CategoryMapper.toCategoryDto(repository.save(category));
    }

    @Override
    public void deleteCategory(Long catId) {
        repository.findById(catId)
                .orElseThrow(() -> new NotFoundException("category not found"));
        if (!eventRepository.findByCategory_IdIs(catId).isEmpty()) {
            throw new InsufficientRightsException("can't delete category with events");
        }
        log.info("delete cat {}", catId);
        repository.deleteById(catId);
    }

    private void checkForUnique(String name) {
        if (repository.findByNameIs(name) != null) {
            throw new DbConflictException("unique constraint violation");
        }
    }
}
