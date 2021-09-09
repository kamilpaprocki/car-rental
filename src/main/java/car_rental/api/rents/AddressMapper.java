package car_rental.api.rents;

import car_rental.api.utils.DTOMapper;

public class AddressMapper implements DTOMapper<Address, AddressDTO> {

    @Override
    public AddressDTO map(Address from) {
        return AddressDTO.builder()
                .id(from.getId().toString())
                .street(from.getStreet())
                .streetNumber(from.getStreetNumber())
                .postalCode(from.getPostalCode())
                .city(from.getCity())
                .build();
                }

    @Override
    public RentAddress reverse(AddressDTO from) {
        RentAddress address = new RentAddress();
        address.setId(Long.parseLong(from.getId()));
        address.setStreet(from.getStreet());
        address.setStreetNumber(from.getStreetNumber());
        address.setPostalCode(from.getPostalCode());
        address.setCity(from.getCity());
        return address;
    }

    public ReturnAddress reverseToReturnAddress(AddressDTO from) {
        ReturnAddress address = new ReturnAddress();
        address.setId(Long.parseLong(from.getId()));
        address.setStreet(from.getStreet());
        address.setStreetNumber(from.getStreetNumber());
        address.setPostalCode(from.getPostalCode());
        address.setCity(from.getCity());
        return address;
    }
}
