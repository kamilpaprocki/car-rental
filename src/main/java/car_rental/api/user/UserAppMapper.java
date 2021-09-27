package car_rental.api.user;

import car_rental.api.userDetails.UserDetailsMapper;
import car_rental.api.utils.DTOMapper;
import car_rental.api.utils.DateParser;

public class UserAppMapper implements DTOMapper<UserApp, UserAppDTO> {
    @Override
    public UserAppDTO mapToDTO(UserApp from) {
        if(from == null){
            return null;
        }
        return UserAppDTO.builder()
                .id(from.getId())
                .email(from.getEmail())
                .username(from.getUsername())
                .password(from.getPassword())
                .registeredDate(new DateParser().parseDateToStringDTO(from.getRegisteredDate()))
                .isActive(String.valueOf(from.isActive()))
                .roles(from.getRoles())
                .userDetailsDTO(new UserDetailsMapper().mapToDTO(from.getUserDetails()))
                .hasActiveRent(String.valueOf(from.isHasActiveRent()))
                .build();
    }

    @Override
    public UserApp mapToDAO(UserAppDTO from) {
      return UserApp.builder()
              .id(from.getId())
              .email(from.getEmail())
              .username(from.getUsername())
              .password(from.getPassword())
              .registeredDate(new DateParser().parseStringToDateDAO(from.getRegisteredDate()))
              .isActive(Boolean.parseBoolean(from.getIsActive()))
              .roles(from.getRoles())
              .userDetails(new UserDetailsMapper().mapToDAO(from.getUserDetailsDTO()))
              .hasActiveRents(Boolean.parseBoolean(from.getHasActiveRent()))
              .build();
    }
}
