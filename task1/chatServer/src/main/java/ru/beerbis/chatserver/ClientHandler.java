package ru.beerbis.chatserver;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ClientHandler {
    private String name;
    private String login;
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

        new Thread(this::listen).start();
    }

    public String getName() {
        return name;
    }

    private void listen() {
        try {
            if (!doAuth()) return;
            chat.allow(this);
            receiveMessage();
        } finally {
            try {
                chat.disallow(this);
            } finally {
                close();
            }
        }
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

    private boolean tryAuthSeq(String mayBeCredentials) throws AuthenticationService.AuthActionException {
        if (mayBeCredentials.startsWith("-auth")) {
            String[] credentials = mayBeCredentials.split("\\s");
            String mayBeNickname = chat.getAuthenticationService()
                    .findNicknameByLoginAndPassword(credentials[1], credentials[2]);
            if (mayBeNickname != null) {
                if (!chat.isNicknameOccupied(mayBeNickname)) {
                    name = mayBeNickname;
                    login = credentials[1];
                    sendMessage("[INFO] Auth OK:" + login + "," + name);

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
                if (message.length() > 1 && message.charAt(0) == '-') {
                    if (message.startsWith("-exit")) {
                        break;

                    } else if (message.startsWith("-pm")) {
                        String pmNick = getNextWord(message, "-pm".length());
                        String pmText = safeCopy(message, "-pm".length() + pmNick.length() + 1, message.length());
                        if (!chat.personalMessage(pmNick, String.format("[%s]: %s", name, pmText)))
                            sendMessage("[INFO] there are no client named " + pmNick);

                    } else if (message.startsWith("-iam")) {
                        String newNick = getNextWord(message, "-iam".length());
                        if (newNick.length() == 0) {
                            sendMessage("[INFO] new nickname should be defined");
                            continue;
                        }

                        String oldNick = name;
                        chat.getAuthenticationService().changeNicknameByLogin(login, newNick);
                        name = newNick;
                        chat.broadcastMessage("[INFO] " + oldNick + " is now known as " + name);

                    }
                } else {
                    chat.broadcastMessage(String.format("[%s]: %s", name, message));
                }
            } catch (IOException e) {
                throw new RuntimeException("SWW", e);
            } catch (AuthenticationService.AuthActionException e) {
                e.printStackTrace();
                sendMessage("[ERROR] " + e.getMessage());
            }
        }
    }

    static private String getNextWord(String src, int startPos) {
        final char whiteSpace = ' ';

        for (; startPos < src.length() && src.charAt(startPos) == whiteSpace; startPos++);
        int endPos;
        for (endPos = startPos; endPos < src.length() && src.charAt(endPos) != whiteSpace; endPos++);
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
