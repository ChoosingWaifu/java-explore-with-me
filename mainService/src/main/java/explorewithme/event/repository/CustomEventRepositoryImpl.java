package explorewithme.event.repository;

import explorewithme.event.Event;
import explorewithme.event.dto.EventSort;
import explorewithme.event.dto.EventState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequiredArgsConstructor
public class CustomEventRepositoryImpl implements CustomEventRepository {

    @PersistenceContext
    private final EntityManager em;

    public List<Event> infoFindEventsBy(String text,
                                        List<Long> categories,
                                        Boolean paid,
                                        LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                        Boolean onlyAvailable,
                                        String sort,
                                        Integer size, Integer from) {
        log.info("repos {}, {}, {}, {}, {}, {}, {}, {}, {}", text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, size, from);
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
            predicates.add(cb.greaterThanOrEqualTo(eventRoot.get("eventDate"),
                    LocalDateTime.now()));
        }
        if (onlyAvailable != null) {
            if (onlyAvailable) {
                predicates.add(cb.or(
                        cb.equal(eventRoot.get("participantLimit"), 0),
                        cb.greaterThan(eventRoot.get("participantLimit"), eventRoot.get("confirmedRequests")))
                );
                predicates.add(cb.greaterThan(eventRoot.get("participantLimit"), eventRoot.get("confirmedRequests")));
            }
        }
        if (sort != null) {
            if (EventSort.from(sort).equals(EventSort.EVENT_DATE)) {
                cq.orderBy(cb.desc(eventRoot.get("eventDate")));
            }
        }
        Predicate[] predicatesArray = predicates.toArray(new Predicate[0]);
        CriteriaQuery<Event> resultQ = cq.where(predicatesArray);
        List<Event> resultList = em.createQuery(resultQ).setFirstResult(from).setMaxResults(size).getResultList();
        log.info("list event {}, {}", resultList, resultList.size());
        return resultList;
    }

    public List<Event> adminFindEventsBy(List<String> states,
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
        List<Event> firstPredicate = em.createQuery(cq.where(predicates.toArray(new Predicate[0]))).getResultList();
        log.info("1, states {}", firstPredicate);
        if (categories != null) {
            predicates.add(eventRoot.get("category").in(categories));
        }
        List<Event> secondPredicate = em.createQuery(cq.where(predicates.toArray(new Predicate[0]))).getResultList();
        log.info("2, category {}", secondPredicate.size());
        if (rangeStart != null && rangeEnd != null) {
            predicates.add(cb.between(eventRoot.get("eventDate"),
                    rangeStart, rangeEnd));
        } else {
            predicates.add(cb.between(eventRoot.get("eventDate"),
                    LocalDateTime.now(), LocalDateTime.MAX));
        }
        List<Event> thirdPredicate = em.createQuery(cq.where(predicates.toArray(new Predicate[0]))).getResultList();
        log.info("3, event date {}", thirdPredicate.size());
        Predicate[] predicatesArray = predicates.toArray(new Predicate[0]);
        CriteriaQuery<Event> resultQ = cq.where(predicatesArray);
        return em.createQuery(resultQ).setFirstResult(from).setMaxResults(size).getResultList();
    }
}


