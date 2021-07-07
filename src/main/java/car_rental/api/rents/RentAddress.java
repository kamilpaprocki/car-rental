package car_rental.api.rents;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;


@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "rent_address")

public class RentAddress extends Address {


}
