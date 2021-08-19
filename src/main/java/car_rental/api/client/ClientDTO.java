package car_rental.api.client;

import car_rental.api.utils.DateFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@DateFormat
public class ClientDTO {

    @JsonProperty(value = "name")
    @NotEmpty(message = "This can not be empty.")
    @Pattern(regexp = "\\p{L}+(\\p{L}+)*", message = "Not allowed special characters/digits.")
    private String name;

    @JsonProperty(value = "last_name")
    @NotEmpty(message = "This can not be empty.")
    @Pattern(regexp = "\\p{L}+([ '-]\\p{L}+)*", message = "Not allowed special characters/digits.")
    private String lastName;

    @JsonProperty(value = "nationality")
    @NotEmpty(message = "This can not be empty.")
    @Pattern(regexp = "\\p{L}+(\\p{L}+)*", message = "Not allowed special characters/digits.")
    private String nationality;

    @JsonProperty(value = "address")
    @Valid
    private ClientAddressDTO address;

    @JsonProperty(value = "driving_license_number")
    @NotEmpty(message = "This can not be empty.")
    @Pattern(regexp = "\\d{5}[/]\\d{2}[/]\\d{4}", message = "Wrong input format. Correct format: 11111/11/1111")
    private String drivingLicenseNumber;

    @JsonProperty(value = "identity_card_number")
    @NotEmpty(message = "This can not be empty.")
   @Pattern(regexp = "[A-Z]{3}\\d{6}", message = "Wrong input format. Correct format: AAA111111")
    private String identityCardNumber;

    @JsonProperty(value = "pesel_number")
    @NotEmpty(message = "This can not be empty.")
    @Pattern(regexp = "\\d{11}", message = "Not allowed characters. Correct pesel format is 11 digits.")
    private String peselNumber;

    @JsonProperty(value = "phone_number")
    @NotEmpty(message = "This can not be empty.")
    @Pattern(regexp = "\\d{9}", message = "Not allowed characters. Correct phone number format is 9 digits.")
    private String phoneNumber;

    @JsonProperty(value = "birth_date")
    private String birthDate;

    public ClientDTO() {
    }

    private ClientDTO(ClientDTOBuilder b) {
        this.name = b.name;
        this.lastName = b.lastName;
        this.nationality = b.nationality;
        this.address = b.address;
        this.drivingLicenseNumber = b.drivingLicenseNumber;
        this.identityCardNumber = b.identityCardNumber;
        this.peselNumber = b.peselNumber;
        this.phoneNumber = b.phoneNumber;
        this.birthDate = b.birthDate;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNationality() {
        return nationality;
    }

    public ClientAddressDTO getAddress() {
        return address;
    }

    public String getDrivingLicenseNumber() {
        return drivingLicenseNumber;
    }

    public String getIdentityCardNumber() {
        return identityCardNumber;
    }

    public String getPeselNumber() {
        return peselNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setAddress(ClientAddressDTO address) {
        this.address = address;
    }

    public void setDrivingLicenseNumber(String drivingLicenseNumber) {
        this.drivingLicenseNumber = drivingLicenseNumber;
    }

    public void setIdentityCardNumber(String identityCardNumber) {
        this.identityCardNumber = identityCardNumber;
    }

    public void setPeselNumber(String peselNumber) {
        this.peselNumber = peselNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public static ClientDTOBuilder builder(){
        return new ClientDTOBuilder();
    }

    public static class ClientDTOBuilder {

        private String name;
        private String lastName;
        private String nationality;
        private ClientAddressDTO address;
        private String drivingLicenseNumber;
        private String identityCardNumber;
        private String peselNumber;
        private String phoneNumber;
        private String birthDate;

        public ClientDTOBuilder name(String name){
            this.name = name;
            return this;
        }

        public ClientDTOBuilder lastName(String lastName){
            this.lastName = lastName;
            return this;
        }

        public ClientDTOBuilder nationality(String nationality){
            this.nationality = nationality;
            return this;
        }

        public ClientDTOBuilder address(ClientAddressDTO address){
            this.address = address;
            return this;
        }

        public ClientDTOBuilder drivingLicenseNumber(String drivingLicenseNumber){
            this.drivingLicenseNumber = drivingLicenseNumber;
            return this;
        }

        public ClientDTOBuilder identityCardNumber(String identityCardNumber){
            this.identityCardNumber = identityCardNumber;
            return this;
        }

        public ClientDTOBuilder peselNumber(String peselNumber){
            this.peselNumber = peselNumber;
            return this;
        }

        public ClientDTOBuilder phoneNumber(String phoneNumber){
            this.phoneNumber = phoneNumber;
            return this;
        }

        public ClientDTOBuilder birthDate(String birthDate){
            this.birthDate = birthDate;
            return this;
        }

        public ClientDTO build(){
            return new ClientDTO(this);
        }
    }

    @Override
    public String toString() {
        return "ClientDTO{" +
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nationality='" + nationality + '\'' +
                ", address='" + address + '\'' +
                ", drivingLicenseNumber='" + drivingLicenseNumber + '\'' +
                ", identityCardNumber='" + identityCardNumber + '\'' +
                ", peselNumber='" + peselNumber + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
