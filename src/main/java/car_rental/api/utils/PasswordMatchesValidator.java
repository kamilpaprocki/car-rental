package car_rental.api.utils;


import car_rental.api.user.UserAppDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatcher, UserAppDTO> {
    @Override
    public void initialize(PasswordMatcher constraintAnnotation) {

    }

    @Override
    public boolean isValid(UserAppDTO userAppDTO, ConstraintValidatorContext constraintValidatorContext) {
        return userAppDTO.getPassword().equals(userAppDTO.getConfirmPassword());
    }


}
