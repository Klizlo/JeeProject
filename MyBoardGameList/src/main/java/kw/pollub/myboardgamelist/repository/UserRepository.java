package kw.pollub.myboardgamelist.repository;

import kw.pollub.myboardgamelist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUsersByEmail(String email);

    Boolean existsByEmail(String email);

}
