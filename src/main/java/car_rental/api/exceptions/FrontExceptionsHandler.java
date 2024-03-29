package car_rental.api.exceptions;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class FrontExceptionsHandler implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null){
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.BAD_REQUEST.value()){
                return "error/400";
            }

            if (statusCode == HttpStatus.UNAUTHORIZED.value()){
                return "error/401";
            }

            if (statusCode == HttpStatus.FORBIDDEN.value()){
                return "error/403";
            }

            if (statusCode == HttpStatus.NOT_FOUND.value()){
                return "error/404";
            }

        }
    return "error/error"; }
}
