package car_rental.api.user;

public class UserSetRolesMapper {

    public UserSetRolesWrapper map(UserApp from) {
        UserSetRolesWrapper user = new UserSetRolesWrapper();
        user.setId(String.valueOf(from.getId()));
        user.setUsername(from.getUsername());
        user.setEmail(from.getEmail());
        return user;
    }

}
