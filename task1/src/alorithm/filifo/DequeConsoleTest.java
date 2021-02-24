package alorithm.filifo;

import alorithm.filifo.adapter.LeftQueueOnDeque;
import alorithm.filifo.adapter.LeftStackOnDeque;
import alorithm.filifo.adapter.RightQueueOnDeque;
import alorithm.filifo.adapter.RightStackOnDeque;
import alorithm.filifo.buffers.Deque;
import alorithm.filifo.buffers.FixedDeque;
import alorithm.filifo.buffers.SequentialBuffer;

import java.util.function.BiFunction;
import java.util.function.Function;

import static alorithm.filifo.StructuresInConsole.testFixed;

public class DequeConsoleTest {
    public static void main(String[] args) {
        fixedDequeTest((size, wrapper) -> wrapper.apply(new FixedDeque(size)));
    }

    public static void fixedDequeTest(
            BiFunction<Integer, Function<Deque<Integer>, SequentialBuffer<Integer>>, SequentialBuffer> constructor) {
        System.out.println("== Поведенческий тест №1 [LeftQueueOnDeque] ==");
        testFixed(size -> constructor.apply(size, LeftQueueOnDeque::new));
        System.out.println();
        System.out.println("== Поведенческий тест №1 [RightQueueOnDeque] ==");
        testFixed(size -> constructor.apply(size, RightQueueOnDeque::new));
        System.out.println();
        System.out.println("== Поведенческий тест №1 [LeftStackOnDeque] ==");
        testFixed(size -> constructor.apply(size, LeftStackOnDeque::new));
        System.out.println();
        System.out.println("== Поведенческий тест №1 [RightStackOnDeque] ==");
        testFixed(size -> constructor.apply(size, RightStackOnDeque::new));
    }
}
