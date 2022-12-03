package explorewithme.compilation.info;

import explorewithme.compilation.Compilation;
import explorewithme.compilation.CompilationRepository;
import explorewithme.compilation.dto.CompilationDto;
import explorewithme.compilation.dto.CompilationMapper;
import explorewithme.exceptions.notfound.CompilationNotFoundException;
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

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size) {
        List<Compilation> result;
        if (pinned != null) {
            result = repository.findAllByPinnedIs(pinned);
        } else {
            Pageable pageable = PageFromRequest.of(from, size);
            result = repository.findAll(pageable).toList();
        }
        return result.stream().map(CompilationMapper::toCompilationDto).collect(Collectors.toList());
    }

    @Override
    public CompilationDto getCompilationById(Long compId) {
        return CompilationMapper.toCompilationDto(repository.findById(compId)
                .orElseThrow(CompilationNotFoundException::new));
    }
}
