package car_rental.api.utils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@PasswordMatcher
public class ChangePasswordWrapper {

    @NotEmpty(message = "This can not be empty")
    @NotNull(message = "This can not be null")
    @Size(min = 6, message = "Try one with at least 6 characters")
    private String password;

    @NotEmpty(message = "This can not be empty")
    @NotNull(message = "This can not be null")
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
