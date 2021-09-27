package car_rental.api.userDetails;

import car_rental.api.utils.DTOMapper;
import car_rental.api.utils.DateParser;

public class UserDetailsMapper implements DTOMapper<UserAppDetails, UserDetailsDTO> {
    @Override
    public UserDetailsDTO mapToDTO(UserAppDetails from) {
        if (from == null){
            return null;
        }

        return UserDetailsDTO.builder()
                .id(String.valueOf(from.getId()))
                .name(from.getName())
                .lastName(from.getLastName())
                .nationality(from.getNationality())
                .address(new UserDetailsAddressMapper().mapToDTO(from.getClientAddress()))
                .drivingLicenseNumber(from.getDrivingLicenseNumber())
                .identityCardNumber(from.getIdentityCardNumber())
                .peselNumber(from.getPeselNumber())
                .phoneNumber(from.getPhoneNumber())
                .birthDate(new DateParser().parseDateToStringDTO(from.getBirthDate()))
                .build();
    }

    @Override
    public UserAppDetails mapToDAO(UserDetailsDTO from) {
        if (from == null){
            return null;
        }
        return UserAppDetails.builder()
                .id(from.getId())
                .name(from.getName())
                .lastName(from.getLastName())
                .nationality(from.getNationality())
                .userDetailsAddress(new UserDetailsAddressMapper().mapToDAO(from.getAddressDTO()))
                .drivingLicenseNumber(from.getDrivingLicenseNumber())
                .identityCardNumber(from.getIdentityCardNumber())
                .peselNumber(from.getPeselNumber())
                .phoneNumber(from.getPhoneNumber())
                .birthDate(new DateParser().parseStringToDateDAO(from.getBirthDate()))
                .build();
    }

}
