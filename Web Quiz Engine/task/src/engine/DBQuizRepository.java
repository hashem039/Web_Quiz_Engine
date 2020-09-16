package engine;

import engine.DBModel.DBQuiz;
import engine.Model.Quiz;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DBQuizRepository extends JpaRepository<DBQuiz, Long> {
      Optional<DBQuiz> findDBquizByQuizId(long id);
}
