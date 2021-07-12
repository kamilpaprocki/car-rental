package car_rental.api.exceptions;

public class PromotionCodeNotFoundException extends RuntimeException{
    public PromotionCodeNotFoundException(String message) {
        super(message);
    }
}
