package car_rental.api.rents;

import car_rental.api.user.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentRepository extends JpaRepository<Rent, Long> {


    @Modifying
    @Query("DELETE FROM Rent r WHERE r.id = :id")
    int deleteRentById(Long id);

    @Query(value = "SELECT r FROM Rent r WHERE r.userApp = :userApp AND r.isFinished = false")
    Optional<List<Rent>> getRentByUserApp(UserApp userApp);

    @Query(value = "SELECT r FROM Rent r WHERE r.isFinished = false")
    Optional<List<Rent>> getActiveRents();

}
