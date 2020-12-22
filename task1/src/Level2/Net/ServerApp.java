package Level2.Net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerApp {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket server = new ServerSocket(80);
        System.out.println("Waiting for client...");
        ChatingChannel channel = new ChatingChannel(server.accept(), "Server", System.out::println);

        Scanner scanner = new Scanner(System.in);
        while (!channel.isClosed()) {
            channel.say(scanner.nextLine());
        }

    }
}
