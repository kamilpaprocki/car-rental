package car_rental.api.userDetails;

import car_rental.api.exceptions.WrongDataFormatException;
import car_rental.api.utils.DTOMapper;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;

public class UserDetailsMapper implements DTOMapper<UserDetails, UserDetailsDTO> {
    @Override
    public UserDetailsDTO map(UserDetails from) {

        return UserDetailsDTO.builder()
                .name(from.getName())
                .lastName(from.getLastName())
                .nationality(from.getNationality())
                .address(new UserDetailsAddressMapper().map(from.getClientAddress()))
                .drivingLicenseNumber(from.getDrivingLicenseNumber())
                .identityCardNumber(from.getIdentityCardNumber())
                .peselNumber(from.getPeselNumber())
                .phoneNumber(from.getPhoneNumber())
                .birthDate(from.getBirthDate().toString())
                .build();
    }

    @Override
    public UserDetails reverse(UserDetailsDTO from) {
        UserDetails userDetails = new UserDetails();
        userDetails.setName(from.getName());
        userDetails.setLastName(from.getLastName());
        userDetails.setNationality(from.getNationality());
        userDetails.setAddress(new UserDetailsAddressMapper().reverse(from.getAddressDTO()));
        userDetails.setDrivingLicenseNumber(from.getDrivingLicenseNumber());
        userDetails.setIdentityCardNumber(from.getIdentityCardNumber());
        userDetails.setPeselNumber(from.getPeselNumber());
        userDetails.setPhoneNumber(from.getPhoneNumber());

        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        try {
            userDetails.setBirthDate(Date.valueOf(df.parse(from.getBirthDate()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
        } catch (ParseException e) {
            throw new WrongDataFormatException("Wrong date format.");
        }
        return userDetails;
    }
}
