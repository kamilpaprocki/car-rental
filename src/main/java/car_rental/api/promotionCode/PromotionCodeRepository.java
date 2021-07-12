package car_rental.api.promotionCode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PromotionCodeRepository extends JpaRepository<PromotionCode, Long> {

    @Query(value = "SELECT p FROM PromotionCode p WHERE p.isActive = true")
    List<PromotionCode> getActivePromotionCodes();

    @Query(value = "SELECT p FROM PromotionCode p WHERE p.isActive = false")
    List<PromotionCode> getInactivePromotionCodes();

    @Modifying
    @Query(value = "DELETE FROM PromotionCode p WHERE p.id = :id")
    int deletePromotionCodeById(Long id);

    @Query(value = "SELECT p FROM PromotionCode  p WHERE p.promotionCode = :promotionCode")
    Optional<PromotionCode> getPromotionCodeByCode(String promotionCode);
}
