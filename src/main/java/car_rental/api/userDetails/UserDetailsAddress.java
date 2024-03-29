package car_rental.api.userDetails;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_details_address")
public class UserDetailsAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_details_address_id")
    private Long id;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "street_number", nullable = false)
    private String streetNumber;

    @Column(name = "apartment_number")
    private String apartmentNumber;

    @Column(name = "postal_code", nullable = false, length = 6)
    private String postalCode;

    @Column(name = "city", nullable = false, length = 30)
    private String city;

    public UserDetailsAddress() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDetailsAddress)) return false;
        UserDetailsAddress userDetailsAddress = (UserDetailsAddress) o;
        return getId().equals(userDetailsAddress.getId()) && getStreet().equals(userDetailsAddress.getStreet()) && getStreetNumber().equals(userDetailsAddress.getStreetNumber()) && Objects.equals(getApartmentNumber(), userDetailsAddress.getApartmentNumber()) && getPostalCode().equals(userDetailsAddress.getPostalCode()) && getCity().equals(userDetailsAddress.getCity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStreet(), getStreetNumber(), getApartmentNumber(), getPostalCode(), getCity());
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                ", apartmentNumber='" + apartmentNumber + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    public static UserDetailsAddressBuilder builder() {
        return new UserDetailsAddressBuilder();
    }

    private UserDetailsAddress(UserDetailsAddressBuilder u){
        this.id = u.id;
        this.street = u.street;
        this.streetNumber = u.streetNumber;
        this.city = u.city;
        this.apartmentNumber = u.apartmentNumber;
        this.postalCode = u.postalCode;
    }

    public static final class UserDetailsAddressBuilder {
        private Long id;
        private String street;
        private String streetNumber;
        private String apartmentNumber;
        private String postalCode;
        private String city;

        private UserDetailsAddressBuilder() {
        }

        public UserDetailsAddressBuilder id(String id) {
            if (id == null){
                this.id = null;
                return this;
            }
            this.id = Long.parseLong(id);
            return this;
        }

        public UserDetailsAddressBuilder street(String street) {
            this.street = street;
            return this;
        }

        public UserDetailsAddressBuilder streetNumber(String streetNumber) {
            this.streetNumber = streetNumber;
            return this;
        }

        public UserDetailsAddressBuilder apartmentNumber(String apartmentNumber) {
            if (apartmentNumber == null){
                this.apartmentNumber = null;
                return this;
            }
            this.apartmentNumber = apartmentNumber;
            return this;
        }

        public UserDetailsAddressBuilder postalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public UserDetailsAddressBuilder city(String city) {
            this.city = city;
            return this;
        }

        public UserDetailsAddress build() {
return new UserDetailsAddress(this);
        }
    }
}
