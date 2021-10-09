package car_rental.api.validators;

import car_rental.api.userDetails.UserDetailsDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class BirthDateFormatValidator implements ConstraintValidator<DateFormatChecker, UserDetailsDTO> {

    private String message;

    @Override
    public void initialize(DateFormatChecker constraintAnnotation) {
            message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(UserDetailsDTO userDetailsDTO, ConstraintValidatorContext constraintValidatorContext) {

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        if (userDetailsDTO.getBirthDate().isEmpty()) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            message = "Birth date can not be empty.";
            constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }


        try{
            dateFormat.setLenient(false);
            Date birthDate = dateFormat.parse(userDetailsDTO.getBirthDate());
            Date now = Calendar.getInstance().getTime();
            if (!birthDate.before(now)){
                constraintValidatorContext.disableDefaultConstraintViolation();
                message = "Birth date can not to be after actual date.";
                constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
                return false;
            }

            LocalDate localBirthDate = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if(Period.between(localBirthDate, LocalDate.now()).getYears() <18 ){
                constraintValidatorContext.disableDefaultConstraintViolation();
                message = "You must be 18 years old.";
                constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
                return false;
            }
            return true;
        }catch(ParseException e){
            return false;
        }
    }
}
