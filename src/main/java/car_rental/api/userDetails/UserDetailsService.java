package car_rental.api.userDetails;


import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserDetailsService {

    private final UserDetailsRepository userDetailsRepository;

    public UserDetailsService(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    public UserDetails createOrUpdateUserDetails(UserDetails userDetails){
        return userDetailsRepository.save(userDetails);
    }

    public List<UserDetails> getAllUserDetails(){
        return userDetailsRepository.findAll();
    }

    public UserDetailsDTO getUserDetailsDTOById(long id){
        UserDetails userDetails = userDetailsRepository.findById(id).orElse(null);
        if (userDetails == null){
            return null;
        }
        return new UserDetailsMapper().map(userDetails);
    }

    @Transactional
    public int deleteUserDetailsById(long id){
        return userDetailsRepository.deleteUserDetailsById(id);
    }

}
