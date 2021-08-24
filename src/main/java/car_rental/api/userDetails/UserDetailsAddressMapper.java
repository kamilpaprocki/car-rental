package car_rental.api.userDetails;

import car_rental.api.utils.DTOMapper;

public class UserDetailsAddressMapper implements DTOMapper<UserDetailsAddress, UserDetailsAddressDTO> {


    @Override
    public UserDetailsAddressDTO map(UserDetailsAddress from) {
        return UserDetailsAddressDTO
                .builder()
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
        userDetailsAddress.setStreet(from.getStreet());
        userDetailsAddress.setStreetNumber(from.getStreetNumber());
        userDetailsAddress.setApartmentNumber(from.getApartmentNumber());
        userDetailsAddress.setPostalCode(from.getPostalCode());
        userDetailsAddress.setCity(from.getCity());
        return userDetailsAddress;
    }
}
