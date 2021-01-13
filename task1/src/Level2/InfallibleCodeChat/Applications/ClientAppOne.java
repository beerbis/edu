package Level2.InfallibleCodeChat.Applications;

import Level2.Chat.ChatGui;
import Level2.InfallibleCodeChat.Client.Client;
import java.io.IOException;
import java.util.function.Consumer;

public class ClientAppOne {
    static private Client client;
    static private ChatGui gui;
    static private Consumer<String> actualSubmitConsumer;

    public static void main(String[] args) {
        actualSubmitConsumer = x -> logNoConnection();
        gui = new ChatGui(msg -> actualSubmitConsumer.accept(msg));

        try {
            client = new Client("127.0.0.1", 8888, gui);
            actualSubmitConsumer = client::send;
        } catch (IOException e) {
            logNoConnection();
        }
    }

    private static void logNoConnection() {
        gui.onIncoming("Соединение не установленно...");
    }
}
