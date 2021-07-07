package car_rental.api.rents;

import lombok.Data;
import lombok.EqualsAndHashCode;


import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "return_address")
@Data
public class ReturnAddress extends Address{

}
