package engine.DBModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.ParameterScriptAssert;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class DBUser {
    @JsonIgnore
    @Id
    @Column(name = "UserID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Email
    @Pattern(regexp=".+@.+\\..+")
    @Column(name = "EMAIL")

    private String email;
    @Size(min = 5)
    @NotNull
    @Column(name = "PASSWORD")
    private String password;
    @JsonIgnore
    @Column(name = "ROLE")
    @NotBlank
    @NotNull
    private String role;

    public DBUser() {
    }

    public DBUser(@Email @NotNull @NotBlank String email, @Size(min = 5) @NotNull @NotBlank String password) {
        this.email = email;
        this.password = password;
    }

    public DBUser(@Email @NotNull @NotBlank String email, @Size(min = 5) @NotNull @NotBlank String password, @NotBlank @NotNull String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
