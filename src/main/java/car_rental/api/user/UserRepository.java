package car_rental.api.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserApp, Long> {

    Optional<UserApp> findByUsername(String username);

    Optional<UserApp> findByEmail(String email);

    @Query(value = "SELECT u FROM UserApp u WHERE u.isActive = true")
    Optional<List<UserApp>> getActiveUsers();

    @Query(value = "SELECT u FROM UserApp u WHERE u.id = :id")
    Optional<UserApp> getUserAppByById(long id);
}
