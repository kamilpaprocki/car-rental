package car_rental.api.exceptions;

public class WrongArgumentException extends RuntimeException{
    public WrongArgumentException(String message) {
        super(message);
    }
}

