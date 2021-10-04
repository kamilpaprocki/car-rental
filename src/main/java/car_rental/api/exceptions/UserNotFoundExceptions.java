package car_rental.api.exceptions;

public class UserNotFoundExceptions extends RuntimeException{
    public UserNotFoundExceptions(String message) {
        super(message);
    }
}
