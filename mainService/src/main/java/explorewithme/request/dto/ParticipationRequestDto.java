package explorewithme.request.dto;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class ParticipationRequestDto {

    private String created;
    private Integer event;
    private Integer id;
    private Integer requester;
    private RequestStatus status;
}
