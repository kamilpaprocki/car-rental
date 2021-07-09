package car_rental.api.rents;


import lombok.EqualsAndHashCode;
import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "return_address")
public class ReturnAddress extends Address{

}
