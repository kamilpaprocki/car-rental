package car_rental.api.userDetails;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;


@Table(name = "details")
@Entity
public class UserDetails {

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
    private UserDetailsAddress userDetailsAddress;

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

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public UserDetailsAddress getClientAddress() {
        return userDetailsAddress;
    }

    public void setAddress(UserDetailsAddress userDetailsAddress) {
        this.userDetailsAddress = userDetailsAddress;
    }

    public String getDrivingLicenseNumber() {
        return drivingLicenseNumber;
    }

    public void setDrivingLicenseNumber(String drivingLicenseNumber) {
        this.drivingLicenseNumber = drivingLicenseNumber;
    }

    public String getIdentityCardNumber() {
        return identityCardNumber;
    }

    public void setIdentityCardNumber(String identityCardNumber) {
        this.identityCardNumber = identityCardNumber;
    }

    public String getPeselNumber() {
        return peselNumber;
    }

    public void setPeselNumber(String peselNumber) {
        this.peselNumber = peselNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDetails)) return false;
        UserDetails userDetails = (UserDetails) o;
        return getId().equals(userDetails.getId()) && getName().equals(userDetails.getName()) && getLastName().equals(userDetails.getLastName()) && getNationality().equals(userDetails.getNationality()) && getClientAddress().equals(userDetails.getClientAddress()) && getDrivingLicenseNumber().equals(userDetails.getDrivingLicenseNumber()) && getIdentityCardNumber().equals(userDetails.getIdentityCardNumber()) && getPeselNumber().equals(userDetails.getPeselNumber()) && getPhoneNumber().equals(userDetails.getPhoneNumber()) && getBirthDate().equals(userDetails.getBirthDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getLastName(), getNationality(), getClientAddress(), getDrivingLicenseNumber(), getIdentityCardNumber(), getPeselNumber(), getPhoneNumber(), getBirthDate());
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nationality='" + nationality + '\'' +
                ", clientAddress=" + userDetailsAddress +
                ", drivingLicenseNumber='" + drivingLicenseNumber + '\'' +
                ", identityCardNumber='" + identityCardNumber + '\'' +
                ", peselNumber='" + peselNumber + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
