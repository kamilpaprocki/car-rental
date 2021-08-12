package car_rental.api.user;

import car_rental.api.exceptions.UserAlreadyExistException;
import com.google.common.collect.Sets;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;
    private final RoleService roleService;

    public CustomUserDetailsService(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    public UserDetails loadUserByUsername(String username){
        Optional<UserApp> userApp = userRepository.findByUsername(username);
        userApp.orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return userApp.map(CustomUserDetails::new).get();
    }

    @Transactional
    public UserApp registerUser(UserAppDTO userAppDTO){
        if (!usernameExist(userAppDTO.getUsername())){
            throw new UserAlreadyExistException("There is an account with that username");
        }

        if (!emailExist(userAppDTO.getEmail())){
            throw new UserAlreadyExistException("There is an account with that email");
        }

        UserApp userApp = new UserApp();
        userApp.setUsername(userAppDTO.getUsername());
        userApp.setEmail(userAppDTO.getEmail());
        userApp.setPassword(bCryptPasswordEncoder().encode(userAppDTO.getPassword()));
        userApp.setRegistredDate(Date.valueOf(LocalDate.now()));
        userApp.setIsActive(true);
        userApp.setRoles(Sets.newHashSet(roleService.createRoleIfNotFound("ROLE_USER")));
        return userRepository.save(userApp);
    }

    private boolean usernameExist(String username){
        return userRepository.findByUsername(username).isEmpty();
    }

    private boolean emailExist(String email){
        return userRepository.findByEmail(email).isEmpty();
    }

    @Bean
    private BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
