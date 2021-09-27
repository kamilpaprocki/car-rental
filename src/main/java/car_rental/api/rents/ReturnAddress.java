package car_rental.api.rents;

import javax.persistence.*;


@Entity
@Table(name = "return_address")
public class ReturnAddress extends Address{

    public ReturnAddress() {
    }

    private ReturnAddress(ReturnAddressBuilder r){
        this.setId(r.id);
        this.setCity(r.city);
        this.setPostalCode(r.postalCode);
        this.setStreet(r.street);
        this.setStreetNumber(r.streetNumber);
    }

    public static ReturnAddressBuilder builder() {
        return new ReturnAddressBuilder();
    }

    public static final class ReturnAddressBuilder {
        private Long id;
        private String street;
        private String streetNumber;
        private String postalCode;
        private String city;

        private ReturnAddressBuilder() {
        }

        public ReturnAddressBuilder id(String id) {
            if (id == null){
                this.id = null;
                return this;
            }
            this.id = Long.parseLong(id);
            return this;
        }

        public ReturnAddressBuilder street(String street) {
            this.street = street;
            return this;
        }

        public ReturnAddressBuilder streetNumber(String streetNumber) {
            this.streetNumber = streetNumber;
            return this;
        }

        public ReturnAddressBuilder postalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public ReturnAddressBuilder city(String city) {
            this.city = city;
            return this;
        }

        public ReturnAddress build() {
            return new ReturnAddress(this);
        }
    }
}
