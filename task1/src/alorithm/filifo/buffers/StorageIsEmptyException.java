package alorithm.filifo.buffers;

public class StorageIsEmptyException extends StorageStateException {
    public StorageIsEmptyException() {
    }

    public StorageIsEmptyException(String message) {
        super(message);
    }
}
