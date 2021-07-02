package car_rental.api.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Modifying
    @Query(value = "DELETE FROM Client c where c.id = :id")
    int deleteClientById(long id);


}
