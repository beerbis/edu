package algorithm.filifo;

import algorithm.filifo.buffers.FixedStack;
import algorithm.filifo.buffers.SequentialBuffer;

import java.util.Scanner;

/**
 * 2. Создать программу, которая переворачивает вводимые строки (читает справа налево).
 */
public class StringReverseByStack {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.nextLine();
            if (line.isEmpty()) break;

            SequentialBuffer<Character> chars = new FixedStack<>(line.length());

            for (int i = 0; i < line.length(); i++)
                chars.push(line.charAt(i));

            while (!chars.isEmpty()) System.out.print(chars.pop());
            System.out.println();
        }
    }
}
