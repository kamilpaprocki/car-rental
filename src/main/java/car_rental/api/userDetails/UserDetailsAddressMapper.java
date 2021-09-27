package car_rental.api.userDetails;

import car_rental.api.utils.DTOMapper;

public class UserDetailsAddressMapper implements DTOMapper<UserDetailsAddress, UserDetailsAddressDTO> {


    @Override
    public UserDetailsAddressDTO mapToDTO(UserDetailsAddress from) {
        return UserDetailsAddressDTO
                .builder()
                .id(String.valueOf(from.getId()))
                .street(from.getStreet())
                .streetNumber(from.getStreetNumber())
                .apartmentNumber(from.getApartmentNumber())
                .postalCode(from.getPostalCode())
                .city(from.getCity())
                .build();
    }

    @Override
    public UserDetailsAddress mapToDAO(UserDetailsAddressDTO from){
return UserDetailsAddress.builder()
        .id(from.getId())
        .street(from.getStreet())
        .streetNumber(from.getStreetNumber())
        .apartmentNumber(from.getApartmentNumber())
        .postalCode(from.getPostalCode())
        .city(from.getCity())
        .build();
    }
}
