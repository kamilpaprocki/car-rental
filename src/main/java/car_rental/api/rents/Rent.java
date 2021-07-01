package car_rental.api.rents;

import car_rental.api.car.Car;
import car_rental.api.client.Client;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "rents")
@Data
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Car car;

    @Column(name = "rent_date", nullable = false)
    private Date rentDate;

    @Column(name = "return_date")
    private Date returnDate;

    @Column(name = "rental_cost")
    @ColumnDefault(value = "0")
    private BigDecimal rentalCost;

    @Column(name = "odometer_distance")
    @ColumnDefault(value = "0")
    private long odomoterDistance;
}
