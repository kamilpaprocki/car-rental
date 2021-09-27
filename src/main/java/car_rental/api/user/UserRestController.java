package car_rental.api.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserRestController {

    CustomUserDetailsService customUserDetailsService;

    public UserRestController(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @GetMapping("/user")
    @ResponseBody
    public ResponseEntity<UserAppDTO> getUserById(@RequestParam Long id){
        UserAppDTO userApp = customUserDetailsService.getUserAppById(id);
        if (userApp == null){
            throw new UsernameNotFoundException("There is no user with id: " + id);
        }
        return new ResponseEntity<>(userApp, HttpStatus.OK);
    }
}
