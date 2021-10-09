package car_rental.api.user;

import car_rental.api.exceptions.UserAlreadyExistException;
import car_rental.api.exceptions.UserNotFoundExceptions;
import car_rental.api.exceptions.WrongArgumentException;
import car_rental.api.userDetails.UserDetailsDTO;
import car_rental.api.validators.ChangePasswordWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserFrontController {

    private final CustomUserDetailsService customUserDetailsService;

    private final static Logger logger = LoggerFactory.getLogger(UserFrontController.class);
    public UserFrontController(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }
    
    @GetMapping("/user")
    @PreAuthorize("hasRole('ADMIN')")
    public String getUsers(Model model){
        List<UserAppDTO> userApps = customUserDetailsService.getActiveUsers();
        if(userApps.isEmpty()){
            logger.error("List of active users is empty.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no active users.");
        }
        logger.info("Return {} active users.", userApps.size());
        model.addAttribute("users", userApps);
        return "user/get-users";
    }

    @PostMapping("/set/user/role")
    @PreAuthorize("hasRole('ADMIN')")
    public String setRole(@RequestParam(value = "roles", required = false) String[] strings, @RequestParam String userid){
        try{
            customUserDetailsService.setRoles(Long.parseLong(userid), strings);
        }catch (WrongArgumentException | UserNotFoundExceptions e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        logger.info("Set {} roles more than USER ROLE to user  with id {}.", strings.length, userid);
        return "redirect:/home";
    }

    @GetMapping("/info/user")
    @PreAuthorize("isAuthenticated()")
    public String getUserInfo(Model model, @RequestParam(required = false) String info){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserApp userApp = (UserApp)(authentication.getPrincipal());
        if (userApp.getUserDetails() == null){
            logger.error("User with id {} does not have completed details.", userApp.getId());
            return "redirect:/registration-user-details?info=empty";
        }
        model.addAttribute("user", userApp);
        model.addAttribute("info", info);
        return "user/user-details";
    }

    @GetMapping("/change/password")
    @PreAuthorize("isAuthenticated()")
    public String getChangePasswordForm(Model model){
        model.addAttribute("changePasswordWrapper",new ChangePasswordWrapper());
        return "user/change-password";
    }

    @PostMapping("/change/password")
    @PreAuthorize("isAuthenticated()")
    public String changePassword(@ModelAttribute("changePasswordWrapper") @Valid ChangePasswordWrapper changePasswordWrapper, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            for (ObjectError error : bindingResult.getAllErrors()){
                logger.error(error.getDefaultMessage());
            }
            return "user/change-password";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserApp userApp = (UserApp)(authentication.getPrincipal());
        customUserDetailsService.changePassword(userApp.getId(), changePasswordWrapper);
        logger.info("User with id {} change password.", userApp.getId());
        return "redirect:/info/user?info=changed";
    }

    @GetMapping("/change/email")
    @PreAuthorize("isAuthenticated()")
    public String getChangeEmailForm(Model model){
        model.addAttribute("email", new ChangeEmailWrapper());
        return "user/change-email";
    }

    @PostMapping("/change/email")
    @PreAuthorize("isAuthenticated()")
    public String changeEmail(@ModelAttribute("email") @Valid ChangeEmailWrapper changeEmailWrapper, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            for(ObjectError error : bindingResult.getAllErrors()){
                logger.error(error.getDefaultMessage());
            }
            return "user/change-email";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserApp userApp = (UserApp)(authentication.getPrincipal());

       try{
           customUserDetailsService.changeEmail(userApp.getId(), changeEmailWrapper.getEmail());
           logger.info("User with id {} change email.", userApp.getId());
        }catch (UserAlreadyExistException e){
           bindingResult.addError(new ObjectError("alreadyExist", "There is already an account registered with that email/username"));
           logger.error("User with id {} try to change to existing email.", userApp.getId());
           return "user/change-email";
       }
        return "redirect:/info/user?info=changed";
    }

    @GetMapping("/change/details")
    @PreAuthorize("isAuthenticated()")
    public String getChangeDetails(Model model, @RequestParam(required = false) String id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserApp userApp = (UserApp)authentication.getPrincipal();
        if (userApp.getUserDetails() == null){
            logger.error("User with id {} does not have completed details.", userApp.getId());
            return "redirect:/registration-user-details";
        }

        model.addAttribute("userDetailsId", id);
        model.addAttribute("userDetails", new UserDetailsDTO());
        return "user/change-details";
    }

    @PostMapping("/change/details")
    @PreAuthorize("isAuthenticated()")
    public String changeDetails(@ModelAttribute("userDetails") @Valid UserDetailsDTO userDetailsDTO, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            for (ObjectError error : bindingResult.getAllErrors()){
                logger.error(error.getDefaultMessage());
            }
            model.addAttribute("userDetailsId", userDetailsDTO.getId());
            return "user/change-details";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserApp userApp = (UserApp)(authentication.getPrincipal());
        customUserDetailsService.updateUserDetails(userApp.getId(), userDetailsDTO);
        logger.info("User with id {} update details.", userApp.getId());
        return "redirect:/info/user?info=changed";
    }

}
