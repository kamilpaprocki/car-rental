package car_rental.api.utils;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {CarRentOdometerValidator.class})
@Documented
public @interface CarRentOdometerChecker {
    String message() default "Current car odometer cannot be less than last odometer.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
