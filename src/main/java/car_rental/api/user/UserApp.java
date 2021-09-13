package car_rental.api.user;

import car_rental.api.userDetails.UserDetails;

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

    @Column(name = "registred_date")
    private Date registredDate;

    @Column(name = "is_active")
    private boolean isActive;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_details_id")
    private UserDetails userDetails;

    public UserApp() {
    }

    public UserApp(UserApp u) {
        this.id = u.id;
        this.username = u.username;
        this.email = u.email;
        this.password = u.password;
        this.registredDate = u.registredDate;
        this.isActive = u.isActive;
        this.roles = u.roles;
        this.userDetails = u.userDetails;
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

    public Date getRegistredDate() {
        return registredDate;
    }

    public void setRegistredDate(Date registredDate) {
        this.registredDate = registredDate;
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

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    @Override
    public String toString() {
        return "UserApp{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", registredDate=" + registredDate +
                ", isActive=" + isActive +
                ", roles=" + roles +
                ", client=" + userDetails +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserApp)) return false;
        UserApp userApp = (UserApp) o;
        return isActive == userApp.isActive && Objects.equals(getId(), userApp.getId()) && getUsername().equals(userApp.getUsername()) && getEmail().equals(userApp.getEmail()) && getPassword().equals(userApp.getPassword()) && Objects.equals(getRegistredDate(), userApp.getRegistredDate()) && Objects.equals(getRoles(), userApp.getRoles()) && getUserDetails().equals(userApp.getUserDetails());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getEmail(), getPassword(), getRegistredDate(), isActive, getRoles(), getUserDetails());
    }
}
