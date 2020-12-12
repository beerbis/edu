package Level2.InfallibleCodeChat.Client;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.function.Consumer;

public class Client {
    private  Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Consumer<String> doOnLog;

    public Client(String host, int port, Consumer<String> doOnLog) throws IOException {
        this.doOnLog = doOnLog;
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
            while (true) doOnLog.accept(in.readUTF());
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
        doOnLog.accept(String.format("[exception] %s, %s", e.getClass().getSimpleName(), e.getMessage()));
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

