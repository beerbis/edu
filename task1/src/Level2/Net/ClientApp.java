package Level2.Net;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientApp {
    public static void main(String[] args) throws IOException {
        ChatingChannel channel = new ChatingChannel(new Socket("127.0.0.1", 80), "Client", System.out::println);

        Scanner scanner = new Scanner(System.in);
        while (!channel.isClosed()) {
            channel.say(scanner.nextLine());
        }

    }
}
