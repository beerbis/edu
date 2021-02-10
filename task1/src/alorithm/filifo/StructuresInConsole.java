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

    private static boolean shallRaise(String desc, Runnable method) {
        try {
            System.out.print(desc);
            method.run();
            System.out.println(": FAIL");
            return false;
        } catch (Exception e) {
            System.out.println(": OK thrown " + e.getClass().getSimpleName());
            return true;
        }
    }

    private static <R> boolean shallReturn(String desc, Supplier<R> method) {
        return shallReturn(desc, method, null);
    }

    private static <R> boolean shallReturn(String desc, Supplier<R> method, R expected) {
        try {
            System.out.print(desc);
            System.out.print(": ");
            R r = method.get();
            if (expected != null) {
                if (expected.equals(r)) {
                    System.out.print("OK, ");
                    System.out.println(r);
                    return true;
                } else {
                    System.out.print("FAIL, ");
                    System.out.println(r);
                    return false;
                }
            }
            System.out.println(r);
        } catch (Exception e) {
            System.out.print("FAIL thrown ");
            System.out.println(e.getClass().getSimpleName());
            return false;
        }
        return true;
    }

    private static boolean shallDo(String desc, Runnable method) {
        try {
            System.out.print(desc);
            System.out.print(": ");
            method.run();
            System.out.println("OK");
            return true;
        } catch (Exception e) {
            System.out.print("FAIL thrown ");
            System.out.println(e.getClass().getSimpleName());
            return false;
        }
    }

    private static boolean debug(Supplier<String> getState, Supplier<Boolean>... steps) {
        for (Supplier<Boolean> step: steps) {
            String before = getState.get();

            if (!step.get()) {
                System.out.println("\t" + before);
                System.out.println("\t" + getState.get());
                return false;
            }
        }
        return true;
    }

    private static boolean testFixed(Function<Integer, SequentialBuffer<Integer>> constructor) {
        SequentialBuffer<Integer> filifo = constructor.apply(3);

        Supplier<String> stateGetter = () -> filifo.toString();
        return debug(stateGetter,
                () -> shallReturn("initial size", filifo::size, 0),
                () -> shallReturn("Изначально пуст", filifo::isEmpty, true),
                () -> shallRaise("Извлечь нечего", filifo::pop),
                () -> shallReturn("Всё ещё пуст", filifo::isEmpty, true),
                () -> shallDo("Добавить \"1\"", () -> filifo.push(1)),
                () -> shallReturn("Уже не пуст", filifo::isEmpty, false),
                () -> shallReturn("Должен извлеч единственный \"1\"", filifo::pop, 1),
                () -> shallDo("Добавить \"3\"", () -> filifo.push(3)),
                () -> shallDo("Добавить \"2\"", () -> filifo.push(2)),
                () -> shallReturn("Почти полон(2)", filifo::size, 2),
                () -> shallReturn("Штуку выкинуть", filifo::pop),
                () -> shallDo("Штуку вбросить \"4\"", () -> filifo.push(4)),
                () -> shallReturn("Тот же размер, что и два шага назад", filifo::size, 2),
                () -> shallDo("Добавить \"1\"", () -> filifo.push(1)),
                () -> shallReturn("Полон(3)", filifo::size, 3),
                () -> shallReturn("Не пуст", filifo::isEmpty, false),
                () -> shallRaise("Некуда добавлять", () -> filifo.push(4)),
                () -> shallReturn("Всё ещё полон(3)", filifo::size, 3),
                () -> shallReturn("Всё ещё Не пуст", filifo::isEmpty, false),
                () -> shallReturn("минус элемент, раз", filifo::pop),
                () -> shallReturn("минус элемент, два", filifo::pop),
                () -> shallReturn("Почти пуст(1)", filifo::size, 1),
                () -> shallReturn("Но не пуст", filifo::isEmpty, false),
                () -> shallReturn("минус элемент, три", filifo::pop),
                () -> shallReturn("Теперь пуст(0)", filifo::size, 0),
                () -> shallReturn("Совсем пуст", filifo::isEmpty, true),
                () -> shallRaise("Больше не извлечь", filifo::pop),
                () -> shallReturn("Всё так же пуст(0)", filifo::size, 0),
                () -> shallReturn("Совсем пуст", filifo::isEmpty, true)
        );
    }
}
