package car_rental.api.user;

import car_rental.api.userDetails.UserDetailsMapper;
import car_rental.api.utils.DTOMapper;
import car_rental.api.utils.DateParser;

public class UserAppMapper implements DTOMapper<UserApp, UserAppDTO> {
    @Override
    public UserAppDTO map(UserApp from) {
        return UserAppDTO.builder()
                .id(from.getId().toString())
                .username(from.getUsername())
                .password(from.getPassword())
                .registeredDate(from.getRegistredDate().toString())
                .isActive(Boolean.toString(from.isActive()))
                .roles(from.getRoles())
                .userDetailsDTO(new UserDetailsMapper().map(from.getUserDetails()))
                .build();
    }

    @Override
    public UserApp reverse(UserAppDTO from) {
        UserApp userApp = new UserApp();
        userApp.setId(Long.parseLong(from.getId()));
        userApp.setUsername(from.getUsername());
        userApp.setEmail(from.getEmail());
        userApp.setPassword(from.getPassword());
        userApp.setRegistredDate(new DateParser().parseDate(from.getRegisteredDate()));
        userApp.setIsActive(Boolean.getBoolean(from.getIsActive()));
        userApp.setRoles(from.getRoles());
        userApp.setUserDetails(new UserDetailsMapper().reverse(from.getUserDetailsDTO()));

        return userApp;
    }
}
