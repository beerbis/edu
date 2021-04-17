package ru.beerbis.spring.lesson2;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.beerbis.spring.lesson2.commander.CartCommander;

import java.util.Scanner;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        var commander = (CartCommander) context.getBean(CartCommander.class);

        System.out.println("exit - to exit");
        System.out.println("help - to help");

        Cart cart = (Cart) context.getBean(Cart.class);
        while (true) {
            var line = SCANNER.nextLine();
            var lowerLine = line.toLowerCase();
            if ("exit".equals(lowerLine)) return;
            if ("help".equals(lowerLine)) {
                System.out.println(commander.getHelpDescription());
                continue;
            }

            cart = commander.Command(line, cart);
        }
    }
}
