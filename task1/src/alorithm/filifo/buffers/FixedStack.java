package alorithm.filifo.buffers;

import java.util.Arrays;

public class FixedStack<E> implements SequentialBuffer<E> {
    private static final int EMPTY = -1;
    private final E[] buffer;
    private int head = EMPTY;

    @Override
    public String toString() {
        return "FixedStack{" +
                "head=" + head +
                ", buffer=" + Arrays.toString(buffer) +
                '}';
    }

    public FixedStack(int size) {
        if (size <= 0) throw new StorageInitializationException();
        buffer = (E[])new Object[size];
    }

    @Override
    public void push(E element) {
        if (size() >= buffer.length) throw new StorageOverflowException();
        buffer[++head] = element;
    }

    @Override
    public E pop() {
        if (isEmpty()) throw new StorageIsEmptyException();
        return buffer[head--];
    }

    @Override
    public E peek() {
        if (isEmpty()) throw new StorageIsEmptyException();
        return buffer[head];
    }

    @Override
    public boolean isEmpty() {
        return head <= EMPTY;
    }

    @Override
    public int size() {
        return head+1;
    }
}
