package algorithm.filifo.buffers;

public class StorageStateException extends RuntimeException{
    public StorageStateException() {
    }

    public StorageStateException(String message) {
        super(message);
    }
}
