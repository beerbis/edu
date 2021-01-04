package Level2.InfallibleCodeChat.Client;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    static final String LOGGED_IN_PREFIX = "[INFO] Auth OK:";

    private  Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private ChatClientEvents chatEvents;

    public Client(String host, int port, ChatClientEvents events) throws IOException {
        this.chatEvents = events;
        try {
            socket = new Socket(host, port);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            close();
            logException(e);
            throw e;
        }

        new Thread(this::listen).start();
    }

    private void listen() {
        try {
            while (true) {
                String msg = in.readUTF();
                if (msg.startsWith(LOGGED_IN_PREFIX)) {
                    String[] ids = msg.substring(LOGGED_IN_PREFIX.length()).split(",");
                    chatEvents.onLoggedIn(ids[0], ids[1]);
                }

                chatEvents.onIncoming(msg);
            }
        } catch (IOException e) {
            logException(e);
        } finally {
            close();
        }
    }

    public boolean send(String message) {
        try {
            out.writeUTF(message);
            return true;
        } catch (IOException e) {
            close();
            logException(e);
            return false;
        }
    };

    private void logException(IOException e) {
        chatEvents.onError(e);
    }

    private void close() {
        justCloseEm(in);
        justCloseEm(out);
        justCloseEm(socket);
    }

    private void justCloseEm(Closeable closeable) {
        if (closeable == null) return;

        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

