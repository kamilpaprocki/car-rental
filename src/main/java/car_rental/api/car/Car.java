package car_rental.api.car;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@DynamicInsert
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "brand", nullable = false, length = 60)
    private String brand;

    @Column(name = "model", nullable = false, length = 30)
    private String model;

    @Enumerated(EnumType.STRING)
    @Column(name = "segment", nullable = false)
    private CarSegment segment;

    @Column(name = "model_year", nullable = false, length = 4)
    private int modelYear;

    @Column(name = "current_odometer", nullable = false)
    private long currentOdometer;

    @Column(name = "price_per_day", nullable = false)
    private BigDecimal pricePerDay;

    @Column(name = "is_available")
    @ColumnDefault("true")
    private Boolean isAvailable;

}
