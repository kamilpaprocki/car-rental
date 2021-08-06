package car_rental.api.client;

import car_rental.api.utils.DTOMapper;

public class ClientMapper implements DTOMapper<Client, ClientDTO> {
    @Override
    public ClientDTO from(Client from) {

        return ClientDTO.builder()
                .name(from.getName())
                .lastName(from.getLastName())
                .nationality(from.getNationality())
                .address(new ClientAddressMapper().from(from.getClientAddress()).toString())
                .drivingLicenseNumber(from.getDrivingLicenseNumber())
                .identityCardNumber(from.getIdentityCardNumber())
                .peselNumber(from.getPeselNumber())
                .phoneNumber(from.getPhoneNumber())
                .birthDate(from.getBirthDate().toString())
                .build();
    }
}
