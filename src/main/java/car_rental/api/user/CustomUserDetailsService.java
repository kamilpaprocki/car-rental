package car_rental.api.user;

import car_rental.api.exceptions.UserAlreadyExistException;
import car_rental.api.exceptions.WrongArgumentException;
import car_rental.api.userDetails.UserDetailsDTO;
import car_rental.api.userDetails.UserDetailsMapper;
import car_rental.api.validators.ChangePasswordWrapper;
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
        return new UserAppMapper().mapToDTO(userRepository.save(userApp));
    }

    public UserAppDTO addUserDetails(long userId, UserDetailsDTO userDetailsDTO){
        UserApp userApp = userRepository.getUserAppById(userId).orElse(null);
        if (userApp == null){
            throw new UsernameNotFoundException("THere is no user with id: " + userId);
        }
        userApp.setUserDetails(new UserDetailsMapper().mapToDAO(userDetailsDTO));
        userApp.setRoles(Sets.newHashSet(roleService.createRoleIfNotFound("USER")));
        if("admin".equals(userApp.getUsername())){
            userApp.getRoles().add(roleService.createRoleIfNotFound("ADMIN"));
        }
        return new UserAppMapper().mapToDTO(userRepository.save(userApp));
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

    public List<UserAppDTO> getInactiveUsers(){
        List<UserApp> userApps = userRepository.getInactiveUsers().orElseThrow(null);
        return userApps.stream().map(new UserAppMapper() :: mapToDTO).collect(Collectors.toList());
    }

    public List<UserAppDTO> getAllUsers(){
        List<UserApp> userApps = userRepository.findAll();
        return userApps.stream().map(new UserAppMapper() :: mapToDTO).collect(Collectors.toList());
    }

    public UserAppDTO getUserAppById(Long id){
        if(id == null){
            throw new WrongArgumentException("User id cannot be a null");
        }
        UserApp userApp = userRepository.getUserAppById(id).orElse(null);
        return new UserAppMapper().mapToDTO(userApp);
    }

    public UserAppDTO setRoles(Long userId, String[] roles){
        if (userId == null){
            throw new WrongArgumentException("User id can not be a null");
        }

        UserApp userApp = userRepository.getUserAppById(userId).orElse(null);
        if (userApp == null){
            throw new UsernameNotFoundException("There is no user with id: " + userId);
        }
        userApp.getRoles().clear();
        userApp.getRoles().add(roleService.createRoleIfNotFound("USER"));
        if (roles != null) {
            for (String role : roles) {
                userApp.getRoles().add(roleService.createRoleIfNotFound(role));
            }
        }
        return new UserAppMapper().mapToDTO(userRepository.save(userApp));
    }

    public UserAppDTO changePassword(Long userId, ChangePasswordWrapper changePasswordWrapper){
        UserApp userApp = userRepository.getUserAppById(userId).orElseThrow(() -> new UsernameNotFoundException("There is no user with id: " + userId));
        userApp.setPassword(bCryptPasswordEncoder().encode(changePasswordWrapper.getPassword()));
        return new UserAppMapper().mapToDTO(userRepository.save(userApp));
    }

    public UserAppDTO changeEmail(Long userId, String email){
        UserApp userApp = userRepository.getUserAppById(userId).orElseThrow(() -> new UsernameNotFoundException("There is no user with id: " + userId));
        if (!emailExist(email)){
            throw new UserAlreadyExistException("There is user with this email");
        }
        userApp.setEmail(email);
        return new UserAppMapper().mapToDTO(userRepository.save(userApp));
    }

    public UserAppDTO updateUserDetails(Long userId, UserDetailsDTO userDetailsDTO){
        UserApp userApp = userRepository.getUserAppById(userId).orElseThrow(() -> new UsernameNotFoundException("There is no user with id: " + userId));
        userApp.setUserDetails(new UserDetailsMapper().mapToDAO(userDetailsDTO));
        return new UserAppMapper().mapToDTO(userRepository.save(userApp));
    }

    @Transactional
    public int deleteUserById(Long userId){
        if (userId == null){
            throw new WrongArgumentException("User id cannot be a null");
        }
       return userRepository.deleteUserAppById(userId);
    }

    @Bean
    private BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }



}
