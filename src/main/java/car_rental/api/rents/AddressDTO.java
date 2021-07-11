package car_rental.api.rents;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddressDTO {

    @JsonProperty(value = "street")
    private String street;

    @JsonProperty(value = "street_number")
    private String streetNumber;

    @JsonProperty(value = "postal_code")
    private String postalCode;

    @JsonProperty(value = "city")
    private String city;

    private AddressDTO(AddressDTOBuilder b){
        this.streetNumber = b.streetNumber;
        this.postalCode = b.postalCode;
        this.street = b.street;
        this.city = b.city;
    }

    public static AddressDTOBuilder builder() {
        return new AddressDTOBuilder();
    }

    public static final class AddressDTOBuilder {
        private String street;
        private String streetNumber;
        private String postalCode;
        private String city;

        public AddressDTOBuilder street(String street) {
            this.street = street;
            return this;
        }

        public AddressDTOBuilder streetNumber(String streetNumber) {
            this.streetNumber = streetNumber;
            return this;
        }

        public AddressDTOBuilder postalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public AddressDTOBuilder city(String city) {
            this.city = city;
            return this;
        }

        public AddressDTO build() {
            return new AddressDTO(this);
        }
    }

    public String getStreet() {
        return street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return String.join("; ", street, streetNumber, postalCode, city);
    }
}
