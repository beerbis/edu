package alorithm.filifo.buffers;

public interface Deque<E> {
    void pushLeft(E element);
    void pushRight(E element);
    E popLeft();
    E popRight();
    E peekLeft();
    E peekRight();
    boolean isEmpty();
    int size();
}
