package alorithm.links;

import alorithm.links.storage.LinkedDeque;

import javax.crypto.spec.PSource;

public class IteratorTest {
    public static void main(String[] args) {
        LinkedDeque<Integer> deque = new LinkedDeque<>();

        for (int i = 3; i <= 6; i++) deque.pushLeft(i);
        System.out.println(deque);

        System.out.println("iterate: ");
        for (int val: deque) System.out.print(val + " ");
        System.out.println();

        LinkedDeque.NodeIterator<Integer> iterator = deque.iterator();
        iterator.next(); //выплюнул 6
        iterator.insertAsLeft(65); //вставил между 6 и 5
        iterator.insertAsRight(54); //вставил между 5 и 4
        iterator.next(); //выплюнул 5
        iterator.next(); //выплюнул 54
        iterator.next(); //выплюнул 4
        iterator.next(); //выплюнул 3
        System.out.println("Всё хорошо? " + !iterator.hasNext());

        System.out.println("Ждём: 6 65 5 54 4 3");
        System.out.println(deque);
        System.out.println("Длинная 6? " + deque.size());

        //I. А ещё будет неприятно, если пока бежит итератор из дэка снесут его текущий элемент, фокусный.
        // и дэк не имеет никакого морального права запоминать свои итереторы, чтобы в них что-то
        // поправить, как это делает итератор с ДЭК-ом... потом, всё потом.
        //II. Очень заморачивает семантика лево-право, когда Next у тебя от элементарнейшего итератора, просто Next:
        //  по совести, если пользоваться им, то надо делать Next/Prev/Current и никак не пересекаться с
        //  интерфесом Iterator. Мухи отдельно, котлеты отдельно.
    }
}
