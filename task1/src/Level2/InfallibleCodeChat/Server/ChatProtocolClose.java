package Level2.InfallibleCodeChat.Server;

public class ChatProtocolClose extends Exception {
    public ChatProtocolClose() {
    }

    public ChatProtocolClose(String message) {
        super(message);
    }

    public ChatProtocolClose(String message, Throwable cause) {
        super(message, cause);
    }
}
