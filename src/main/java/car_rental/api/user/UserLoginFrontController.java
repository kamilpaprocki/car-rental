package car_rental.api.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserLoginFrontController {

    private final CustomUserDetailsService customUserDetailsService;

    private final static Logger logger = LoggerFactory.getLogger(UserLoginFrontController.class);

    public UserLoginFrontController(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @GetMapping("/login")
    public String loginView(Model model, @RequestParam(required = false) String info){
        model.addAttribute("info", info);
        logger.info("User log in.");
        return "login";
}

    @GetMapping("/logout")
    public String logoutView(HttpServletRequest request, HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null){
            logger.info("User log out.");
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/login?info=logout";
    }

}
