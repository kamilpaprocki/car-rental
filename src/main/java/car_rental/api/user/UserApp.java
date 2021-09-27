package car_rental.api.user;

import car_rental.api.userDetails.UserAppDetails;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "user")
public class UserApp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "login", nullable = false)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "registered_date")
    private Date registeredDate;

    @Column(name = "is_active")
    private boolean isActive;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_details_id")
    private UserAppDetails userDetails;

    @Column(name = "has_active_rents")
    private boolean hasActiveRent;

    public UserApp() {
    }

    public UserApp(UserApp u) {
        this.id = u.id;
        this.username = u.username;
        this.email = u.email;
        this.password = u.password;
        this.registeredDate = u.registeredDate;
        this.isActive = u.isActive;
        this.roles = u.roles;
        this.userDetails = u.userDetails;
        this.hasActiveRent = u.hasActiveRent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String login) {
        this.username = login;
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

    public Date getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean is_active) {
        this.isActive = is_active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public UserAppDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserAppDetails userDetails) {
        this.userDetails = userDetails;
    }

    public boolean isHasActiveRent() {
        return hasActiveRent;
    }

    public void setHasActiveRent(boolean hasActiveRent) {
        this.hasActiveRent = hasActiveRent;
    }

    @Override
    public String toString() {
        return "UserApp{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", registeredDate=" + registeredDate +
                ", isActive=" + isActive +
                ", roles=" + roles +
                ", userDetails=" + userDetails +
                ", hasActiveRent=" + hasActiveRent +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserApp)) return false;
        UserApp userApp = (UserApp) o;
        return isActive() == userApp.isActive() && isHasActiveRent() == userApp.isHasActiveRent() && Objects.equals(getId(), userApp.getId()) && Objects.equals(getUsername(), userApp.getUsername()) && Objects.equals(getEmail(), userApp.getEmail()) && Objects.equals(getPassword(), userApp.getPassword()) && Objects.equals(getRegisteredDate(), userApp.getRegisteredDate()) && Objects.equals(getRoles(), userApp.getRoles()) && Objects.equals(getUserDetails(), userApp.getUserDetails());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getEmail(), getPassword(), getRegisteredDate(), isActive(), getRoles(), getUserDetails(), isHasActiveRent());
    }

    public static UserAppBuilder builder() {
        return new UserAppBuilder();
    }

    private UserApp(UserAppBuilder u){
        this.id = u.id;
        this.username = u.username;
        this.email = u.email;
        this.password = u.password;
        this.registeredDate = u.registeredDate;
        this.isActive = u.isActive;
        this.roles = u.roles;
        this.userDetails = u.userDetails;
        this.hasActiveRent = u.hasActiveRents;
    }

    public static final class UserAppBuilder {
        private Long id;
        private String username;
        private String email;
        private String password;
        private Date registeredDate;
        private boolean isActive;
        private Set<Role> roles;
        private UserAppDetails userDetails;
        private boolean hasActiveRents;

        private UserAppBuilder() {
        }

        public UserAppBuilder id(String id) {
            if (id == null){
                this.id = null;
                return this;
            }
            this.id = Long.parseLong(id);
            return this;
        }

        public UserAppBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserAppBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserAppBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserAppBuilder registeredDate(Date registeredDate) {
            this.registeredDate = registeredDate;
            return this;
        }

        public UserAppBuilder isActive(boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public UserAppBuilder roles(Set<Role> roles) {
            if (roles == null){
                this.roles = null;
                return this;
            }
            this.roles = roles;
            return this;
        }

        public UserAppBuilder userDetails(UserAppDetails userDetails) {
            if (userDetails == null){
                this.userDetails = null;
                return this;
            }
            this.userDetails = userDetails;
            return this;
        }

        public UserAppBuilder hasActiveRents(boolean hasActiveRents){
            this.hasActiveRents = hasActiveRents;
            return this;
        }

        public UserApp build() {
            return new UserApp(this);
        }
    }
}
