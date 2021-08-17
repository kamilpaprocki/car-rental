package car_rental.api.user;

import car_rental.api.utils.PasswordMatcher;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@PasswordMatcher()
public class UserAppDTO {

    @JsonProperty("username")
    @NotEmpty(message = "This can not be empty")
    @NotNull(message = "This can not be null")
    @Size(min = 5, message = "Try one with at least 5 characters")
    private String username;

    @JsonProperty("email")
    @NotEmpty(message = "This can not be empty")
    @NotNull(message = "This can not be null")
    @Email
    private String email;

    @JsonProperty("password")
    @NotEmpty(message = "This can not be empty")
    @NotNull(message = "This can not be null")
    @Size(min = 6, message = "Try one with at least 6 characters")
    private String password;

    @JsonProperty("confirmPassword")
    @NotEmpty(message = "This can not be empty")
    @NotNull(message = "This can not be null")
    private String confirmPassword;

    public String getUsername() {
        return username;
    }

    public UserAppDTO() {
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }



    @Override
    public String toString() {
        return "UserAppDTO{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAppDTO)) return false;
        UserAppDTO that = (UserAppDTO) o;
        return getUsername().equals(that.getUsername()) && getEmail().equals(that.getEmail()) && getPassword().equals(that.getPassword()) && getConfirmPassword().equals(that.getConfirmPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getEmail(), getPassword(), getConfirmPassword());
    }

private UserAppDTO(UserAppDTOBuilder u){
    this.username = u.username;
    this.email = u.email;
    this.password = u.password;
    this.confirmPassword = u.confirmPassword;
}


    public static final class UserAppDTOBuilder {
        private String username;
        private String email;
        private String password;

        private String confirmPassword;

        public static UserAppDTOBuilder userAppDTO() {
            return new UserAppDTOBuilder();
        }

        public UserAppDTOBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserAppDTOBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserAppDTOBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserAppDTOBuilder confirmPassword(String confirmPassword) {
            this.confirmPassword = confirmPassword;
            return this;
        }

        public UserAppDTO build() {
            return new UserAppDTO(this);
        }
    }
}