package car_rental.api.rents;

public class AddressMapper {

    public AddressDTO mapToDTO(Address from) {
        return AddressDTO.builder()
                .id(from.getId())
                .street(from.getStreet())
                .streetNumber(from.getStreetNumber())
                .postalCode(from.getPostalCode())
                .city(from.getCity())
                .build();
                }

    public RentAddress mapAddressToRentAddressDAO(AddressDTO from) {
        return RentAddress.builder()
                .id(from.getId())
                .city(from.getCity())
                .postalCode(from.getPostalCode())
                .street(from.getStreet())
                .streetNumber(from.getStreetNumber())
                .build();
    }

    public ReturnAddress mapAddressToReturnAddressDAO(AddressDTO from) {
        return ReturnAddress.builder()
                .id(from.getId())
                .city(from.getCity())
                .postalCode(from.getPostalCode())
                .street(from.getStreet())
                .streetNumber(from.getStreetNumber())
                .build();
    }
}
