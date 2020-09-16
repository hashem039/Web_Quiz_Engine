package engine.DBModel;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Completions {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cId;

    @NotNull
    @NotBlank
    @JsonIgnore
    private String userEmail;

    @NotNull
    private long id;

    @NotNull
    private Date completedAt;

    public Completions() {
    }

    public Completions(@NotNull @NotBlank String userEmail, @NotNull long id, @NotNull Date completionDate) {
        this.userEmail = userEmail;
        this.id = id;
        this.completedAt = completionDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getcId() {
        return cId;
    }

    public void setcId(long id) {
        this.cId = cId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completionDate) {
        this.completedAt = completionDate;
    }
}
