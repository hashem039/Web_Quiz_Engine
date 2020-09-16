package engine.DBModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import engine.Model.Quiz;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class DBQuiz {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long quizId;

    @Column
    private String text;
    @Column
    private String title;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "quizId", nullable = false)
    private List<QuizOption> options = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "quizId")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    List<QuizAnswer> answers = new ArrayList<>();

    @Column
    private String auther;


    public DBQuiz() {
    }

    public DBQuiz(String text, String title, List<QuizOption> options, List<QuizAnswer> answers, String auther) {
        this.text = text;
        this.title = title;
        this.options = options;
        this.answers = answers;
        this.auther = auther;
    }

    public String getAuther() {
        return auther;
    }

    public void setAuther(String auther) {
        this.auther = auther;
    }

    public long getQuizId() {
        return quizId;
    }

    public void setQuizId(long quizId) {
        this.quizId = quizId;
    }

    public List<QuizOption> getOptions() {
        return options;
    }

    public void setOptions(List<QuizOption> options) {
        this.options = options;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<QuizAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<QuizAnswer> answers) {
        this.answers = answers;
    }

    public DBQuiz(String text, String title, List<QuizOption> options, List<QuizAnswer> answers) {
        this.text = text;
        this.title = title;
        this.options = options;
        this.answers = answers;
    }
    public Quiz toQuiz() {
        Quiz quiz = new Quiz();
        quiz.setId((int) this.getQuizId());
        quiz.setText(this.getText());
        quiz.setTitle(this.getTitle());
        List<String> options = new ArrayList<>();
        for (QuizOption qo : this.options) {
            options.add(qo.getValue());
        }
        quiz.setOptions(options);
        int[] answers;
        if (this.answers != null){
            answers = this.answers.stream().mapToInt(e -> e.getValue()).toArray();
        } else {
            answers = new int[0];
        }
        quiz.setAnswer(answers);
        return quiz;
    }
}
