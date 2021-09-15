package car_rental.api.utils;

import car_rental.api.rents.CarReturnOdometerWrapper;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CarRentOdometerValidator implements ConstraintValidator<CarRentOdometerChecker, CarReturnOdometerWrapper> {

    private String message;

    @Override
    public void initialize(CarRentOdometerChecker constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(CarReturnOdometerWrapper carReturnOdometerWrapper, ConstraintValidatorContext constraintValidatorContext) {

        if(carReturnOdometerWrapper.getCurrentOdometer().isEmpty()){
            constraintValidatorContext.disableDefaultConstraintViolation();
            message = "This can not be empty.";
            constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }

        return Long.parseLong(carReturnOdometerWrapper.getCurrentOdometer()) > Long.parseLong(carReturnOdometerWrapper.getLastOdometer());
    }
}
