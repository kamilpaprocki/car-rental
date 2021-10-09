package car_rental.api.validators;

import car_rental.api.promotionCode.PromotionCodeDTO;
import car_rental.api.promotionCode.PromotionCodeService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PromotionCodeValidator implements ConstraintValidator<PromotionCodeChecker, PromotionCodeDTO> {

    @Autowired
    PromotionCodeService promotionCodeService;

    @Override
    public void initialize(PromotionCodeChecker constraint) {
    }

    @Override
    public boolean isValid(PromotionCodeDTO promotionCodeDTO, ConstraintValidatorContext context) {
        if (!promotionCodeDTO.getPromotionCodeDTO().isEmpty()){
            return promotionCodeService.isCorrectAndAvailablePromotionCode(promotionCodeDTO.getPromotionCodeDTO());
        }
        return true;
         }
}
