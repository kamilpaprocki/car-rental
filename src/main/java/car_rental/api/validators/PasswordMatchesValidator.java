package car_rental.api.validators;


import car_rental.api.user.UserRegisterDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatcher, UserRegisterDTO> {
    @Override
    public void initialize(PasswordMatcher constraintAnnotation) {

    }

    @Override
    public boolean isValid(UserRegisterDTO userRegisterDTO, ConstraintValidatorContext constraintValidatorContext) {
        return userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword());
    }


}
