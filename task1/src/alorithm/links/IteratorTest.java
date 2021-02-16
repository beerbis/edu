package alorithm.links;

import alorithm.links.storage.LinkedDeque;

public class IteratorTest {
    public static void main(String[] args) {
        LinkedDeque<Integer> deque = new LinkedDeque<>();
        for (int i = 3; i < 10; i++) deque.pushLeft(i);
        System.out.println(deque);
        for (int val: deque) System.out.println(val);
    }
}
