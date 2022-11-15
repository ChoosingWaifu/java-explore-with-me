package explorewithme.request;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import explorewithme.request.dto.RequestStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "requests")
public class ParticipationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    private LocalDateTime created;
    @NotNull
    private Long event;
    @NotNull
    private Long requester;
    @NotNull
    @Enumerated(EnumType.STRING)
    private RequestStatus status;
}
