package kw.pollub.myboardgamelist.service;

import kw.pollub.myboardgamelist.model.BoardGame;

import java.util.List;

public interface IBoardGameService {

    List<BoardGame> findAllBoardGamesByOwner(Long userId);
    BoardGame findBoardGameById(Long boardGameId);
    BoardGame addBoardGame(BoardGame boardGame);
    BoardGame updateBoardGame(BoardGame boardGame, Long boardGameId);
    void deleteBoardGame(Long boardGameId);

}
