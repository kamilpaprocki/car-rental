package car_rental.api.user;

import car_rental.api.userDetails.UserDetailsDTO;

import java.util.Objects;
import java.util.Set;

public class UserAppDTO {

    private String id;
    private String username;
    private String email;
    private String password;
    private String registeredDate;
    private String isActive;
    private Set<Role> roles;
    private UserDetailsDTO userDetailsDTO;

    private UserAppDTO(UserAppDTOBuilder u){
        this.id = u.id;
        this.username = u.username;
        this.email = u.email;
        this.password = u.password;
        this.registeredDate = u.registeredDate;
        this.isActive = u.isActive;
        this.roles = u.roles;
        this.userDetailsDTO = u.userDetailsDTO;
    }

    public UserAppDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(String registeredDate) {
        this.registeredDate = registeredDate;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public UserDetailsDTO getUserDetailsDTO() {
        return userDetailsDTO;
    }

    public void setUserDetailsDTO(UserDetailsDTO userDetailsDTO) {
        this.userDetailsDTO = userDetailsDTO;
    }

    public static UserAppDTOBuilder builder(){
        return new UserAppDTOBuilder();
    }


    public static final class UserAppDTOBuilder {
        private String id;
        private String username;
        private String email;
        private String password;
        private String registeredDate;
        private String isActive;
        private Set<Role> roles;
        private UserDetailsDTO userDetailsDTO;

        private UserAppDTOBuilder() {
        }

        public static UserAppDTOBuilder anUserAppDTO() {
            return new UserAppDTOBuilder();
        }

        public UserAppDTOBuilder id(String id) {
            this.id = id;
            return this;
        }

        public UserAppDTOBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserAppDTOBuilder email(String email){
            this.email = email;
            return this;
        }

        public UserAppDTOBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserAppDTOBuilder registeredDate(String registeredDate) {
            this.registeredDate = registeredDate;
            return this;
        }

        public UserAppDTOBuilder isActive(String isActive) {
            this.isActive = isActive;
            return this;
        }

        public UserAppDTOBuilder roles(Set<Role> roles) {
            this.roles = roles;
            return this;
        }

        public UserAppDTOBuilder userDetailsDTO(UserDetailsDTO userDetailsDTO) {
            this.userDetailsDTO = userDetailsDTO;
            return this;
        }

        public UserAppDTO build() {
            return new UserAppDTO();
        }
    }

    @Override
    public String toString() {
        return "UserAppDTO{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", registeredDate='" + registeredDate + '\'' +
                ", isActive='" + isActive + '\'' +
                ", roles=" + roles +
                ", userDetailsDTO=" + userDetailsDTO +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAppDTO)) return false;
        UserAppDTO that = (UserAppDTO) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getUsername(), that.getUsername()) && Objects.equals(getEmail(), that.getEmail()) && Objects.equals(getPassword(), that.getPassword()) && Objects.equals(getRegisteredDate(), that.getRegisteredDate()) && Objects.equals(getIsActive(), that.getIsActive()) && Objects.equals(getRoles(), that.getRoles()) && Objects.equals(getUserDetailsDTO(), that.getUserDetailsDTO());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getEmail(), getPassword(), getRegisteredDate(), getIsActive(), getRoles(), getUserDetailsDTO());
    }
}
