package Level2.InfallibleCodeChat.Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ServerChat implements Chat {
    private ServerSocket serverSocket;
    private Set<ClientHandler> clients;
    private AuthenticationService authenticationService;

    public ServerChat() {
        start();
    }

    @Override
    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    private void start() {
        try {
            serverSocket = new ServerSocket(8888);
            clients = new HashSet<>();
            authenticationService = new AuthenticationService();

            while (true) {
                System.out.println("Server is waiting for a connection ...");
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket, this);

                //1. Уж прямо сукисфулли логид ин... при всё желении авторизация, идущая в параллельном потоке, не успеет пройти,
                // а если и успеет - гонка потоков.
                //2. ClientHandler.name(getName) - не разделяемый ресурс? К нему, как вижу, не требуется синхронизация доступа.
                System.out.println(String.format("[%s] Client[%s] is successfully logged in", new Date(), clientHandler.getName()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public synchronized void broadcastMessage(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    private synchronized ClientHandler findClient(String nickname) {
        for (ClientHandler client : clients) {
            if (client.getName().equals(nickname)) {
                return client;
            }
        }
        return null;
    }

    @Override
    public boolean isNicknameOccupied(String nickname) {
        return findClient(nickname) != null;
    }

    @Override
    public boolean personalMessage(String nickname, String message) {
        ClientHandler client = findClient(nickname);
        if (client == null) return false;

        client.sendMessage(message);
        return true;
    }

    @Override
    public synchronized void allow(ClientHandler client) {
        broadcastMessage(String.format("[%s] logged in", client.getName()));
        clients.add(client);
    }

    @Override
    public synchronized void disallow(ClientHandler client) {
        if (clients.remove(client))
            broadcastMessage(String.format("[%s] logged out", client.getName()));
    }
}
