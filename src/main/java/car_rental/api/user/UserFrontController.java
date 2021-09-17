package car_rental.api.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserFrontController {

    CustomUserDetailsService customUserDetailsService;

    public UserFrontController(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }
    
    @GetMapping("/user")
    public String getUsers(Model model){
        model.addAttribute("users", customUserDetailsService.getActiveUsers());
        return "get-users";
    }

    @PostMapping("/set/user/role")
    public String setRole(@RequestParam(value = "roles", required = false) String[] strings, UserSetRolesWrapper userSetRolesWrapper){
        customUserDetailsService.setRoles(userSetRolesWrapper, strings);
        return "redirect:/home";
    }

    @GetMapping("/info/user")
    public String getUserInfo(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserApp userApp = (UserApp)(authentication.getPrincipal());
        model.addAttribute("user", userApp);
        return "user-details";
    }

}
