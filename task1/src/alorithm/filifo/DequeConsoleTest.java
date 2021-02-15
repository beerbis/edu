package alorithm.filifo;

import alorithm.filifo.adapter.LeftQueueOnDeque;
import alorithm.filifo.adapter.LeftStackOnDeque;
import alorithm.filifo.adapter.RightQueueOnDeque;
import alorithm.filifo.adapter.RightStackOnDeque;
import alorithm.filifo.buffers.FixedDeque;

import static alorithm.filifo.StructuresInConsole.testFixed;

public class DequeConsoleTest {
    public static void main(String[] args) {
        System.out.println("== Поведенческий тест №1 [LeftQueueOnDeque] ==");
        testFixed(size -> new LeftQueueOnDeque<>(new FixedDeque<>(size)));
        System.out.println();
        System.out.println("== Поведенческий тест №1 [RightQueueOnDeque] ==");
        testFixed(size -> new RightQueueOnDeque<>(new FixedDeque<>(size)));
        System.out.println();
        System.out.println("== Поведенческий тест №1 [LeftStackOnDeque] ==");
        testFixed(size -> new LeftStackOnDeque<>(new FixedDeque<>(size)));
        System.out.println();
        System.out.println("== Поведенческий тест №1 [RightStackOnDeque] ==");
        testFixed(size -> new RightStackOnDeque<>(new FixedDeque<>(size)));
    }
}
