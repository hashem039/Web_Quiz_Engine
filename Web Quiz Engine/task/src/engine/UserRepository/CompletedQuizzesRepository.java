package engine.UserRepository;

import engine.DBModel.Completions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.processing.Completion;
@Repository
public interface CompletedQuizzesRepository extends PagingAndSortingRepository<Completions,Long> {
    public Page<Completions> getAllByUserEmail(String email, Pageable page);
}
