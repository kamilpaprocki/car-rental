package car_rental.api.userDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {

    @Modifying
    @Query(value = "DELETE FROM UserDetails u where u.id =: id")
    int deleteUserDetailsById(long id);


}
