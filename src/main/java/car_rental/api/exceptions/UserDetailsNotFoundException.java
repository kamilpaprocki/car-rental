package car_rental.api.exceptions;

public class UserDetailsNotFoundException extends RuntimeException{
    public UserDetailsNotFoundException(String message) {
        super(message);
    }
}
