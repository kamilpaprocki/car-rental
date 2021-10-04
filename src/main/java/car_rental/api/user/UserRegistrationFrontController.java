package car_rental.api.user;

import car_rental.api.exceptions.UserAlreadyExistException;
import car_rental.api.userDetails.UserDetailsDTO;
import car_rental.api.userDetails.UserDetailsMapper;
import car_rental.api.userDetails.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class UserRegistrationFrontController {

    @Autowired
    AuthenticationManager authenticationManager;

    CustomUserDetailsService customUserDetailsService;
    UserDetailsService userDetailsService;

    public UserRegistrationFrontController(CustomUserDetailsService customUserDetailsService, UserDetailsService userDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/registration")
public String register(Model model){
    model.addAttribute("userDTO", new UserRegisterDTO());
    return  "registration";
}

@PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("userDTO") UserRegisterDTO userRegisterDTO,
                               BindingResult bindingResult, HttpServletRequest request){
    if (bindingResult.hasErrors()){
        return "registration";
    }
    try {
        customUserDetailsService.registerUser(userRegisterDTO);
    }catch(UserAlreadyExistException uE){
            bindingResult.addError(new ObjectError("alreadyExist", "There is already an account registered with that email/username"));
            return "registration";
        }
    try{
        authenticateUserAfterRegistration(userRegisterDTO, request);
    }catch (Exception e){
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong username or password");
    }
    return "redirect:/home?info=registered";
}

@GetMapping("/registration-user-details")
@PreAuthorize("isAuthenticated()")
    public String registrationUserDetails(Model model, @RequestParam(required = false) String info){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserApp userApp = (UserApp)authentication.getPrincipal();
        if (userApp.getUserDetails() != null){
            return "redirect:/home";
        }

        model.addAttribute("userDetailsDTO", new UserDetailsDTO());
        model.addAttribute("info", info);
        return "registration-user-details";
}

@PostMapping("/registration-user-details")
@PreAuthorize("isAuthenticated()")
    public String registrationClientInfo(@ModelAttribute("userDetailsDTO") @Valid UserDetailsDTO userDetailsDTO,
                                         BindingResult bindingResult){
        if (bindingResult.hasErrors() ){
            return "registration-user-details";
        }

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserApp currentUser = (UserApp)(authentication.getPrincipal());

    UserAppDTO registerUserDetails = customUserDetailsService.addUserDetails(currentUser.getId(), userDetailsDTO);
    currentUser.setUserDetails(new UserDetailsMapper().mapToDAO(registerUserDetails.getUserDetailsDTO()));

        return "redirect:/home?info=details";
}

private void authenticateUserAfterRegistration(UserRegisterDTO userRegisterDTO, HttpServletRequest request){
    UserDetails user = customUserDetailsService.loadUserByUsername(userRegisterDTO.getUsername());
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userRegisterDTO.getUsername(), userRegisterDTO.getPassword(), user.getAuthorities());
    request.getSession();
    authenticationToken.setDetails(new WebAuthenticationDetails(request));
    Authentication authentication = authenticationManager.authenticate(authenticationToken);

    if (authenticationToken.isAuthenticated()){
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
    request.setAttribute("username", user.getUsername());
    request.setAttribute("authorities", authenticationToken.getAuthorities());

}

}
