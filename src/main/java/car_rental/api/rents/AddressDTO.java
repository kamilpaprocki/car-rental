package car_rental.api.rents;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class AddressDTO {

    @JsonProperty(value = "id")
    private String id;

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

    public AddressDTO() {
    }

    private AddressDTO(AddressDTOBuilder b){
        this.id = b.id;
        this.streetNumber = b.streetNumber;
        this.postalCode = b.postalCode;
        this.street = b.street;
        this.city = b.city;
    }

    public static AddressDTOBuilder builder() {
        return new AddressDTOBuilder();
    }

    public static final class AddressDTOBuilder {
        private String id;
        private String street;
        private String streetNumber;
        private String postalCode;
        private String city;

        public AddressDTOBuilder id(Long id){
            if (id == null){
                this.id = null;
                return this;
            }
            this.id = String.valueOf(id);
            return this;
        }

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

    public String getId() {
        return id;
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

    public void setId(String id) {
        this.id = id;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return String.join("; ", street, streetNumber, postalCode, city);
    }
}
