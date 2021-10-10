package car_rental.api.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WrongArgumentException extends RuntimeException{

    private Logger logger = LoggerFactory.getLogger(WrongArgumentException.class);

    public WrongArgumentException(String message) {
        super(message);
        logger.error(message);
    }
}

