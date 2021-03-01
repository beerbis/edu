package algorithm.links.adapter;

import algorithm.filifo.buffers.SequentialBuffer;
import algorithm.filifo.buffers.StorageOverflowException;

import static java.util.Objects.requireNonNull;

public class BufferLimitation<E> implements SequentialBuffer<E> {
    private final int limit;
    private final SequentialBuffer<E> buffer;

    public BufferLimitation(int limit, SequentialBuffer<E> buffer) {
        if (limit < 0) throw new IllegalArgumentException("Переданный лимит на размер не применим");
        requireNonNull(buffer);
        this.limit = limit;
        this.buffer = buffer;
    }

    @Override
    public void push(E element) {
        if (buffer.size() == limit) throw new StorageOverflowException();
        buffer.push(element);
    }

    @Override
    public E pop() {
        return buffer.pop();
    }

    @Override
    public E peek() {
        return buffer.peek();
    }

    @Override
    public boolean isEmpty() {
        return buffer.isEmpty();
    }

    @Override
    public int size() {
        return buffer.size();
    }
}
