package kw.pollub.myboardgamelist.service;

import kw.pollub.myboardgamelist.model.BoardGame;

import java.util.List;

public interface IBoardGameService {

    List<BoardGame> findAllBoardGamesByOwner(Long userId);
    BoardGame findBoardGameById(Long boardGameId);
    BoardGame findBoardGameWithCategoryById(Long boardGameId);
    BoardGame findBoardGameWithCategoryAndOwnerById(Long boardGameId);
    BoardGame addBoardGame(BoardGame boardGame);
    BoardGame updateBoardGame(BoardGame boardGame, BoardGame boardGameToEdit);
    void removeBoardGame(Long boardGameId);

}
