package explorewithme.request;

import explorewithme.event.Event;
import explorewithme.event.repository.EventRepository;
import explorewithme.exceptions.InsufficientRightsException;
import explorewithme.exceptions.notfound.EventNotFoundException;
import explorewithme.request.dto.ParticipationRequestDto;
import explorewithme.request.dto.RequestMapper;
import explorewithme.request.dto.RequestStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository repository;

    private final EventRepository eventRepository;

    @Override
    public ParticipationRequestDto addRequest(Long userId, Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(EventNotFoundException::new);
        if (userId.equals(event.getInitiator().getId())) {
            throw new InsufficientRightsException("can't request for own event");
        }
        if (event.getPublishedOn() == null) {
            throw new InsufficientRightsException("event should be published");
        }
        ParticipationRequest request = RequestMapper.newRequest(userId, eventId);
        if (!event.getRequestModeration()) {
            request.setStatus(RequestStatus.CONFIRMED);
        }
        log.info("request service, add request user {} for event {}", userId, eventId);
        return RequestMapper.toRequestDto(repository.save(request));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParticipationRequestDto> getRequests(Long userId) {
        log.info("request service, get requests for user {}", userId);
        return RequestMapper.toRequestDtoList(repository.findByRequesterIs(userId));
    }

    @Override
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        List<ParticipationRequest> userRequests = repository.findByRequesterIs(userId);
        ParticipationRequest request = userRequests.stream()
                .filter(o -> o.getId().equals(requestId))
                .collect(Collectors.toList()).get(0);
        request.setStatus(RequestStatus.CANCELED);
        return RequestMapper.toRequestDto(repository.save(request));
    }
}
