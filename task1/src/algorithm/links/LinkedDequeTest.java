package algorithm.links;

import algorithm.filifo.DequeConsoleTest;
import algorithm.links.adapter.BufferLimitation;
import algorithm.links.storage.LinkedDeque;

public class LinkedDequeTest {
    public static void main(String[] args) {
        DequeConsoleTest.fixedDequeTest((size, wrapper) ->
                new BufferLimitation<>(size, wrapper.apply(new LinkedDeque<>())));
    }
}
