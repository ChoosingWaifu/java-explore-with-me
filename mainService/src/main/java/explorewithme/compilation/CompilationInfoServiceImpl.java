package explorewithme.compilation;

import explorewithme.compilation.dto.CompilationDto;
import explorewithme.compilation.dto.RepoCompilationMapper;
import explorewithme.exceptions.NotFoundException;
import explorewithme.pagination.PageFromRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompilationInfoServiceImpl implements CompilationInfoService {

    private final CompilationRepository repository;

    private final RepoCompilationMapper mapper;

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size) {
        List<Compilation> result;
        if (pinned != null) {
            result = repository.findAllByPinnedIs(pinned);
        } else {
            Pageable pageable = PageFromRequest.of(from, size);
            result = repository.findAll(pageable).toList();
        }
        return result.stream().map(mapper::toCompilationDto).collect(Collectors.toList());
    }

    @Override
    public CompilationDto getCompilationById(Long compId) {
        return mapper.toCompilationDto(repository.findById(compId)
                .orElseThrow(() -> new NotFoundException("compilation not found")));
    }
}
