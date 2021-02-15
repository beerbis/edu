package alorithm.filifo.buffers;

public class StorageOverflowException extends StorageStateException {
    public StorageOverflowException() {
    }

    public StorageOverflowException(String message) {
        super(message);
    }
}
