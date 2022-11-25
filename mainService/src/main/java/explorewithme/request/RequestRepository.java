package explorewithme.request;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<ParticipationRequest, Long> {

    List<ParticipationRequest> findByRequesterIs(Long userId);

    List<ParticipationRequest> findByEventIs(Long eventId);
}
