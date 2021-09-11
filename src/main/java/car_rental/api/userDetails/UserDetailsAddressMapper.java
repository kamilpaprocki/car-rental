package car_rental.api.userDetails;

import car_rental.api.utils.DTOMapper;

public class UserDetailsAddressMapper implements DTOMapper<UserDetailsAddress, UserDetailsAddressDTO> {


    @Override
    public UserDetailsAddressDTO map(UserDetailsAddress from) {
        return UserDetailsAddressDTO
                .builder()
                .id(from.getId().toString())
                .street(from.getStreet())
                .streetNumber(from.getStreetNumber())
                .apartmentNumber(from.getApartmentNumber())
                .postalCode(from.getPostalCode())
                .city(from.getCity())
                .build();
    }

    @Override
    public UserDetailsAddress reverse(UserDetailsAddressDTO from){
        UserDetailsAddress userDetailsAddress = new UserDetailsAddress();
        if (from.getId() != null){
            userDetailsAddress.setId(Long.parseLong(from.getId()));
        }
        userDetailsAddress.setStreet(from.getStreet());
        userDetailsAddress.setStreetNumber(from.getStreetNumber());
        userDetailsAddress.setApartmentNumber(from.getApartmentNumber());
        userDetailsAddress.setPostalCode(from.getPostalCode());
        userDetailsAddress.setCity(from.getCity());
        return userDetailsAddress;
    }
}
