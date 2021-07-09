package car_rental.api.rents;

import lombok.EqualsAndHashCode;
import javax.persistence.*;


@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "rent_address")
public class RentAddress extends Address {



}
