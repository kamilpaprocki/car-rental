package car_rental.api.user;

import car_rental.api.userDetails.UserDetailsDTO;
import car_rental.api.userDetails.UserDetailsMapper;
import car_rental.api.userDetails.UserDetailsService;
import car_rental.api.exceptions.UserAlreadyExistException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.validation.Valid;

@Controller
@SessionAttributes("newUser")
public class UserRegistrationFrontController {

    CustomUserDetailsService customUserDetailsService;
    UserDetailsService userDetailsService;

    public UserRegistrationFrontController(CustomUserDetailsService customUserDetailsService, UserDetailsService userDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/registration")
public String register(Model model){
    model.addAttribute("userDTO", new UserAppDTO());
    return  "registration";
}

@PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("userDTO") UserAppDTO userAppDTO,
                               BindingResult bindingResult,
                               @ModelAttribute("newUser") UserApp userApp,
                                Model model){
    if (bindingResult.hasErrors()){
        return "registration";
    }
    try {
        userApp = customUserDetailsService.registerUser(userAppDTO);
        model.addAttribute("newUser", userApp);
    }catch(UserAlreadyExistException uE){
            bindingResult.addError(new ObjectError("alreadyExist", "There is already an account registered with that email/username"));
            return "registration";
        }
    return "redirect:/registration-user-details";
}

@GetMapping("/registration-user-details")
    public String registrationUserDetails(Model model){
        model.addAttribute("userDetailsDTO", new UserDetailsDTO());
        return "registration-user-details";
}

@PostMapping("/registration-user-details")
    public String registrationClientInfo(@ModelAttribute("newUser") UserApp userApp,
                                         @ModelAttribute("userDetailsDTO") @Valid UserDetailsDTO userDetailsDTO,
                                         BindingResult bindingResult){

        if (bindingResult.hasErrors() ){
            return "registration-user-details";
        }

        customUserDetailsService.addUserDetails(userApp, new UserDetailsMapper().reverse(userDetailsDTO));
        return "redirect:/home?info=registered";
}

@ModelAttribute("newUser")
public UserApp userApp(){
        return new UserApp();
}

}
