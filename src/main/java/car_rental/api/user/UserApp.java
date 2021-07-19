package car_rental.api.user;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "user")
public class UserApp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
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
    private boolean is_active;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public UserApp() {
    }

    public UserApp(UserApp u) {
        this.id = u.id;
        this.username = u.username;
        this.email = u.email;
        this.password = u.password;
        this.registredDate = u.registredDate;
        this.is_active = u.is_active;
        this.roles = u.roles;
    }

    public Long getId() {
        return id;
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

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserApp{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", registredDate=" + registredDate +
                ", is_active=" + is_active +
                ", roles=" + roles +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserApp)) return false;
        UserApp userApp = (UserApp) o;
        return isIs_active() == userApp.isIs_active() && getId().equals(userApp.getId()) && getUsername().equals(userApp.getUsername()) && getEmail().equals(userApp.getEmail()) && getPassword().equals(userApp.getPassword()) && getRegistredDate().equals(userApp.getRegistredDate()) && getRoles().equals(userApp.getRoles());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getEmail(), getPassword(), getRegistredDate(), isIs_active(), getRoles());
    }
}
