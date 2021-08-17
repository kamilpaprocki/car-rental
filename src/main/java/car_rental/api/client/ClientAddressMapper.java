package car_rental.api.client;

import car_rental.api.utils.DTOMapper;

public class ClientAddressMapper implements DTOMapper<ClientAddress, ClientAddressDTO> {


    @Override
    public ClientAddressDTO map(ClientAddress from) {
        return ClientAddressDTO
                .builder()
                .street(from.getStreet())
                .streetNumber(from.getStreetNumber())
                .apartmentNumber(from.getApartmentNumber())
                .postalCode(from.getPostalCode())
                .city(from.getCity())
                .build();
    }

    @Override
    public ClientAddress reverse(ClientAddressDTO from){
        ClientAddress clientAddress = new ClientAddress();
        clientAddress.setStreet(from.getStreet());
        clientAddress.setStreetNumber(from.getStreetNumber());
        clientAddress.setApartmentNumber(from.getApartmentNumber());
        clientAddress.setPostalCode(from.getPostalCode());
        clientAddress.setCity(from.getCity());
        return clientAddress;
    }
}
