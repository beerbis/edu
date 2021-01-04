package Level2.InfallibleCodeChat.Client;

public interface ChatClientEvents {
    void onIncoming(String msg);
    void onLoggedIn(String login, String nickname);
    void onError(Throwable e);
}
