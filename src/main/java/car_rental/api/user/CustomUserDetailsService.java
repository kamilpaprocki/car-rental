package car_rental.api.user;

import car_rental.api.exceptions.UserAlreadyExistException;
import car_rental.api.exceptions.WrongArgumentException;
import car_rental.api.userDetails.UserDetailsDTO;
import car_rental.api.userDetails.UserDetailsMapper;
import car_rental.api.utils.ChangePasswordWrapper;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public UserAppDTO registerUser(UserRegisterDTO userRegisterDTO){
        if (!usernameExist(userRegisterDTO.getUsername())){
            throw new UserAlreadyExistException("There is an account with that username");
        }

        if (!emailExist(userRegisterDTO.getEmail())){
            throw new UserAlreadyExistException("There is an account with that email");
        }

        UserApp userApp = new UserApp();
        userApp.setUsername(userRegisterDTO.getUsername());
        userApp.setEmail(userRegisterDTO.getEmail());
        userApp.setPassword(bCryptPasswordEncoder().encode(userRegisterDTO.getPassword()));
        userApp.setRegisteredDate(Date.valueOf(LocalDate.now()));
        userApp.setIsActive(true);
        userApp.setRoles(Sets.newHashSet(roleService.createRoleIfNotFound("ROLE_USER")));
        return new UserAppMapper().mapToDTO(userApp);
    }

    public UserApp addUserDetails(UserAppDTO userApp, UserDetailsDTO userDetails){
        System.out.println(userDetails.toString());
        userApp.setUserDetailsDTO(userDetails);
        return userRepository.save(new UserAppMapper().mapToDAO(userApp));
    }

    private boolean usernameExist(String username){
        if(username.isEmpty()) {
            throw new WrongArgumentException("Email cannot be empty.");
        }
        return userRepository.findByUsername(username).isEmpty();
    }

    private boolean emailExist(String email){
        if(email.isEmpty()) {
            throw new WrongArgumentException("Email cannot be empty.");
        }
        return userRepository.findByEmail(email).isEmpty();
    }

    public UserApp updateUser(UserApp userApp){
        return userRepository.save(userApp);
    }

    public List<UserAppDTO> getActiveUsers(){
        List<UserApp> userApps = userRepository.getActiveUsers().orElseThrow(null);
        return userApps.stream().map(new UserAppMapper() :: mapToDTO).collect(Collectors.toList());
    }

    public UserAppDTO getUserAppById(Long id){
        if(id == null){
            throw new WrongArgumentException("User id cannot be a null");
        }
        UserApp userApp = userRepository.getUserAppByById(id).orElse(null);
        return new UserAppMapper().mapToDTO(userApp);
    }

    public UserApp setRoles(UserSetRolesWrapper userSetRolesWrapper, String[] roles){
        if (userSetRolesWrapper == null){
            throw new UsernameNotFoundException("There is no user");
        }

        UserApp userApp = userRepository.getUserAppByById(Long.parseLong(userSetRolesWrapper.getId())).orElseThrow();
        userApp.getRoles().clear();
        userApp.getRoles().add(roleService.createRoleIfNotFound("ROLE_USER"));
        if (roles != null) {
            for (String role : roles) {
                userApp.getRoles().add(roleService.createRoleIfNotFound(role));
            }
        }
        return userRepository.save(userApp);

    }

    public UserApp changePassword(Long userId, ChangePasswordWrapper changePasswordWrapper){
        UserApp userApp = userRepository.getUserAppByById(userId).orElseThrow(() -> new UsernameNotFoundException("There is no user with id: " + userId));
        userApp.setPassword(bCryptPasswordEncoder().encode(changePasswordWrapper.getPassword()));
        return userRepository.save(userApp);
    }

    public UserApp changeEmail(Long userId, String email){
        UserApp userApp = userRepository.getUserAppByById(userId).orElseThrow(() -> new UsernameNotFoundException("There is no user with id: " + userId));
        if (!emailExist(email)){
            throw new UserAlreadyExistException("There is user with this email");
        }
        userApp.setEmail(email);
        return userRepository.save(userApp);
    }

    public UserApp updateUserDetails(Long userId, UserDetailsDTO userDetailsDTO){
        UserApp userApp = userRepository.getUserAppByById(userId).orElseThrow(() -> new UsernameNotFoundException("There is no user with id: " + userId));
        userApp.setUserDetails(new UserDetailsMapper().mapToDAO(userDetailsDTO));
        return userRepository.save(userApp);
    }

    @Bean
    private BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }



}
