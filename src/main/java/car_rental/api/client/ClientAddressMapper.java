package car_rental.api.client;

import car_rental.api.utils.DTOMapper;

public class ClientAddressMapper implements DTOMapper<ClientAddress, ClientAddressDTO> {


    @Override
    public ClientAddressDTO from(ClientAddress from) {
        return ClientAddressDTO
                .builder()
                .street(from.getStreet())
                .streetNumber(from.getStreetNumber())
                .apartmentNumber(from.getApartmentNumber())
                .postalCode(from.getPostalCode())
                .city(from.getCity())
                .build();
    }
}
