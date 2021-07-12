package car_rental.api.promotionCode;

public class WrongPromotionCodeException extends RuntimeException {
    public WrongPromotionCodeException(String message) {
        super(message);
    }
}
