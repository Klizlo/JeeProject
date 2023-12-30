package kw.pollub.myboardgamelist.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long userId) {
        super("User with id " + userId + " not found");
    }

    public UserNotFoundException(String email) {
        super("User not found with email: " + email);
    }
}
