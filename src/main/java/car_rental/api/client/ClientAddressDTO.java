package car_rental.api.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientAddressDTO {

    @JsonProperty(value = "street")
    private String street;

    @JsonProperty(value = "street_number")
    private String streetNumber;

    @JsonProperty(value = "apartment_number")
    private String apartmentNumber;

    @JsonProperty(value = "postal_code")
    private String postalCode;

    @JsonProperty(value = "city")
    private String city;

    public static ClientAddressDTOBuilder builder() {
        return new ClientAddressDTOBuilder();
    }

    private ClientAddressDTO(ClientAddressDTOBuilder b) {
        this.street = b.street;
        this.apartmentNumber = b.apartmentNumber;
        this.postalCode = b.postalCode;
        this.city = b.city;
        this.streetNumber = b.streetNumber;
    }

    public static final class ClientAddressDTOBuilder {
        private String street;
        private String streetNumber;
        private String apartmentNumber;
        private String postalCode;
        private String city;

        public ClientAddressDTOBuilder street(String street) {
            this.street = street;
            return this;
        }

        public ClientAddressDTOBuilder streetNumber(String streetNumber) {
            this.streetNumber = streetNumber;
            return this;
        }

        public ClientAddressDTOBuilder apartmentNumber(String apartmentNumber) {
            this.apartmentNumber = apartmentNumber;
            return this;
        }

        public ClientAddressDTOBuilder postalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public ClientAddressDTOBuilder city(String city) {
            this.city = city;
            return this;
        }

        public ClientAddressDTO build() {
            return new ClientAddressDTO(this);

        }
    }

    public String getStreet() {
        return street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return String.join("; ", street, streetNumber, apartmentNumber, postalCode, city);
    }
}
