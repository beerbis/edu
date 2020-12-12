package Level2.InfallibleCodeChat.Server;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ClientHandler {
    private String name;
    private DataInputStream in;
    private DataOutputStream out;
    private Socket socket;
    private Chat chat;

    static private final int TIMEOUT_AUTH_MS = 120_000;

    public ClientHandler(Socket socket, Chat chat) {
        this.socket = socket;
        this.chat = chat;
        name = String.valueOf(socket.getPort());
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            throw new RuntimeException("SWW", e);
        }

        listen();
    }

    public String getName() {
        return name;
    }

    private void listen() {
        new Thread(() -> {
            if (!doAuth()) {
                close();
                return;
            }
            receiveMessage();
        }).start();
    }

    /**
     * -auth login password
     * sample: -auth l1 p1
     */
    private boolean doAuth() {
        sendMessage("Please enter credentials. Sample [-auth login password]");
        try {
            int initialTimeout = socket.getSoTimeout();
            long startTime = System.currentTimeMillis();
            while (true) {
                int restOfTheTimeout = TIMEOUT_AUTH_MS - (int)(System.currentTimeMillis() - startTime);
                if (restOfTheTimeout <= 0) {
                    socket.setSoTimeout(initialTimeout);
                    sendMessage("[INFO] Auth timed out");
                    return false;
                }
                socket.setSoTimeout(restOfTheTimeout);

                try {
                    if (tryAuthSeq(in.readUTF())) {
                        socket.setSoTimeout(initialTimeout);
                        chat.subscribe(this);
                        return true;
                    }
                } catch (SocketTimeoutException e) {
                    continue;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("SWW", e);
        }
    }

    private boolean tryAuthSeq(String mayBeCredentials) {
        if (mayBeCredentials.startsWith("-auth")) {
            String[] credentials = mayBeCredentials.split("\\s");
            String mayBeNickname = chat.getAuthenticationService()
                    .findNicknameByLoginAndPassword(credentials[1], credentials[2]);
            if (mayBeNickname != null) {
                if (!chat.isNicknameOccupied(mayBeNickname)) {
                    sendMessage("[INFO] Auth OK");
                    name = mayBeNickname;
                    chat.broadcastMessage(String.format("[%s] logged in", name));

                    return true;
                } else {
                    sendMessage("[INFO] Current user is already logged in.");
                }
            } else {
                sendMessage("[INFO] Wrong login or password.");
            }
        }
        return false;
    }

    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            throw new RuntimeException("SWW", e);
        }
    }

    public void receiveMessage() {
        while (true) {
            try {
                String message = in.readUTF();
                if (message.startsWith("-exit")) {
                    //клиент разлогинился - да; он больше не будет получать общих сообщений(broadcast) - да. Но не вижу где быон нас покинул.
                    chat.unsubscribe(this);
                    chat.broadcastMessage(String.format("[%s] logged out", name));
                    break;
                } else if (message.startsWith("-pm")) {
                    String pmNick = getLettersUntil(message, "-pm ".length(), ' ');
                    String pmText = safeCopy(message, "-pm ".length() + pmNick.length() + 1, message.length());
                    if (!chat.personalMessage(pmNick, String.format("[%s]: %s", name, pmText)))
                        sendMessage("[INFO] there are no client named " + pmNick);
                } else {
                    chat.broadcastMessage(String.format("[%s]: %s", name, message));
                }
            } catch (IOException e) {
                throw new RuntimeException("SWW", e);
            }
        }
    }

    static private String getLettersUntil(String src, int startPos, char until) {
        int endPos;
        for (endPos = startPos; endPos < src.length() && src.charAt(endPos) != until; endPos++);
        return safeCopy(src, startPos, endPos);
    }

    static private String safeCopy(String src, int start, int count) {
        if (start >= src.length())
            return "";

        return src.substring(start, count);
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
