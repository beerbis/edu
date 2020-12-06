package Level2.Net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class ChatingChannel {
    private final Socket socket;
    private final DataOutputStream out;
    private final DataInputStream in;
    private final Thread incomingThread;
    private String nickname = "Incognito";
    private final Consumer<String> logTheChat;
    private boolean closed = false;

    public ChatingChannel(Socket socket, String myNickname, Consumer<String> logTheChat) throws IOException {
        this.socket = socket;
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());
        this.logTheChat = logTheChat;
        initProtocol();

        introduceMySelf(myNickname);
        incomingThread = new Thread(this::readIncomings);
        incomingThread.start();
    }

    synchronized boolean isClosed() {
        return closed;
    }

    private void readIncomings() {
        try {
            listening:
            while(!isClosed()) {
                String message = in.readUTF();

                for (String s: protocolPhrases.keySet())
                    if (message.startsWith(s)) {
                        if (protocolPhrases.get(s).apply(message.substring(s.length())))
                            break listening;
                        continue listening;
                    }

                logTheChat.accept(String.format("%s> %s", nickname, message));
            }
        } catch (IOException e) {
            markClosed();
        } finally {
            markClosed();
        }
    }

    private synchronized void markClosed() {
        if (closed) return;
        closed = true;
        sayBie();
        logTheChat.accept(String.format("messaging channel with %s was closed...", nickname));

        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final String CMD_MY_NAME_IS = "My name is ";
    private static final String CMD_BIE = "Bie!";
    private final Map<String, Function<String, Boolean>> protocolPhrases = new HashMap<>();
    private void initProtocol() {
        protocolPhrases.put(
                CMD_MY_NAME_IS, s -> {
                    nickname = s;
                    logTheChat.accept("specking with " + nickname + "...");
                    return false;
                });
        protocolPhrases.put(
                CMD_BIE, s -> {
                    logTheChat.accept(nickname + " is gone...");
                    markClosed();
                    return true;
                });
    }

    synchronized void introduceMySelf(String nickname) {
        try {
            out.writeUTF(CMD_MY_NAME_IS + nickname);
        } catch (IOException e) {
            markClosed();
        }
    }

    synchronized void sayBie() {
        try {
            out.writeUTF(CMD_BIE);
        } catch (IOException e) {}
    }

    synchronized void say(String message) {
        try {
            out.writeUTF(message); //или не нужен кругом тут synchronized и будет достаточно `volatile out`? А может и вообще не надо ждать синхронизации в состояниях out-а?
        } catch (IOException e) {
            markClosed();
        }
    }
}
