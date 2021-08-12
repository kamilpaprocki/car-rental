package car_rental.api.user;

import car_rental.api.exceptions.UserAlreadyExistException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UserFrontController {

    CustomUserDetailsService customUserDetailsService;

    public UserFrontController(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @GetMapping("/registration")
public String register(Model model){
    model.addAttribute("userDTO", new UserAppDTO());
    return  "registration";
}

@PostMapping("/registration")
    public String registration(@ModelAttribute("userDTO") @Valid UserAppDTO userAppDTO, BindingResult bindingResult){
    if (bindingResult.hasErrors()){
        return "registration";
    }
    try {
            customUserDetailsService.registerUser(userAppDTO);
        }catch(UserAlreadyExistException uE){
            bindingResult.addError(new ObjectError("alreadyExist", "There is already an account registered with that email/username"));
            return "registration";
        }
    return "redirect:/home?info=registered";
}

}
