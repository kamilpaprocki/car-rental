package car_rental.api.PromotionCode;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "promotion_code")
@Data
@DynamicInsert
public class PromotionCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "promotion_code", nullable = false)
    private String promotionCode;

    @Column(name = "used_date")
    private Date usedDate;

    @Column(name = "generate_date", nullable = false)
    private Date generateDate;

    @Column(name = "exp_date", nullable = false)
    private Date expDate;

    @Column(name = "discount", nullable = false)
    private BigDecimal discount;

    @Column(name = "is_multiple_use", nullable = false)
    private boolean isMultipleUse;

    @Column(name = "available_use", nullable = false)
    private int availableUse;

    @Column(name = "is_active")
    @ColumnDefault("true")
    private Boolean isActive;

}
