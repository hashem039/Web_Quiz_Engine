package engine.Model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import engine.DBModel.QuizAnswer;
import engine.DBModel.QuizOption;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Component
public class Quiz {
    @NotBlank(message = "Title is Mandatory")
    private String title;
    @NotBlank(message = "text is Mandatory")
    private String text;
    @NotNull
    @Size(min = 2)
    private List<String> options;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int[] answer;
    private int id;
    public static int autoInc = 0;

    public Quiz() {
        super();
    }

    public Quiz(String title, String text, List<String> options, int[] answer) {
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
        autoInc++;
        this.id = autoInc;
    }
    public Quiz(int id, String title, String text, List<String> options,int[] answer) {
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
        this.id = id;
    }

    public int[] getAnswer() {
        return this.answer == null ? new int[0] : this.answer;
    }

    public void setAnswer(int[] answer) {
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
   public List<QuizOption> convToListOfQuizOptions() {
        List<QuizOption> result = new ArrayList<>();
        for (String str: this.options) {
            result.add(new QuizOption(str));
        }
        return result;
    }
    public List<QuizAnswer> convToListOfAnswerOptions() {
        List<QuizAnswer> result = new ArrayList<>();
        if (this.answer != null) {
            for (Integer str : this.answer) {
                result.add(new QuizAnswer(str));
            }
        }
        return result;
    }
}
