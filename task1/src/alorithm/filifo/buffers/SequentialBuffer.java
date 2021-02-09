package alorithm.filifo.buffers;

public interface SequentialBuffer<E> {
    void push(E element);
    E pop();
    E peek();
    boolean isEmpty();
    int size();
}
