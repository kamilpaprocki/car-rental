package car_rental.api.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class RestExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = CarNotFoundException.class)
    protected ResponseEntity<Object> handleCarNotFoundException(RuntimeException e, WebRequest request){
        ExceptionBody body = ExceptionBody
                .builder()
                .message(e.getMessage())
                .status(404)
                .path(request.getDescription(true))
                .timestamp(new Date().toString())
                .build();
        return handleExceptionInternal(e, body, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = BadRequestException.class)
    protected ResponseEntity<Object> handleBadRequestException(RuntimeException e, WebRequest request){
        ExceptionBody body = ExceptionBody
                .builder()
                .message(e.getMessage())
                .status(400)
                .path(request.getDescription(true))
                .timestamp(new Date().toString())
                .build();
        return handleExceptionInternal(e, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = UserDetailsNotFoundException.class)
    protected ResponseEntity<Object> handleUserDetailsNotFoundException(RuntimeException e, WebRequest request){
        ExceptionBody body = ExceptionBody
                .builder()
                .message(e.getMessage())
                .status(404)
                .path(request.getDescription(true))
                .timestamp(new Date().toString())
                .build();
        return handleExceptionInternal(e, body, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = PromotionCodeNotFoundException.class)
    protected ResponseEntity<Object> handlePromotionCodeException(RuntimeException e, WebRequest request){
        ExceptionBody body = ExceptionBody
                .builder()
                .message(e.getMessage())
                .status(404)
                .path(request.getDescription(true))
                .timestamp(new Date().toString())
                .build();
        return handleExceptionInternal(e, body, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = RentNotFoundException.class)
    protected ResponseEntity<Object> handleRentNotFoundException(RuntimeException e, WebRequest request){
        ExceptionBody body = ExceptionBody
                .builder()
                .message(e.getMessage())
                .status(404)
                .path(request.getDescription(true))
                .timestamp(new Date().toString())
                .build();
        return handleExceptionInternal(e, body, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = WrongArgumentException.class)
    protected ResponseEntity<Object> handleWrongArgumentException(RuntimeException e, WebRequest request){
        ExceptionBody body = ExceptionBody
                .builder()
                .message(e.getMessage())
                .status(404)
                .path(request.getDescription(true))
                .timestamp(new Date().toString())
                .build();
        return handleExceptionInternal(e, body, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = UserAlreadyExistException.class)
    protected ResponseEntity<Object> handleUserAlreadyExistException(RuntimeException e, WebRequest request){
        ExceptionBody body = ExceptionBody
                .builder()
                .message(e.getMessage())
                .status(400)
                .path(request.getDescription(true))
                .timestamp(new Date().toString())
                .build();
        return handleExceptionInternal(e, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = UserNotFoundExceptions.class)
    protected ResponseEntity<Object> handleUserNotFoundException(RuntimeException e, WebRequest request){
        ExceptionBody body = ExceptionBody
                .builder()
                .message(e.getMessage())
                .status(404)
                .path(request.getDescription(true))
                .timestamp(new Date().toString())
                .build();
        return handleExceptionInternal(e, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
