package car_rental.api.car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Modifying
    @Query(value = "DELETE FROM Car c where c.id = :id")
    int deleteCarById(long id);

    @Query(value = "SELECT c FROM Car c where c.isAvailable = true")
    Optional<List<Car>> getAvailableCars();

    @Query(value = "SELECT c FROM Car c where c.isAvailable = false")
    Optional<List<Car>> getUnavailableCars();
}
