package explorewithme.event.repository;

import explorewithme.event.Event;
import explorewithme.event.dto.EventSort;
import explorewithme.event.dto.EventState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

    @Override
    public List<Object[]> topEvents() {
        String query = "Select likes_dislikes_query.event_id as id, likes_dislikes_query.lc - likes_dislikes_query.dc as rating" +
                " from (SELECT event_id, count(joined.event_id) filter (where type) as lc, count(joined.event_id) filter (where not type) as dc" +
                " FROM (events_likes ul" +
                " JOIN likes l on l.id = ul.like_id) joined" +
                " GROUP BY event_id" +
                " ORDER BY event_id) as likes_dislikes_query" +
                " LIMIT 100";
        Query q = em.createNativeQuery(query);
        List<Object[]> objectList = (List<Object[]>) q.getResultList();
        log.info("result {}", objectList);
        return objectList;
    }

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
        log.info("list event {}", resultList.size());
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
        if (categories != null) {
            predicates.add(eventRoot.get("category").in(categories));
        }
        if (rangeStart != null && rangeEnd != null) {
            predicates.add(cb.between(eventRoot.get("eventDate"),
                    rangeStart, rangeEnd));
        } /*else if (rangeEnd != null) {
            log.info("range end != null");
            predicates.add(cb.between(eventRoot.get("eventDate"),
                    LocalDateTime.MIN, rangeEnd));
        } else {
            log.info("now to max");
            predicates.add(cb.between(eventRoot.get("eventDate"),
                    LocalDateTime.now(), LocalDateTime.MAX));
        }*/
        Predicate[] predicatesArray = predicates.toArray(new Predicate[0]);
        CriteriaQuery<Event> resultQ = cq.where(predicatesArray);
        return em.createQuery(resultQ).setFirstResult(from).setMaxResults(size).getResultList();
    }
}


