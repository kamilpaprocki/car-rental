package car_rental.api.userDetails;


import car_rental.api.exceptions.WrongArgumentException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsService {

    private final UserDetailsRepository userDetailsRepository;

    public UserDetailsService(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    public UserAppDetails createOrUpdateUserDetails(UserDetailsDTO userDetails){
        return userDetailsRepository.save(new UserDetailsMapper().mapToDAO(userDetails));
    }

    public List<UserDetailsDTO> getAllUserDetails(){
        List<UserAppDetails> userAppDetails = userDetailsRepository.findAll();
        return userAppDetails.stream().map(new UserDetailsMapper() :: mapToDTO).collect(Collectors.toList());
    }

    public UserDetailsDTO getUserDetailsDTOById(Long userDetailsId){
        if (userDetailsId == null){
            throw new WrongArgumentException("User details id cannot be a null");
        }
        UserAppDetails userDetails = userDetailsRepository.findById(userDetailsId).orElseThrow(null);
        return new UserDetailsMapper().mapToDTO(userDetails);
    }

    @Transactional
    public int deleteUserDetailsById(Long userDetailsId){
        if (userDetailsId == null){
            throw new WrongArgumentException("User details id cannot be a null");
        }
        return userDetailsRepository.deleteUserDetailsById(userDetailsId);
    }

}
