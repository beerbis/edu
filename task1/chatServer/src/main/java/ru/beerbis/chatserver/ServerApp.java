package ru.beerbis.chatserver;

public class ServerApp {
    public static void main(String[] args) {
        System.out.println(
                System.getProperty("user.dir"));
        new ServerChat();
    }
}
