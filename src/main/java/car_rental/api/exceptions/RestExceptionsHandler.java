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

    @ExceptionHandler(value = ClientNotFoundException.class)
    protected ResponseEntity<Object> handleClientNotFoundException(RuntimeException e, WebRequest request){
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
}
