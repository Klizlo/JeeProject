package kw.pollub.myboardgamelist.exception;

public class BoardGameNotFoundException extends RuntimeException {
    public BoardGameNotFoundException(Long boardGameId) {
        super("Board game not found with id: " + boardGameId);
    }
}
