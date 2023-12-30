package kw.pollub.myboardgamelist.exception;

public class CategoryAlreadyExistsException extends RuntimeException {
    public CategoryAlreadyExistsException(String name) {
        super("Category " + name + " already exists");
    }
}
