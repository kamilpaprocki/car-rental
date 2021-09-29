package car_rental.api.utils;

import car_rental.api.user.UserApp;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeFrontController {

    @GetMapping("/home")
    public String getHomePage(Model model, @RequestParam(required = false) String info){
        model.addAttribute("info", info);
        if ("login".equals(info) || "registered".equals(info)){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserApp userApp = (UserApp) (authentication.getPrincipal());
            if (userApp.getUserDetails() == null){
                return "redirect:/registration-user-details?info=empty";
            }
        }
        return "index";
    }
}
