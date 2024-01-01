package kw.pollub.myboardgamelist.repository;

import kw.pollub.myboardgamelist.model.BoardGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardGameRepository extends JpaRepository<BoardGame, Long> {

    List<BoardGame> findAllByOwner_Id(Long userId);
}
