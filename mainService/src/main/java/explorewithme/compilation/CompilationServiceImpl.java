package explorewithme.compilation;

import explorewithme.compilation.dto.CompilationDto;
import explorewithme.compilation.dto.CompilationMapper;
import explorewithme.compilation.dto.NewCompilationDto;
import explorewithme.compilation.dto.RepoCompilationMapper;
import explorewithme.event.Event;
import explorewithme.event.EventRepository;
import explorewithme.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository repository;

    private final EventRepository eventRepository;

    private final RepoCompilationMapper mapper;

    @Override
    public CompilationDto addCompilation(NewCompilationDto dto) {
        List<Event> events = eventRepository.findByIdIn(dto.getEvents());
        Compilation compilation = CompilationMapper.toCompilation(dto);
        compilation.setEvents(new HashSet<>(events));
        return mapper.toCompilationDto(repository.save(compilation));
    }

    @Override
    public void deleteCompilation(Long compId) {
        repository.deleteById(compId);
    }

    @Override
    public void addEvent(Long compId, Long eventId) {
        Compilation compilation = repository.findById(compId)
                .orElseThrow(() -> new NotFoundException("compilation not found"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("event not found"));
        Set<Event> events = compilation.getEvents();
        events.add(event);
        compilation.setEvents(events);
        repository.save(compilation);
    }

    @Override
    public void deleteEvent(Long compId, Long eventId) {
        Compilation compilation = repository.findById(compId)
                .orElseThrow(() -> new NotFoundException("compilation not found"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("event not found"));
        Set<Event> events = compilation.getEvents();
        events.remove(event);
        compilation.setEvents(events);
        repository.save(compilation);
    }

    @Override
    public void pinCompilation(Long compId) {
        Compilation compilation = repository.findById(compId)
                .orElseThrow(() -> new NotFoundException("compilation not found"));
        compilation.setPinned(true);
        repository.save(compilation);
    }

    @Override
    public void unpinCompilation(Long compId) {
        Compilation compilation = repository.findById(compId)
                .orElseThrow(() -> new NotFoundException("compilation not found"));
        compilation.setPinned(false);
        repository.save(compilation);
    }
}
