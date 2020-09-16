package engine.ExceptionHandler;

public class UserNotAuthorizedToDeleteQuiz extends RuntimeException{
    public UserNotAuthorizedToDeleteQuiz(String message) {
        super(message);
    }
}
