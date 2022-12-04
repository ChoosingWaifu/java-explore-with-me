package explorewithme.request.dto;

import explorewithme.request.ParticipationRequest;
import explorewithme.utility.DateTimeMapper;

import java.util.List;
import java.util.stream.Collectors;

public class RequestMapper {

    public static ParticipationRequest newRequest(Long userId, Long eventId) {
        return new ParticipationRequest(
                null,
                null,
                eventId,
                userId,
                RequestStatus.PENDING
        );
    }

    public static ParticipationRequestDto toRequestDto(ParticipationRequest request) {
        return new ParticipationRequestDto(
                request.getId(),
                request.getCreated().format(DateTimeMapper.format()),
                request.getEvent(),
                request.getRequester(),
                request.getStatus()
        );
    }

    public static List<ParticipationRequestDto> toRequestDtoList(List<ParticipationRequest> requests) {
       return requests.stream().map(RequestMapper::toRequestDto).collect(Collectors.toList());
    }
}
