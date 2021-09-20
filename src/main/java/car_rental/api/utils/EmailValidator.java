package car_rental.api.utils;

import car_rental.api.user.ChangeEmailWrapper;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<EmailMatcher, ChangeEmailWrapper> {
    @Override
    public void initialize(EmailMatcher constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(ChangeEmailWrapper changeEmailWrapper, ConstraintValidatorContext constraintValidatorContext) {
        return changeEmailWrapper.getEmail().equals(changeEmailWrapper.getConfirmEmail());
    }
}
