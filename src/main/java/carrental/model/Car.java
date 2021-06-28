package carrental.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "segment")
    private CarSegment segment;

    @Column(name = "model_year")
    private int modelYear;

    @Column(name = "current_odometer")
    private long currentOdometer;

    @Column(name = "price_per_day")
    private BigDecimal pricePerDay;

    @Column(name = "is_available")
    private boolean isAvailable;

}
