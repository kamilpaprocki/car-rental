package car_rental.api.rents;

public class WrongRentException extends RuntimeException {

    public WrongRentException(String message) {
        super(message);
    }
}
