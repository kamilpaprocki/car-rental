package car_rental.api.rents;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RentRepository extends JpaRepository<Rent, Long> {


    @Modifying
    @Query("DELETE FROM Rent r WHERE r.id = :id")
    int deleteRentById(Long id);
}
