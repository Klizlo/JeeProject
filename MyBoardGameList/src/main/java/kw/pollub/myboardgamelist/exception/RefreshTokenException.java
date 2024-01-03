package kw.pollub.myboardgamelist.exception;

public class RefreshTokenException extends RuntimeException {

    public RefreshTokenException() {
        super("Refresh token has expired");
    }

}
