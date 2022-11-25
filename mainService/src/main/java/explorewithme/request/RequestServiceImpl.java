package explorewithme.request;

import explorewithme.request.dto.ParticipationRequestDto;
import explorewithme.request.dto.RequestMapper;
import explorewithme.request.dto.RequestStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository repository;

    @Override
    public ParticipationRequestDto addRequest(Long userId, Long requestId) {
        ParticipationRequest request = RequestMapper.newRequest(userId, requestId);
        return RequestMapper.toRequestDto(repository.save(request));
    }

    @Override
    public List<ParticipationRequestDto> getRequests(Long userId) {
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
