package car_rental.api.user;

import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findRoleByName(String name){
       return roleRepository.findRoleByRole(name);
    }

    public Role createRoleIfNotFound(String name){
        Role role = findRoleByName(name);
        if (role == null){
            role = new Role(name);
            roleRepository.save(role);
        }
        return role;
    }
}
