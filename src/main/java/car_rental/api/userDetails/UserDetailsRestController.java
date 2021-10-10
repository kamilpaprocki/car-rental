package car_rental.api.userDetails;

import car_rental.api.exceptions.UserDetailsNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserDetailsRestController {

    private final UserDetailsService userDetailsService;

    private final static Logger logger = LoggerFactory.getLogger(UserDetailsRestController.class);

    public UserDetailsRestController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/users/details")
    @PreAuthorize("hasAnyRole('USER', 'WORKER', 'ADMIN')")
    public ResponseEntity<UserDetailsDTO> createOrUpdateUserDetails(@RequestBody UserDetailsDTO userDetails){
        if (userDetails.getId() != null){
            logger.info("Update user details with id {}.", userDetails.getId());
            return new ResponseEntity<>(userDetailsService.createOrUpdateUserDetails(userDetails), HttpStatus.OK);
        }
        logger.info("Create new user details.");
        return new ResponseEntity<>(userDetailsService.createOrUpdateUserDetails(userDetails), HttpStatus.CREATED);
    }

    @GetMapping("/users/details/all")
    @PreAuthorize("hasAnyRole('WORKER', 'ADMIN')")
    public ResponseEntity<List<UserDetailsDTO>> getAllUserDetails(){
        List<UserDetailsDTO> userDetails = userDetailsService.getAllUserDetails();
        if(userDetails.isEmpty()){
            logger.error("List of user details is empty.");
            throw new UserDetailsNotFoundException("There are no users details");
        }
        logger.info("Return {} user details.", userDetails.size());
        return new ResponseEntity<>(userDetails, HttpStatus.OK);
    }
    @ResponseBody
    @GetMapping("/users/details")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDetailsDTO> getUserDetailsById(@RequestParam Long id){
        UserDetailsDTO userDetailsDTO = userDetailsService.getUserDetailsDTOById(id);
        if (userDetailsDTO == null){
            logger.error("User details with id {} not found.", id);
            throw new UserDetailsNotFoundException("There is no user details with id: " + id);
        }
        logger.info("Return user details with id {}.", id);
        return new ResponseEntity<>(userDetailsDTO, HttpStatus.OK);
    }

    @DeleteMapping("/users/details/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserAppDetails> deleteUserDetailsById(@RequestParam Long id){

            if(userDetailsService.deleteUserDetailsById(id) > 0){
                logger.info("Delete user details with id {}.", id);
                return new ResponseEntity<>(HttpStatus.OK);
            }

        logger.info("User details with id {} not exist.", id);
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
