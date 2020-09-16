package engine.Model;

import javax.validation.constraints.*;

public class UserModel {
    @NotNull
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @NotNull
    @Size(min = 5)
    private String password;

    public UserModel() {
    }

    public UserModel(@NotNull @NotBlank @Email String email, @NotBlank @NotNull @Size(min = 4) String password) {
        this.email = email;
        this.password = password;
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
}
