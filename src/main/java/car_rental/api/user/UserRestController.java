package car_rental.api.user;

import car_rental.api.exceptions.BadRequestException;
import car_rental.api.exceptions.UserAlreadyExistException;
import car_rental.api.exceptions.UserNotFoundExceptions;
import car_rental.api.exceptions.WrongArgumentException;
import car_rental.api.userDetails.UserDetailsDTO;
import car_rental.api.validators.ChangePasswordWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class UserRestController {

    private final CustomUserDetailsService customUserDetailsService;

    private final static Logger logger = LoggerFactory.getLogger(UserRestController.class);

    public UserRestController(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @GetMapping("/user")
    @ResponseBody
    @PreAuthorize("hasAnyRole('WORKER', 'ADMIN')")
    public ResponseEntity<UserAppDTO> getUserById(@RequestParam Long id){
        UserAppDTO userApp = customUserDetailsService.getUserAppById(id);
        if (userApp == null){
            logger.error("User with id {} not exist.", id);
            throw new UsernameNotFoundException("There is no user with id: " + id);
        }
        logger.info("Return user with id {}.", id);
        return new ResponseEntity<>(userApp, HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<UserAppDTO> registerUser(@Valid @RequestBody UserRegisterDTO userRegisterDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            for (ObjectError error : bindingResult.getAllErrors()) {
                logger.error(error.getDefaultMessage());
            }
            String result = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(";"));
            throw new BadRequestException(result);
        }

        try{
            UserAppDTO userApp = customUserDetailsService.registerUser(userRegisterDTO);
            logger.info("Create new user {}.", userRegisterDTO.getUsername());
            return new ResponseEntity<>(userApp, HttpStatus.CREATED);
        }catch (UserAlreadyExistException e){
            logger.error(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }

    @PostMapping("/user/details")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserAppDTO> addOrUpdateUserDetails(@RequestParam Long userid, @Valid @RequestBody UserDetailsDTO userDetailsDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            for (ObjectError error : bindingResult.getAllErrors()) {
                logger.error(error.getDefaultMessage());
            }
           String result = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining("; "));
            throw new BadRequestException(result);
        }
        try{
            UserAppDTO userApp;
            if(userDetailsDTO.getId() == null){
                userApp = customUserDetailsService.addUserDetails(userid, userDetailsDTO);
                logger.info("Add new details to user with id {}.", userid);
                return new ResponseEntity<>(userApp, HttpStatus.CREATED);
            }
            userApp = customUserDetailsService.updateUserDetails(userid, userDetailsDTO);
            logger.info("User with id {} update details.", userid);
            return new ResponseEntity<>(userApp, HttpStatus.OK);
        }catch (UserAlreadyExistException e){
            logger.error(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
   }

    @PostMapping("/user/roles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserAppDTO> setUserRoles(@RequestParam Long userid, @RequestParam String[] roles){
        try{
           UserAppDTO userAppDTO = customUserDetailsService.setRoles(userid, roles);
           logger.info("Set {} roles to user with id {}.", roles.length, userid);
            return new ResponseEntity<>(userAppDTO, HttpStatus.OK);
        }catch (WrongArgumentException | UsernameNotFoundException e){
            logger.error(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }

    @PostMapping("/user/change/password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserAppDTO> changePassword(@RequestParam Long userid, @Valid @RequestBody ChangePasswordWrapper changePasswordWrapper, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            for (ObjectError error : bindingResult.getAllErrors()) {
                logger.error(error.getDefaultMessage());
            }
            String result = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining("; "));
            throw new BadRequestException(result);
        }
        try{
           UserAppDTO userApp = customUserDetailsService.changePassword(userid, changePasswordWrapper);
           logger.info("User with id {} changed password.", userid);
            return new ResponseEntity<>(userApp, HttpStatus.OK);
        }catch (UsernameNotFoundException e){
            logger.error(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }
    @PostMapping("/user/change/email")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserAppDTO> changeEmail(@RequestParam Long userid, @RequestBody @Valid ChangeEmailWrapper email, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            for (ObjectError error : bindingResult.getAllErrors()) {
                logger.error(error.getDefaultMessage());
            }
            String result = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining("; "));
            throw new BadRequestException(result);
        }
        try{
           UserAppDTO userApp = customUserDetailsService.changeEmail(userid, email.getEmail());
           logger.info("User with id {} changed email.", userid);
            return new ResponseEntity<>(userApp, HttpStatus.OK);
        }catch (UserAlreadyExistException | UsernameNotFoundException e){
            logger.error(e.getMessage());
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
                logger.error("List of active users is empty.");
                throw new UserNotFoundExceptions("There are no active users");
            }
            logger.info("Return {} active users.", userAppDTOS.size());
            return new ResponseEntity<>(userAppDTOS, HttpStatus.OK);
        }

        if ("inactive".equals(userstatus)){
            userAppDTOS = customUserDetailsService.getInactiveUsers();
            if (userAppDTOS.isEmpty()){
                logger.error("List of inactive users is empty.");
                throw new UserNotFoundExceptions("There are no inactive users");
            }
            logger.info("Return {} inactive users.", userAppDTOS.size());
            return new ResponseEntity<>(userAppDTOS, HttpStatus.OK);
        }

        if (userstatus == null){
            userAppDTOS = customUserDetailsService.getAllUsers();
            if (userAppDTOS.isEmpty()){
                logger.error("List of users is empty.");
                throw new UserNotFoundExceptions("There are no users");
            }
            logger.info("Return {} users.", userAppDTOS.size());
            return new ResponseEntity<>(userAppDTOS, HttpStatus.OK);
        }
        logger.error("Wrong user status: {}.", userstatus);
        throw new BadRequestException("Wrong input parameter. Input user status: " + userstatus);
    }

    @DeleteMapping("/user/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserApp> deleteUserById(@RequestParam Long userid){
        try{
            if (customUserDetailsService.deleteUserById(userid) > 0){
                logger.info("Delete user with id {}. ", userid);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }catch (WrongArgumentException e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        logger.info("User with id {} not exist.", userid);
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }


}
