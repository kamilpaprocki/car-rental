package car_rental.api.client;

import car_rental.api.exceptions.WrongDataFormatException;
import car_rental.api.utils.DTOMapper;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;

public class ClientMapper implements DTOMapper<Client, ClientDTO> {
    @Override
    public ClientDTO map(Client from) {

        return ClientDTO.builder()
                .name(from.getName())
                .lastName(from.getLastName())
                .nationality(from.getNationality())
                .address(new ClientAddressMapper().map(from.getClientAddress()))
                .drivingLicenseNumber(from.getDrivingLicenseNumber())
                .identityCardNumber(from.getIdentityCardNumber())
                .peselNumber(from.getPeselNumber())
                .phoneNumber(from.getPhoneNumber())
                .birthDate(from.getBirthDate().toString())
                .build();
    }

    @Override
    public Client reverse(ClientDTO from) {
        Client client = new Client();
        client.setName(from.getName());
        client.setLastName(from.getLastName());
        client.setNationality(from.getNationality());
        client.setAddress(new ClientAddressMapper().reverse(from.getAddress()));
        client.setDrivingLicenseNumber(from.getDrivingLicenseNumber());
        client.setIdentityCardNumber(from.getIdentityCardNumber());
        client.setPeselNumber(from.getPeselNumber());
        client.setPhoneNumber(from.getPhoneNumber());

        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        try {
            client.setBirthDate(Date.valueOf(df.parse(from.getBirthDate()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
        } catch (ParseException e) {
            throw new WrongDataFormatException("Wrong date format.");
        }
        return client;
    }
}
