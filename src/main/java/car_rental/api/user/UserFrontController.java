package car_rental.api.user;

import car_rental.api.utils.ChangePasswordWrapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

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
    public String getUserInfo(Model model, @RequestParam(required = false) String info){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserApp userApp = (UserApp)(authentication.getPrincipal());
        model.addAttribute("user", userApp);
        model.addAttribute("info", info);
        return "user-details";
    }

    @GetMapping("/change/password")
    public String getChangePasswordForm(Model model){
        model.addAttribute("changePasswordWrapper",new ChangePasswordWrapper());
        return "change-password";
    }

    @PostMapping("/change/password")
    public String changePassword(@ModelAttribute("changePasswordWrapper") @Valid ChangePasswordWrapper changePasswordWrapper, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "change-password";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserApp userApp = (UserApp)(authentication.getPrincipal());
        customUserDetailsService.changePassword(userApp.getId(), changePasswordWrapper);
        return "redirect:/info/user?info=changed";
    }

}
