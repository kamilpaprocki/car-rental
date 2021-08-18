package car_rental.api.utils;

import car_rental.api.client.ClientDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DataFormatValidator implements ConstraintValidator<DateFormat, ClientDTO> {

    private String message;

    @Override
    public void initialize(DateFormat constraintAnnotation) {
            message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(ClientDTO clientDTO, ConstraintValidatorContext constraintValidatorContext) {

        boolean isValid;
        java.text.DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        if (clientDTO.getBirthDate().isEmpty()) {
            isValid = false;
            constraintValidatorContext.disableDefaultConstraintViolation();
            message = "This can not be empty.";
            constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return isValid;
        }


        try{
            dateFormat.setLenient(false);
            Date birthDate = dateFormat.parse(clientDTO.getBirthDate());
            Date now = Calendar.getInstance().getTime();
            if (!birthDate.before(now)){
                isValid = false;
                constraintValidatorContext.disableDefaultConstraintViolation();
                message = "Birth date can not to be after actual date.";
                constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
                return isValid;
            }

            LocalDate localBirthDate = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if(Period.between(localBirthDate, LocalDate.now()).getYears() <18 ){
                isValid = false;
                constraintValidatorContext.disableDefaultConstraintViolation();
                message = "You must be 18 years old.";
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
