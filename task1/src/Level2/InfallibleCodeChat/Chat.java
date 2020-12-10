package Level2.InfallibleCodeChat;

public interface Chat {
    void broadcastMessage(String message);
    boolean isNicknameOccupied(String nickname);
    void subscribe(ClientHandler client);
    void unsubscribe(ClientHandler client);
    AuthenticationService getAuthenticationService();
}
