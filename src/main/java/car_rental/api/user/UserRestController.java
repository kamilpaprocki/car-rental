package car_rental.api.user;

import car_rental.api.exceptions.BadRequestException;
import car_rental.api.exceptions.UserAlreadyExistException;
import car_rental.api.exceptions.UserNotFoundExceptions;
import car_rental.api.userDetails.UserDetailsDTO;
import car_rental.api.utils.ChangePasswordWrapper;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class UserRestController {

    CustomUserDetailsService customUserDetailsService;

    public UserRestController(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @GetMapping("/user")
    @ResponseBody
    @PreAuthorize("hasAnyRole('WORKER', 'ADMIN')")
    public ResponseEntity<UserAppDTO> getUserById(@RequestParam Long id){
        UserAppDTO userApp = customUserDetailsService.getUserAppById(id);
        if (userApp == null){
            throw new UsernameNotFoundException("There is no user with id: " + id);
        }
        return new ResponseEntity<>(userApp, HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<UserAppDTO> registerUser(@Valid @RequestBody UserRegisterDTO userRegisterDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            String result = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(";"));
            throw new BadRequestException(result);
        }

        try{
            UserAppDTO userApp = customUserDetailsService.registerUser(userRegisterDTO);
            return new ResponseEntity<>(userApp, HttpStatus.CREATED);
        }catch (UserAlreadyExistException e){
            throw new BadRequestException(e.getMessage());
        }
    }

    @PostMapping("/user/details")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserAppDTO> addOrUpdateUserDetails(@RequestParam Long userid, @Valid @RequestBody UserDetailsDTO userDetailsDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
           String result = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining("; "));
            throw new BadRequestException(result);
        }
        try{
            UserAppDTO userApp;
            if(userDetailsDTO.getId() == null){
                userApp = customUserDetailsService.addUserDetails(userid, userDetailsDTO);
                return new ResponseEntity<>(userApp, HttpStatus.CREATED);
            }
            userApp = customUserDetailsService.updateUserDetails(userid, userDetailsDTO);
            return new ResponseEntity<>(userApp, HttpStatus.OK);
        }catch (UserAlreadyExistException e){
            throw new BadRequestException(e.getMessage());
        }
   }

    @PostMapping("/user/roles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserAppDTO> setUserRoles(@RequestParam Long userid, @RequestParam String[] roles){
        try{
           UserAppDTO userAppDTO = customUserDetailsService.setRoles(userid, roles);
            return new ResponseEntity<>(userAppDTO, HttpStatus.OK);
        }catch (RuntimeException e){
            throw new BadRequestException(e.getMessage());
        }
    }

    @PostMapping("/user/change/password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserAppDTO> changePassword(@RequestParam Long userid, @Valid @RequestBody ChangePasswordWrapper changePasswordWrapper, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            String result = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining("; "));
            throw new BadRequestException(result);
        }
        try{
           UserAppDTO userApp = customUserDetailsService.changePassword(userid, changePasswordWrapper);
            return new ResponseEntity<>(userApp, HttpStatus.OK);
        }catch (UsernameNotFoundException e){
            throw new BadRequestException(e.getMessage());
        }
    }
    @PostMapping("/user/change/email")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserAppDTO> changeEmail(@RequestParam Long userid, @RequestBody @Valid ChangeEmailWrapper email, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            String result = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining("; "));
            throw new BadRequestException(result);
        }
        try{
           UserAppDTO userApp = customUserDetailsService.changeEmail(userid, email.getEmail());
            return new ResponseEntity<>(userApp, HttpStatus.OK);
        }catch (UsernameNotFoundException e){
            throw new BadRequestException(e.getMessage());
        }
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserAppDTO>> getUsers(@RequestParam(required = false) String userstatus){
        List<UserAppDTO> userAppDTOS;
        if ("active".equals(userstatus)){
            userAppDTOS  = customUserDetailsService.getActiveUsers();
            if (userAppDTOS.isEmpty()){
                throw new UserNotFoundExceptions("There are no active users");
            }
            return new ResponseEntity<>(userAppDTOS, HttpStatus.OK);
        }

        if ("inactive".equals(userstatus)){
            userAppDTOS = customUserDetailsService.getInactiveUsers();
            if (userAppDTOS.isEmpty()){
                throw new UserNotFoundExceptions("There are no inactive users");
            }
            return new ResponseEntity<>(userAppDTOS, HttpStatus.OK);
        }

        if (userstatus == null){
            userAppDTOS = customUserDetailsService.getAllUsers();
            if (userAppDTOS.isEmpty()){
                throw new UserNotFoundExceptions("There are no users");
            }
            return new ResponseEntity<>(userAppDTOS, HttpStatus.OK);
        }
        throw new BadRequestException("Wrong input parameter. Input user status: " + userstatus);
    }

    @DeleteMapping("/user/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserApp> deleteUserById(@RequestParam Long userid){
        if (customUserDetailsService.deleteUserById(userid) > 0){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }


}
