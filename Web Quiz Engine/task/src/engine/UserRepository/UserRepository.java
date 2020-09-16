package engine.UserRepository;

import engine.DBModel.DBUser;
import engine.SpringSecurity.MyUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<DBUser,Long> {
    Optional<DBUser> findUserByEmail(String email);
}
