package Level2.Task2;

/***
 * Май арай сасай з эксепшен
 *
 */
public class MyArraySizeException extends RuntimeException{
    public MyArraySizeException(String message) {
        super(message);
    }

    public MyArraySizeException(String message, Throwable cause) {
        super(message, cause);
    }
}
