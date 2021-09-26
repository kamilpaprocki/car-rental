package car_rental.api.userDetails;

import car_rental.api.exceptions.UserDetailsNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserDetailsRestController {

    private final UserDetailsService userDetailsService;

    public UserDetailsRestController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/users/details")
    public ResponseEntity<UserAppDetails> createOrUpdateUserDetails(@RequestBody UserDetailsDTO userDetails){
        if (userDetails.getId() != null){
            return new ResponseEntity<>(userDetailsService.createOrUpdateUserDetails(userDetails), HttpStatus.OK);
        }
        return new ResponseEntity<>(userDetailsService.createOrUpdateUserDetails(userDetails), HttpStatus.CREATED);
    }

    @GetMapping("/users/details/all")
    public ResponseEntity<List<UserDetailsDTO>> getAllUserDetails(){
        List<UserDetailsDTO> userDetails = userDetailsService.getAllUserDetails();
        if(userDetails.isEmpty()){
            throw new UserDetailsNotFoundException("There are no users details");
        }
            return new ResponseEntity<>(userDetails, HttpStatus.OK);
    }
    @ResponseBody
    @GetMapping("/users/details")
    public ResponseEntity<UserDetailsDTO> getUserDetailsById(@RequestParam Long id){
        UserDetailsDTO userDetailsDTO = userDetailsService.getUserDetailsDTOById(id);
        if (userDetailsDTO == null){
            throw new UserDetailsNotFoundException("There is no user details with id: " + id);
        }
        return new ResponseEntity<>(userDetailsDTO, HttpStatus.OK);
    }

    @DeleteMapping("/users/details/delete")
    public ResponseEntity<UserAppDetails> deleteUserDetailsById(@RequestParam Long id){
        if(userDetailsService.deleteUserDetailsById(id) > 0){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
