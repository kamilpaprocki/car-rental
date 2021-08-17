package car_rental.api.user;

import car_rental.api.client.ClientDTO;
import car_rental.api.client.ClientMapper;
import car_rental.api.client.ClientService;
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
    ClientService clientService;

    public UserRegistrationFrontController(CustomUserDetailsService customUserDetailsService, ClientService clientService) {
        this.customUserDetailsService = customUserDetailsService;
        this.clientService = clientService;
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
    return "redirect:/registration-client-info";
}

@GetMapping("/registration-client-info")
    public String registrationClient(Model model){
        model.addAttribute("clientDTO", new ClientDTO());
        return "registration-client-info";
}

@PostMapping("/registration-client-info")
    public String registrationClientInfo(@ModelAttribute("newUser") UserApp userApp,
                                         @ModelAttribute("clientDTO") @Valid ClientDTO clientDTO,
                                         BindingResult bindingResult){

        if (bindingResult.hasErrors() ){
            return "registration-client-info";
        }

        customUserDetailsService.addClientInfo(userApp, new ClientMapper().reverse(clientDTO));
        return "redirect:/home?info=registered";
}

@ModelAttribute("newUser")
public UserApp userApp(){
        return new UserApp();
}

}
