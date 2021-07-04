package car_rental.api.PromotionCode;

public class WrongPromotionCodeException extends RuntimeException {
    public WrongPromotionCodeException(String message) {
        super(message);
    }
}
