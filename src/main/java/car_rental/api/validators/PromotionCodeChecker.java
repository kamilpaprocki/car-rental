package car_rental.api.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = PromotionCodeValidator.class)
@Documented
public @interface PromotionCodeChecker {
    String message() default "Wrong or used promotion code.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
