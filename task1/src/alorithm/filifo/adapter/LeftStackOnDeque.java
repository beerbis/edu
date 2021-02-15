package alorithm.filifo.adapter;

import alorithm.filifo.buffers.Deque;
import alorithm.filifo.buffers.SequentialBuffer;

public class LeftStackOnDeque<E> implements SequentialBuffer<E> {
    private final Deque<E> deque;

    public LeftStackOnDeque(Deque<E> deque) {
        this.deque = deque;
    }

    @Override
    public void push(E element) {
        deque.pushLeft(element);
    }

    @Override
    public E pop() {
        return deque.popLeft();
    }

    @Override
    public E peek() {
        return deque.peekLeft();
    }

    @Override
    public boolean isEmpty() {
        return deque.isEmpty();
    }

    @Override
    public int size() {
        return deque.size();
    }
}
