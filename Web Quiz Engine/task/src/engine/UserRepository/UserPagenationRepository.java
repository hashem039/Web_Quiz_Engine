package engine.UserRepository;

import engine.DBModel.DBQuiz;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPagenationRepository extends PagingAndSortingRepository<DBQuiz, Long> {

}
