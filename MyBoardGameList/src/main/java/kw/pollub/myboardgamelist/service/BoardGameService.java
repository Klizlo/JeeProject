package kw.pollub.myboardgamelist.service;

import kw.pollub.myboardgamelist.model.BoardGame;
import kw.pollub.myboardgamelist.repository.BoardGameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardGameService implements IBoardGameService{

    private final BoardGameRepository boardGameRepository;

    @Override
    public List<BoardGame> findAllBoardGamesByOwner(Long userId) {
        return boardGameRepository.findAllByOwner_Id(userId);
    }

    @Override
    public BoardGame findBoardGameById(Long boardGameId) {
        return null;
    }

    @Override
    public BoardGame addBoardGame(BoardGame boardGame) {
        return null;
    }

    @Override
    public BoardGame updateBoardGame(BoardGame boardGame, Long boardGameId) {
        return null;
    }

    @Override
    public void deleteBoardGame(Long boardGameId) {

    }
}
