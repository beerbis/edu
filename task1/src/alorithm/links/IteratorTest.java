package alorithm.links;

import alorithm.links.storage.LinkedDeque;

public class IteratorTest {
    public static void main(String[] args) {
        LinkedDeque<Integer> deque = new LinkedDeque<>();

        for (int i = 3; i <= 6; i++) deque.pushLeft(i);
        System.out.println(deque);

        System.out.println("iterate: ");
        for (int val: deque) System.out.print(val + " ");
        System.out.println();

        LinkedDeque.BIterator<Integer> iterator = deque.leftBIterator();
        iterator.stepRight(); //сошёл с 6
        iterator.insertAsLeft(65); //вставил между 6 и 5
        iterator.insertAsRight(54); //вставил между 5 и 4
        iterator.stepRight(); //сошёл с 5
        iterator.stepRight(); //сошёл с 54
        System.out.println("4? " + iterator.current());
        System.out.println("Всё хорошо?(шагнул дальше) " + iterator.stepRight()); //стоит на 4 - шагает на сошёл с 3
        System.out.println("3? " + iterator.current());
        System.out.println("Есть куда идти?(нет) " + iterator.stepRight()); //дальше некуда идти

        System.out.println("Ждём: 6 65 5 54 4 3");
        System.out.println(deque);
        System.out.println("Длинная 6? " + deque.size());

        //I. А ещё будет неприятно, если пока бежит итератор из дэка снесут его текущий элемент, фокусный.
        // и дэк не имеет никакого морального права запоминать свои итереторы, чтобы в них что-то
        // поправить, как это делает итератор с ДЭК-ом... потом, всё потом.
    }
}
