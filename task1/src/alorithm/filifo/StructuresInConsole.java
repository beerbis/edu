package alorithm.filifo;

import alorithm.filifo.buffers.FixedQueue;
import alorithm.filifo.buffers.FixedStack;
import alorithm.filifo.buffers.SequentialBuffer;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 1. Реализовать рассмотренные структуры данных в консольных программах.
 */
public class StructuresInConsole {
    public static void main(String[] args) {
        System.out.println("== Поведенческий тест №1 [FixedStack] ==");
        testFixed(FixedStack::new);
        System.out.println();
        System.out.println("== Поведенческий тест №2 [FixedQueue] ==");
        testFixed(FixedQueue::new);
    }

    private static void shallRaise(String desc, Runnable method) {
        try {
            System.out.print(desc);
            method.run();
            System.out.println(": FAIL");
        } catch (Exception e) {
            System.out.println(": OK thrown " + e.getClass().getSimpleName());
        }
    }

    private static <R> void shallReturn(String desc, Supplier<R> method) {
        shallReturn(desc, method, null);
    }

    private static <R> void shallReturn(String desc, Supplier<R> method, R expected) {
        try {
            System.out.print(desc);
            System.out.print(": ");
            R r = method.get();
            if (expected != null) System.out.print(expected.equals(r) ? "OK, " : "FAIL, ");
            System.out.println(r);
        } catch (Exception e) {
            System.out.print("FAIL thrown ");
            System.out.println(e.getClass().getSimpleName());
        }
    }

    private static void shallDo(String desc, Runnable method) {
        try {
            System.out.print(desc);
            System.out.print(": ");
            method.run();
            System.out.println("OK");
        } catch (Exception e) {
            System.out.print("FAIL thrown ");
            System.out.println(e.getClass().getSimpleName());
        }
    }

    private static void testFixed(Function<Integer, SequentialBuffer<Integer>> constructor) {
        SequentialBuffer<Integer> filifo = constructor.apply(3);
        shallReturn("initial size", filifo::size, 0);
        System.out.println(filifo);

        shallReturn("Изначально пуст", filifo::isEmpty, true);
        shallRaise("Извлечь нечего", filifo::pop);
        shallReturn("Всё ещё пуст", filifo::isEmpty, true);
        shallDo("Добавить \"1\"", () -> filifo.push(1));
        shallReturn("Уже не пуст", filifo::isEmpty, false);
        System.out.println(filifo);
        shallReturn("Должен извлеч единственный \"1\"", filifo::pop, 1);
        System.out.println(filifo);
        shallDo("Добавить \"3\"", () -> filifo.push(3));
        System.out.println(filifo);
        shallDo("Добавить \"2\"", () -> filifo.push(2));
        shallReturn("Почти полон(2)", filifo::size, 2);
        System.out.println(filifo);
        shallReturn("Штуку выкинуть", filifo::pop);
        shallDo("Штуку вбросить \"4\"", () -> filifo.push(4));
        shallReturn("Тот же размер, что и два шага назад", filifo::size, 2);
        System.out.println(filifo);
        shallDo("Добавить \"1\"", () -> filifo.push(1));
        shallReturn("Полон(3)", filifo::size, 3);
        shallReturn("Не пуст", filifo::isEmpty, false);
        System.out.println(filifo);
        shallRaise("Некуда добавлять", () -> filifo.push(4));
        shallReturn("Всё ещё полон(3)", filifo::size, 3);
        shallReturn("Всё ещё Не пуст", filifo::isEmpty, false);
        System.out.println(filifo);
        shallReturn("минус элемент, раз", filifo::pop);
        System.out.println(filifo);
        shallReturn("минус элемент, два", filifo::pop);
        shallReturn("Почти пуст(1)", filifo::size, 1);
        shallReturn("Но не пуст", filifo::isEmpty, false);
        System.out.println(filifo);
        shallReturn("минус элемент, три", filifo::pop);
        shallReturn("Теперь пуст(0)", filifo::size, 0);
        shallReturn("Совсем пуст", filifo::isEmpty, true);
        System.out.println(filifo);
        shallRaise("Больше не извлечь", filifo::pop);
        shallReturn("Всё так же пуст(0)", filifo::size, 0);
        shallReturn("Совсем пуст", filifo::isEmpty, true);
        System.out.println(filifo);
    }
}
