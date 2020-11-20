package Level2.Task1;

public class StudentNotFound extends RuntimeException{
    public StudentNotFound(String message) {
        super(message);
    }

    public StudentNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
