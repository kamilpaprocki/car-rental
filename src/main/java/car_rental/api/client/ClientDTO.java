package car_rental.api.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientDTO {

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "last_name")
    private String lastName;

    @JsonProperty(value = "nationality")
    private String nationality;

    @JsonProperty(value = "address")
    private String address;

    @JsonProperty(value = "driving_license_number")
    private String drivingLicenseNumber;

    @JsonProperty(value = "identity_card_number")
    private String identityCardNumber;

    @JsonProperty(value = "pesel_number")
    private String peselNumber;

    @JsonProperty(value = "phone_number")
    private String phoneNumber;

    @JsonProperty(value = "birth_date")
    private String birthDate;

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

    public String getAddress() {
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

    public static ClientDTOBuilder builder(){
        return new ClientDTOBuilder();
    }

    public static class ClientDTOBuilder {

        private String name;
        private String lastName;
        private String nationality;
        private String address;
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

        public ClientDTOBuilder address(String address){
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
