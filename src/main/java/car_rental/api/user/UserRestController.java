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
    public ResponseEntity<UserSetRolesWrapper> getUserById(@RequestParam long id){
        UserSetRolesWrapper userSetRolesWrapper = customUserDetailsService.getUserById(id);
        if (userSetRolesWrapper == null){
            throw new UsernameNotFoundException("There is no user with id: " + id);
        }
        return new ResponseEntity<>(userSetRolesWrapper, HttpStatus.OK);
    }
}
