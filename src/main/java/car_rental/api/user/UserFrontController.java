package car_rental.api.user;

import car_rental.api.exceptions.UserAlreadyExistException;
import car_rental.api.userDetails.UserDetailsDTO;
import car_rental.api.utils.ChangePasswordWrapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class UserFrontController {

    private final CustomUserDetailsService customUserDetailsService;

    public UserFrontController(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }
    
    @GetMapping("/user")
    @PreAuthorize("hasRole('ADMIN')")
    public String getUsers(Model model){
        model.addAttribute("users", customUserDetailsService.getActiveUsers());
        model.addAttribute("userSetRoleWrapper", new UserSetRolesWrapper());
        return "get-users";
    }

    @PostMapping("/set/user/role")
    @PreAuthorize("hasRole('ADMIN')")
    public String setRole(@RequestParam(value = "roles", required = false) String[] strings, UserSetRolesWrapper userSetRolesWrapper){
        customUserDetailsService.setRoles(Long.parseLong(userSetRolesWrapper.getId()), strings);
        return "redirect:/home";
    }

    @GetMapping("/info/user")
    @PreAuthorize("isAuthenticated()")
    public String getUserInfo(Model model, @RequestParam(required = false) String info){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserApp userApp = (UserApp)(authentication.getPrincipal());
        if (userApp.getUserDetails() == null){
            return "redirect:/registration-user-details?info=empty";
        }
        model.addAttribute("user", userApp);
        model.addAttribute("info", info);
        return "user-details";
    }

    @GetMapping("/change/password")
    @PreAuthorize("isAuthenticated()")
    public String getChangePasswordForm(Model model){
        model.addAttribute("changePasswordWrapper",new ChangePasswordWrapper());
        return "change-password";
    }

    @PostMapping("/change/password")
    @PreAuthorize("isAuthenticated()")
    public String changePassword(@ModelAttribute("changePasswordWrapper") @Valid ChangePasswordWrapper changePasswordWrapper, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "change-password";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserApp userApp = (UserApp)(authentication.getPrincipal());
        customUserDetailsService.changePassword(userApp.getId(), changePasswordWrapper);
        return "redirect:/info/user?info=changed";
    }

    @GetMapping("/change/email")
    @PreAuthorize("isAuthenticated()")
    public String getChangeEmailForm(Model model){
        model.addAttribute("email", new ChangeEmailWrapper());
        return "change-email";
    }

    @PostMapping("/change/email")
    @PreAuthorize("isAuthenticated()")
    public String changeEmail(@ModelAttribute("email") @Valid ChangeEmailWrapper changeEmailWrapper, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "change-email";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserApp userApp = (UserApp)(authentication.getPrincipal());

       try{
           customUserDetailsService.changeEmail(userApp.getId(), changeEmailWrapper.getEmail());
        }catch (UserAlreadyExistException e){
           bindingResult.addError(new ObjectError("alreadyExist", "There is already an account registered with that email/username"));
           return "change-email";
       }
        return "redirect:/info/user?info=changed";
    }

    @GetMapping("/change/details")
    @PreAuthorize("isAuthenticated()")
    public String getChangeDetails(Model model, @RequestParam(required = false) String id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserApp userApp = (UserApp)authentication.getPrincipal();
        if (userApp.getUserDetails() == null){
            return "redirect:/registration-user-details";
        }

        model.addAttribute("userDetailsId", id);
        model.addAttribute("userDetails", new UserDetailsDTO());
        return "change-details";
    }

    @PostMapping("/change/details")
    @PreAuthorize("isAuthenticated()")
    public String changeDetails(@ModelAttribute("userDetails") @Valid UserDetailsDTO userDetailsDTO, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("userDetailsId", userDetailsDTO.getId());
            return "change-details";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserApp userApp = (UserApp)(authentication.getPrincipal());
        customUserDetailsService.updateUserDetails(userApp.getId(), userDetailsDTO);
        return "redirect:/info/user?info=changed";
    }

}
