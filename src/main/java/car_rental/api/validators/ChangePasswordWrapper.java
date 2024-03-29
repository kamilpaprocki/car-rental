package car_rental.api.validators;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@PasswordMatcher
public class ChangePasswordWrapper {

    @NotEmpty(message = "Field: \"password\" cannot be empty.")
    @NotNull(message = "Field: \"password\" cannot be null.")
    @Size(min = 6, message = "Try one with at least 6 characters in password field.")
    private String password;

    @NotEmpty(message = "Field: \"confirm password\" cannot be empty.")
    @NotNull(message = "Field: \"confirm password\" cannot be null.")
    private String confirmPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }


}
