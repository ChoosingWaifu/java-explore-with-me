package explorewithme.compilation.admin;

import explorewithme.compilation.Compilation;
import explorewithme.compilation.CompilationRepository;
import explorewithme.compilation.dto.CompilationDto;
import explorewithme.compilation.dto.CompilationMapper;
import explorewithme.compilation.dto.NewCompilationDto;
import explorewithme.event.Event;
import explorewithme.event.repository.EventRepository;
import explorewithme.exceptions.notfound.CompilationNotFoundException;
import explorewithme.exceptions.notfound.EventNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository repository;

    private final EventRepository eventRepository;

    @Override
    @Transactional
    public CompilationDto addCompilation(NewCompilationDto dto) {
        List<Event> events = eventRepository.findByIdIn(dto.getEvents());
        Compilation compilation = CompilationMapper.toCompilation(dto);
        compilation.setEvents(new HashSet<>(events));
        return CompilationMapper.toCompilationDto(repository.save(compilation));
    }

    @Override
    @Transactional
    public void deleteCompilation(Long compId) {
        repository.deleteById(compId);
    }

    @Override
    public void addEvent(Long compId, Long eventId) {
        Compilation compilation = repository.findById(compId)
                .orElseThrow(CompilationNotFoundException::new);
        Event event = eventRepository.findById(eventId)
                .orElseThrow(EventNotFoundException::new);
        Set<Event> events = compilation.getEvents();
        log.info("before {}", events.size());
        events.add(event);
        log.info("after add {}", events.size());
        compilation.setEvents(events);
        log.info("comp get {}", compilation.getEvents().size());
        repository.save(compilation);
        Set<Event>  check = repository.findById(compId)
                .orElseThrow(EventNotFoundException::new).getEvents();
        log.info("check {}, {}", check.size(), check);
    }

    @Override
    public void deleteEvent(Long compId, Long eventId) {
        Compilation compilation = repository.findById(compId)
                .orElseThrow(CompilationNotFoundException::new);
        Event event = eventRepository.findById(eventId)
                .orElseThrow(EventNotFoundException::new);
        Set<Event> events = compilation.getEvents();
        events.remove(event);
        compilation.setEvents(events);
        repository.save(compilation);
    }

    @Override
    public void pinCompilation(Long compId) {
        Compilation compilation = repository.findById(compId)
                .orElseThrow(CompilationNotFoundException::new);
        compilation.setPinned(true);
        repository.save(compilation);
    }

    @Override
    public void unpinCompilation(Long compId) {
        Compilation compilation = repository.findById(compId)
                .orElseThrow(CompilationNotFoundException::new);
        compilation.setPinned(false);
        repository.save(compilation);
    }
}
