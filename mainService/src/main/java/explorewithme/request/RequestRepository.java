package explorewithme.request;

import explorewithme.request.dto.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<ParticipationRequest, Long> {

    List<ParticipationRequest> findByRequesterIs(Long userId);

    List<ParticipationRequest> findByEventIs(Long eventId);

    Long countByEventIsAndStatusIs(Long eventId, RequestStatus status);
}
