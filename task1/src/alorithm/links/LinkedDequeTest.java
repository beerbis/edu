package alorithm.links;

import alorithm.filifo.DequeConsoleTest;
import alorithm.links.adapter.BufferLimitation;
import alorithm.links.storage.LinkedDeque;

public class LinkedDequeTest {
    public static void main(String[] args) {
        DequeConsoleTest.fixedDequeTest((size, wrapper) ->
                new BufferLimitation<>(size, wrapper.apply(new LinkedDeque<>())));
    }
}
