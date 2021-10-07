package car_rental.api.validators;

import car_rental.api.rents.RentDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;

public class RentDateFormatValidator implements ConstraintValidator<DateFormatChecker, RentDTO> {

    private String message;


    @Override
    public void initialize(DateFormatChecker constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(RentDTO rentDTO, ConstraintValidatorContext constraintValidatorContext) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        if (rentDTO.getRentDate().isEmpty()) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            message = "Rent date can not be empty.";
            constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }
        if (rentDTO.getPlannedReturnDate().isEmpty()) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            message = "Return date can not be empty.";
            constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }


        try{
            dateFormat.setLenient(false);
            LocalDate rentDate = dateFormat.parse(rentDTO.getRentDate()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate now = LocalDate.now();

            if (rentDate.compareTo(now) < 0){
                constraintValidatorContext.disableDefaultConstraintViolation();
                message = "Rent date can not to be before actual date.";
                constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
                return false;
            }

            LocalDate returnDate = dateFormat.parse(rentDTO.getPlannedReturnDate()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (returnDate.compareTo(now) < 0){
                constraintValidatorContext.disableDefaultConstraintViolation();
                message = "Return date can not to be before actual date.";
                constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
                return false;
            }

            if (returnDate.compareTo(rentDate) < 0){
                constraintValidatorContext.disableDefaultConstraintViolation();
                message = "Return date can not to be before rent date.";
                constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
                return false;
            }

            return true;
        }catch(ParseException e){
            return false;
        }
    }
}
