package car_rental.api.userDetails;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserDetailsAddressDTO {

    @JsonProperty(value = "street")
    @NotEmpty(message = "This can not be empty.")
    @NotNull(message = "This can not be null.")
    @Pattern(regexp = "(\\p{L}|\\d)+([ '-](\\p{L}|\\d)+)*", message = "Not allowed special characters/digits.")
    private String street;

    @JsonProperty(value = "street_number")
    @NotEmpty(message = "This can not be empty.")
    @NotNull(message = "This can not be null.")
    @Pattern(regexp = "\\d+[a-zA-Z]?", message = "Not allowed special characters/digits. Street number must be start with number.")
    private String streetNumber;

    @JsonProperty(value = "apartment_number")
    @Pattern(regexp = "(\\d)*|([a-zA-Z])?", message = "Not allowed special characters/digits. Apartment number must be a number or character.")
    private String apartmentNumber;

    @JsonProperty(value = "postal_code")
    @NotEmpty(message = "This can not be empty.")
    @NotNull(message = "This can not be null.")
    @Pattern(regexp = "\\d{2}[-]\\d{3}", message = "Wrong input format. Correct format: 11-111")
    private String postalCode;

    @JsonProperty(value = "city")
    @NotEmpty(message = "This can not be empty.")
    @NotNull(message = "This can not be null.")
    @Pattern(regexp = "(\\p{L}|\\d)+([ '-]\\p{L}+)*", message = "Not allowed special characters/digits.")
    private String city;

    public static ClientAddressDTOBuilder builder() {
        return new ClientAddressDTOBuilder();
    }

    public UserDetailsAddressDTO() {
    }

    private UserDetailsAddressDTO(ClientAddressDTOBuilder b) {
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

        public UserDetailsAddressDTO build() {
            return new UserDetailsAddressDTO(this);

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

    public void setStreet(String street) {
        this.street = street;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return String.join("; ", street, streetNumber, apartmentNumber, postalCode, city);
    }
}
