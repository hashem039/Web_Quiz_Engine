package engine.DBModel;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class QuizOption {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long optionID;
    @Column
    private String value;

    public QuizOption() {
    }

    public long getOptionID() {
        return optionID;
    }

    public void setOptionID(long optionID) {
        this.optionID = optionID;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public QuizOption(String value) {
        this.value = value;
    }
}
