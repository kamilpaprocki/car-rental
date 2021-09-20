package car_rental.api.user;

import car_rental.api.utils.EmailMatcher;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@EmailMatcher
public class ChangeEmailWrapper {

    @NotEmpty(message = "This can not be empty")
    @NotNull(message = "This can not be null")
    @Email
    private String email;

    @NotEmpty(message = "This can not be empty")
    @NotNull(message = "This can not be null")
    @Email
    private String confirmEmail;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmEmail() {
        return confirmEmail;
    }

    public void setConfirmEmail(String confirmEmail) {
        this.confirmEmail = confirmEmail;
    }
}
