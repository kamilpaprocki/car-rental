package car_rental.api.utils;

import car_rental.api.rents.RentDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RentDateFormatValidator implements ConstraintValidator<DateFormat, RentDTO> {

    private String message;


    @Override
    public void initialize(DateFormat constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(RentDTO rentDTO, ConstraintValidatorContext constraintValidatorContext) {
        boolean isValid;
        java.text.DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        if (rentDTO.getRentDate().isEmpty()) {
            isValid = false;
            constraintValidatorContext.disableDefaultConstraintViolation();
            message = "Rent date can not be empty.";
            constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return isValid;
        }
        if (rentDTO.getPlannedReturnDate().isEmpty()) {
            isValid = false;
            constraintValidatorContext.disableDefaultConstraintViolation();
            message = "Return date can not be empty.";
            constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return isValid;
        }


        try{
            dateFormat.setLenient(false);
            Date rentDate = dateFormat.parse(rentDTO.getRentDate());
            Date now = Calendar.getInstance().getTime();
            if (rentDate.before(now)){
                isValid = false;
                constraintValidatorContext.disableDefaultConstraintViolation();
                message = "Rent date can not to be before actual date.";
                constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
                return isValid;
            }

            Date returnDate = dateFormat.parse(rentDTO.getPlannedReturnDate());
            if (returnDate.before(now)){
                isValid = false;
                constraintValidatorContext.disableDefaultConstraintViolation();
                message = "Return date can not to be before actual date.";
                constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
                return isValid;
            }

            if (returnDate.before(rentDate)){
                isValid = false;
                constraintValidatorContext.disableDefaultConstraintViolation();
                message = "Return date can not to be before rent date.";
                constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
                return isValid;
            }


            isValid = true;
        }catch(ParseException e){
            isValid = false;
            return isValid;
        }
        return isValid;
    }
}
