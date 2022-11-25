package explorewithme.event;

import explorewithme.event.dto.EventSort;
import explorewithme.event.dto.EventState;
import explorewithme.pagination.PageFromRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CustomEventRepositoryImpl implements CustomEventRepository {

    @PersistenceContext
    private final EntityManager em;

    public Page<Event> infoFindEventsBy(String text,
                                        List<Long> categories,
                                        Boolean paid,
                                        LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                        Boolean onlyAvailable,
                                        String sort,
                                        Integer size, Integer from) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Event> cq = cb.createQuery(Event.class);
        Root<Event> eventRoot = cq.from(Event.class);
        List<Predicate> predicates = new ArrayList<>();
        if (text != null) {
            String lc = text.toLowerCase();
            predicates.add(cb.or(
                    cb.like(cb.lower(eventRoot.get("annotation")), "%" + lc + "%"),
                    cb.like(cb.lower(eventRoot.get("description")), "%" + lc + "%")
                    )
            );
        }
        if (categories != null) {
            predicates.add(eventRoot.get("category").in(categories));
        }
        if (paid != null) {
            predicates.add(cb.equal(eventRoot.get("paid"), paid));
        }
        if (rangeStart != null && rangeEnd != null) {
            predicates.add(cb.between(eventRoot.get("eventDate"),
                    rangeStart, rangeEnd));
        } else {
            predicates.add(cb.between(eventRoot.get("eventDate"),
                    LocalDateTime.now(), LocalDateTime.MAX));
        }
        if (onlyAvailable != null) {
            predicates.add(cb.greaterThan(eventRoot.get("participantLimit"), eventRoot.get("confirmedRequests")));
        }
        if (EventSort.from(sort).equals(EventSort.EVENT_DATE)) {
            cq.orderBy(cb.desc(eventRoot.get("eventDate")));
        }
        Predicate[] predicatesArray = predicates.toArray(new Predicate[0]);
        CriteriaQuery<Event> resultQ = cq.where(predicatesArray);
        List<Event> resultList = em.createQuery(resultQ).getResultList();
        Pageable pageable = PageFromRequest.of(from, size);
        return new PageImpl<>(resultList, pageable, size);
    }

    public Page<Event> privateFindEventsBy(List<String> states,
                                           List<Long> categories,
                                           LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                           Integer size, Integer from) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Event> cq = cb.createQuery(Event.class);
        Root<Event> eventRoot = cq.from(Event.class);
        List<Predicate> predicates = new ArrayList<>();
        if (states != null) {
            List<EventState> eventStates = states.stream().map(EventState::fromStr).collect(Collectors.toList());
            predicates.add(eventRoot.get("state").in(eventStates));
        }
        if (categories != null) {
            predicates.add(eventRoot.get("category").in(categories));
        }
        if (rangeStart != null && rangeEnd != null) {
            predicates.add(cb.between(eventRoot.get("eventDate"),
                    rangeStart, rangeEnd));
        } else {
            predicates.add(cb.between(eventRoot.get("eventDate"),
                    LocalDateTime.now(), LocalDateTime.MAX));
        }
        Pageable pageable = PageFromRequest.of(from, size);
        Predicate[] predicatesArray = predicates.toArray(new Predicate[0]);
        cq.where(predicatesArray);
        List<Event> resultList = em.createQuery(cq).getResultList();
        return new PageImpl<>(resultList, pageable, size);
    }
}


