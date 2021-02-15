package alorithm.filifo.buffers;

import java.util.Arrays;

public class FixedQueue<E> implements SequentialBuffer<E> {
    private final E[] buffer;
    private int head = 0;
    private int tail = 0;

    /**
     * Нужно понимать почему голова с хвостом встретились: это пусто, или очень даже наоборот.
     */
    private boolean full = false;

    public FixedQueue(int size) {
        if (size <= 0) throw new StorageInitializationException();
        this.buffer = (E[]) new Object[size];
    }

    private int next(int idx) {
        return (idx + 1) % buffer.length;
    }

    @Override
    public String toString() {
        return "FixedQueue{" +
                "head=" + head +
                ", tail=" + tail +
                ", full=" + full +
                ", buffer=" + Arrays.toString(buffer) +
                '}';
    }

    @Override
    public void push(E element) {
        if (full) throw new StorageOverflowException();
        buffer[tail] = element;
        tail = next(tail);
        full = isEmpty();
    }

    @Override
    public E pop() {
        if (isEmpty()) throw new StorageIsEmptyException();
        full = false;
        final int idx = head;
        head = next(head);
        return buffer[idx];
    }

    @Override
    public E peek() {
        if (isEmpty()) throw new StorageIsEmptyException();
        return buffer[head];
    }

    @Override
    public boolean isEmpty() {
        return !full && head == tail;
    }

    @Override
    public int size() {
        if (full) return buffer.length;
        return (buffer.length + tail - head) % buffer.length;
    }
}
