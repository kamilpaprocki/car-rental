package car_rental.api.client;

import org.springframework.stereotype.Component;

@Component
public class ClientDataFormatValidator {

    public boolean isCorrectFormatName(String name){
        return name.matches("\\p{L}+(\\p{L}+)*");
    }

    public boolean isCorrectFormatLastName(String lastName){
        return lastName.matches("\\p{L}+([ '-]\\p{L}+)*");
    }

    public boolean isCorrectFormatStreetName(String streetName){
        return streetName.matches("(\\p{L}|\\d)+([ '-](\\p{L}|\\d)+)*");
    }

    public boolean isCorrectFormatStreetOrApartmentNumber(String number){
        return number.matches("\\d+[a-zA-Z]?");
    }

    public boolean isCorrectFormatPostalCode(String postalCode){
        return postalCode.matches("\\d{2}[-]\\d{3}");
    }

    public boolean isCorrectFormatCity(String city){
        return city.matches("(\\p{L}|\\d)+([ '-]\\p{L}+)*");
    }

    public boolean isCorrectFormatDrivingLicenseNumber(String drivingLicenseNumber){
        return drivingLicenseNumber.matches("\\d{5}[/]\\d{2}[/]\\d{4}");
    }

    public boolean isCorrectFormatIdentityCardNumber(String identityCardNumber){
        return identityCardNumber.matches("[A-Z]{3}\\d{6}");
    }

    public boolean isCorrectFormatPeselNumber(String peselNumber){
        return peselNumber.matches("\\d{11}");
    }

    public boolean isCorrectFormatPhoneNumber(String phoneNumber){
        return phoneNumber.matches("\\d{9}");
    }

    public boolean isCorrectFormatBirthDate(String birthDate){
        return birthDate.matches("\\d{4}[-]\\d{1,2}[-]\\d{1,2}");
    }

}
