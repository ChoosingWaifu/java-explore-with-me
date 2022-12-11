package explorewithme.user.repository;

import explorewithme.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>, CustomUserRepository {

    List<User> findByIdIn(@Param("ids") List<Long> ids);

    User findByNameIs(String name);

}
