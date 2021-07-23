package car_rental.api.utils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeFrontController {

    @GetMapping("/home")
    public String getHomePage(){
        return "index";
    }
}
