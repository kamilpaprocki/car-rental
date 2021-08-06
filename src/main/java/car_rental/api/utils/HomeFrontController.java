package car_rental.api.utils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeFrontController {

    @GetMapping("/home")
    public String getHomePage(Model model, @RequestParam(required = false) String info){
        model.addAttribute("info", info);
        return "index";
    }
}
