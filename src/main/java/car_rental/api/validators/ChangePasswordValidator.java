package car_rental.api.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ChangePasswordValidator implements ConstraintValidator<PasswordMatcher, ChangePasswordWrapper> {
    @Override
    public void initialize(PasswordMatcher constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(ChangePasswordWrapper changePasswordWrapper, ConstraintValidatorContext constraintValidatorContext) {
        return changePasswordWrapper.getPassword().equals(changePasswordWrapper.getConfirmPassword());
    }
}
