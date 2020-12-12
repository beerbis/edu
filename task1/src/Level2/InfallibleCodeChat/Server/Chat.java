package Level2.InfallibleCodeChat.Server;

public interface Chat {
    void broadcastMessage(String message);
    boolean isNicknameOccupied(String nickname);
    boolean personalMessage(String nickname, String message);
    void allow(ClientHandler client);
    void disallow(ClientHandler client);
    AuthenticationService getAuthenticationService();
}
