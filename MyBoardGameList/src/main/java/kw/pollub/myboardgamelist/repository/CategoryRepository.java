package kw.pollub.myboardgamelist.repository;

import kw.pollub.myboardgamelist.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select c from Category c join fetch c.boardGames b where b.owner.id = ?1")
    List<Category> findAllCategoriesWithBoardGamesByOwnerId(Long userId);

    Boolean existsByName(String name);
}
