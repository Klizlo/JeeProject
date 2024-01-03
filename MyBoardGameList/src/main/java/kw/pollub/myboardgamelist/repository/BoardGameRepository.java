package kw.pollub.myboardgamelist.repository;

import kw.pollub.myboardgamelist.model.BoardGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardGameRepository extends JpaRepository<BoardGame, Long> {

    List<BoardGame> findAllByOwner_Id(Long userId);

    @Query("select b from BoardGame b join fetch b.category where b.id = ?1")
    Optional<BoardGame> findBoardGameWithCategoryById(Long id);

    @Query("select b from BoardGame b join fetch b.category join fetch b.owner where b.id =?1")
    Optional<BoardGame> findBoardGameWithCategoryAndOwnerById(Long id);
}
