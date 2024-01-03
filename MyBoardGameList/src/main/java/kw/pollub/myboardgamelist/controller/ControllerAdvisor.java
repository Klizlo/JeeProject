package kw.pollub.myboardgamelist.controller;

import kw.pollub.myboardgamelist.exception.BoardGameNotFoundException;
import kw.pollub.myboardgamelist.exception.CategoryNotFoundException;
import kw.pollub.myboardgamelist.exception.UserAlreadyExistsException;
import kw.pollub.myboardgamelist.exception.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException exception, WebRequest request) {
        Map<String, Object> nameToMessage = new HashMap<>();
        nameToMessage.put("timestamp", new Date());
        nameToMessage.put("msg", exception.getMessage());

        return new ResponseEntity<>(nameToMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> handleUserAlreadyExistsException(UserAlreadyExistsException exception, WebRequest request) {
        Map<String, Object> nameToMessage = new HashMap<>();
        nameToMessage.put("timestamp", new Date());
        nameToMessage.put("msg", exception.getMessage());

        return new ResponseEntity<>(nameToMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<?> handleCategoryNotFoundException(CategoryNotFoundException exception, WebRequest webRequest) {
        Map<String, Object> nameToMessage = new HashMap<>();
        nameToMessage.put("timestamp", new Date());
        nameToMessage.put("msg", exception.getMessage());

        return new ResponseEntity<>(nameToMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BoardGameNotFoundException.class)
    public ResponseEntity<?> handleBoardGameNotFoundException(BoardGameNotFoundException exception, WebRequest request) {
        Map<String, Object> nameToMessage = new HashMap<>();
        nameToMessage.put("timestamp", new Date());
        nameToMessage.put("msg", exception.getMessage());

        return new ResponseEntity<>(nameToMessage, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> {
                    if (errors.containsKey(error.getField())) {
                        errors.put(error.getField(), String.format("%s, %s", errors.get(error.getField())));
                    } else {
                        errors.put(error.getField(), error.getDefaultMessage());
                    }
                });

        Map<String, Object> nameToMessage = new HashMap<>();
        nameToMessage.put("timestamp", new Date());
        nameToMessage.put("errors", errors);

        return new ResponseEntity<>(nameToMessage, HttpStatus.BAD_REQUEST);
    }
}
