package car_rental.api.userDetails;

import car_rental.api.utils.DTOMapper;
import car_rental.api.utils.DateParser;

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
        userDetails.setBirthDate(new DateParser().parseDate(from.getBirthDate()));

        return userDetails;
    }
}
