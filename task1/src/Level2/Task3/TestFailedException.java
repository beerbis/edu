package Level2.Task3;

public class TestFailedException extends RuntimeException{

    public TestFailedException() {
    }

    public TestFailedException(String message) {
        super(message);
    }

    public TestFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
