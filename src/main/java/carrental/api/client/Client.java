package carrental.api.client;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "client")
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "nationality", nullable = false)
    private String nationality;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @Column(name = "driving_license_number", nullable = false, length = 13)
    private String drivingLicenseNumber;

    @Column(name = "identity_card_number", nullable = false, length = 9)
    private String identityCardNumber;

    @Column(name = "pesel_number", nullable = false, length = 11)
    private String peselNumber;

    @Column(name = "phone_number", nullable = false, length = 9)
    private String phoneNumber;

    @Column(name = "birth_date", nullable = false)
    private Date birthDate;


}
