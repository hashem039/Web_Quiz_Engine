package engine.ObjectRepository;

import engine.Model.Quiz;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
@Repository
public class QuizRepository implements ObjectRepository<Quiz> {
    private List<Quiz> quizzes = new ArrayList<>();
    private static final AtomicInteger quizID = new AtomicInteger();
    @Override
    public void store(Quiz quiz) {
        int id = quizID.incrementAndGet();
        quiz.setId(id);
        quizzes.add(quiz);
    }

    @Override
    public Quiz retrieve(int id) {
        return quizzes.stream().filter(e -> e.getId() == id).findFirst().get();
    }

    @Override
    public Quiz remove(int id) {
        return null;
    }
    public List<Quiz> retrieveAll() {
        return quizzes;
    }
}
