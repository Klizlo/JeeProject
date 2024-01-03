package kw.pollub.myboardgamelist.service;

import jakarta.transaction.Transactional;
import kw.pollub.myboardgamelist.exception.BoardGameNotFoundException;
import kw.pollub.myboardgamelist.model.BoardGame;
import kw.pollub.myboardgamelist.model.Category;
import kw.pollub.myboardgamelist.model.User;
import kw.pollub.myboardgamelist.repository.BoardGameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardGameService implements IBoardGameService{

    private final BoardGameRepository boardGameRepository;
    private final ICategoryService categoryService;
    private final IUserService userService;

    @Override
    public List<BoardGame> findAllBoardGamesByOwner(Long userId) {
        return boardGameRepository.findAllByOwner_Id(userId);
    }

    @Override
    public BoardGame findBoardGameById(Long boardGameId) {
        return boardGameRepository.findById(boardGameId)
                .orElseThrow(() -> new BoardGameNotFoundException(boardGameId));
    }

    @Override
    public BoardGame findBoardGameWithCategoryById(Long boardGameId) {
        return boardGameRepository.findBoardGameWithCategoryById(boardGameId)
                .orElseThrow(() -> new BoardGameNotFoundException(boardGameId));
    }

    @Override
    public BoardGame findBoardGameWithCategoryAndOwnerById(Long boardGameId) {
        return boardGameRepository.findBoardGameWithCategoryAndOwnerById(boardGameId)
                .orElseThrow(() -> new BoardGameNotFoundException(boardGameId));
    }

    @Override
    @Transactional
    public BoardGame addBoardGame(BoardGame boardGame) {

        Category category = categoryService.findCategoryById(boardGame.getCategory().getId());
        User owner = userService.findUserById(boardGame.getOwner().getId());

        category.addBoardGame(boardGame);
        owner.addBoardGame(boardGame);

        boardGame.setOwner(owner);
        boardGame.setCategory(category);

        return boardGameRepository.save(boardGame);
    }

    @Override
    @Transactional
    public BoardGame updateBoardGame(BoardGame boardGame, BoardGame boardGameToEdit) {

        boardGameToEdit.setName(boardGame.getName());
        boardGameToEdit.setDeveloper(boardGame.getDeveloper());
        boardGameToEdit.setDescription(boardGame.getDescription());
        boardGameToEdit.setMinNumberOfPlayers(boardGame.getMinNumberOfPlayers());
        boardGameToEdit.setMaxNumberOfPlayers(boardGame.getMaxNumberOfPlayers());
        boardGameToEdit.setNumberOfHours(boardGame.getNumberOfHours());
        boardGameToEdit.setPicture(boardGame.getPicture());

        if (!boardGame.getCategory().getId().equals(boardGameToEdit.getCategory().getId())) {
            Category newCategory = categoryService.findCategoryById(boardGame.getCategory().getId());
            boardGameToEdit.setCategory(newCategory);
            newCategory.addBoardGame(boardGameToEdit);
        }

        return boardGameRepository.save(boardGameToEdit);
    }

    @Override
    @Transactional
    public void removeBoardGame(Long boardGameId) {
        boardGameRepository.deleteById(boardGameId);
    }
}
