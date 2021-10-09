package car_rental.api.userDetails;

import car_rental.api.validators.DateFormatChecker;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@DateFormatChecker
public class UserDetailsDTO {

    @JsonProperty(value = "id")
    private String id;

    @JsonProperty(value = "name")
    @NotEmpty(message = "Field: \"name\" cannot be empty.")
    @Pattern(regexp = "\\p{L}+(\\p{L}+)*", message = "Not allowed special characters/digits in name field.")
    private String name;

    @JsonProperty(value = "lastName")
    @NotEmpty(message = "Field: \"last name\" cannot be empty.")
    @Pattern(regexp = "\\p{L}+([ '-]\\p{L}+)*", message = "Not allowed special characters/digits in last name field.")
    private String lastName;

    @JsonProperty(value = "nationality")
    @NotEmpty(message = "Field: \"nationality\" cannot be empty.")
    @Pattern(regexp = "\\p{L}+(\\p{L}+)*", message = "Not allowed special characters/digits in nationality field.")
    private String nationality;

    @JsonProperty(value = "address")
    @Valid
    private UserDetailsAddressDTO addressDTO;

    @JsonProperty(value = "drivingLicenseNumber")
    @NotEmpty(message = "Field: \"driving license number\" cannot be empty.")
    @Pattern(regexp = "\\d{5}[/]\\d{2}[/]\\d{4}", message = "Wrong input format. Correct driving license number format : 11111/11/1111")
    private String drivingLicenseNumber;

    @JsonProperty(value = "identityCardNumber")
    @NotEmpty(message = "Field: \"identity card number\" cannot be empty.")
   @Pattern(regexp = "[A-Z]{3}\\d{6}", message = "Wrong input format. Correct identity card number format : AAA111111")
    private String identityCardNumber;

    @JsonProperty(value = "peselNumber")
    @NotEmpty(message = "Field: \"pesel number\" cannot be empty.")
    @Pattern(regexp = "\\d{11}", message = "Not allowed characters. Correct pesel number format is 11 digits.")
    private String peselNumber;

    @JsonProperty(value = "phoneNumber")
    @NotEmpty(message = "Field: \"phone number\" cannot be empty.")
    @Pattern(regexp = "\\d{9}", message = "Not allowed characters. Correct phone number format is 9 digits.")
    private String phoneNumber;

    @JsonProperty(value = "birthDate")
    private String birthDate;

    public UserDetailsDTO() {
    }

    private UserDetailsDTO(ClientDTOBuilder b) {
        this.id = b.id;
        this.name = b.name;
        this.lastName = b.lastName;
        this.nationality = b.nationality;
        this.addressDTO = b.address;
        this.drivingLicenseNumber = b.drivingLicenseNumber;
        this.identityCardNumber = b.identityCardNumber;
        this.peselNumber = b.peselNumber;
        this.phoneNumber = b.phoneNumber;
        this.birthDate = b.birthDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public UserDetailsAddressDTO getAddressDTO() {
        return addressDTO;
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

    public void setAddressDTO(UserDetailsAddressDTO addressDTO) {
        this.addressDTO = addressDTO;
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

        private String id;
        private String name;
        private String lastName;
        private String nationality;
        private UserDetailsAddressDTO address;
        private String drivingLicenseNumber;
        private String identityCardNumber;
        private String peselNumber;
        private String phoneNumber;
        private String birthDate;

        public ClientDTOBuilder id(String id){
            this.id = id;
            return this;
        }

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

        public ClientDTOBuilder address(UserDetailsAddressDTO address){
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

        public UserDetailsDTO build(){
            return new UserDetailsDTO(this);
        }
    }

    @Override
    public String toString() {
        return "UserDetailsDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nationality='" + nationality + '\'' +
                ", addressDTO=" + addressDTO +
                ", drivingLicenseNumber='" + drivingLicenseNumber + '\'' +
                ", identityCardNumber='" + identityCardNumber + '\'' +
                ", peselNumber='" + peselNumber + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", birthDate='" + birthDate + '\'' +
                '}';
    }
}
