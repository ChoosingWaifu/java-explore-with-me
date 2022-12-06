package explorewithme.user.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class CustomUserRepositoryImpl implements CustomUserRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Object[]> topUsers() {
        String query = "Select likes_dislikes_query.user_id as id, likes_dislikes_query.lc - likes_dislikes_query.dc as rating" +
                        " from (SELECT user_id, count(joined.user_id) filter (where type) as lc, count(joined.user_id) filter (where not type) as dc" +
                        " FROM (users_likes ul" +
                        " JOIN likes l on l.id = ul.like_id) joined" +
                        " GROUP BY user_id" +
                        " ORDER BY user_id) as likes_dislikes_query";
        Query q = em.createNativeQuery(query);
        List<Object[]> objectList = (List<Object[]>) q.getResultList();
        log.info("result {}", objectList);
        return objectList;
    }
}
