package car_rental.api.rents;


import javax.persistence.*;


@Entity
@Table(name = "rent_address")
public class RentAddress extends Address {

    public RentAddress() {
    }

    public static RentAddressBuilder builder() {
        return new RentAddressBuilder();
    }

    private RentAddress(RentAddressBuilder r){
        this.setId(r.id);
        this.setCity(r.city);
        this.setPostalCode(r.postalCode);
        this.setStreet(r.street);
        this.setStreetNumber(r.streetNumber);
    }

    public static final class RentAddressBuilder {
        private Long id;
        private String street;
        private String streetNumber;
        private String postalCode;
        private String city;

        private RentAddressBuilder() {
        }

        public RentAddressBuilder id(String id) {
            if (id == null){
                this.id = null;
                return this;
            }
            this.id = Long.parseLong(id);
            return this;
        }

        public RentAddressBuilder street(String street) {
            this.street = street;
            return this;
        }

        public RentAddressBuilder streetNumber(String streetNumber) {
            this.streetNumber = streetNumber;
            return this;
        }

        public RentAddressBuilder postalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public RentAddressBuilder city(String city) {
            this.city = city;
            return this;
        }

        public RentAddress build() {
           return new RentAddress(this);
        }
    }
}
