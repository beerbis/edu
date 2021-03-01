package algorithm.filifo.buffers;

public class FixedDeque<E> implements Deque<E> {
    private E[] buffer;
    private int left;
    private int right;
    private boolean empty = true;

    public FixedDeque(int size) {
        //В теории можно завести понятие стратегии размещения - куда
        // воткнуть left/right, но смысла не вижу, пусть и дальше живут в ноле
        this.buffer = (E[]) new Object[size];
    }

    @Override
    public void pushLeft(E element) {
        left = next(Action.PUSH, Side.LEFT, left, right);
        buffer[left] = element;
    }

    @Override
    public void pushRight(E element) {
        right = next(Action.PUSH, Side.RIGHT, right, left);
        buffer[right] = element;
    }

    @Override
    public E popLeft() {
        int result = left;
        left = next(Action.POP, Side.LEFT, left, right);
        return buffer[result];
    }

    @Override
    public E popRight() {
        int result = right;
        right = next(Action.POP, Side.RIGHT, right, left);
        return buffer[result];
    }

    @Override
    public E peekLeft() {
        if (empty) throw new StorageIsEmptyException();
        return buffer[left];
    }

    @Override
    public E peekRight() {
        if (empty) throw new StorageIsEmptyException();
        return buffer[right];
    }

    /**
     * Получить следующий ндекс элемента.
     * Метод не так чист как хотелось бы, он управляет {@link #empty}
     *
     * @param action выполняемое действие
     * @param side направление/сторона списка
     * @param valueIdx начальное значение
     * @param oppositeIdx актуальное значение индекса противоположной стороны
     * @return следующее значение для {@param value}
     * @throws StorageIsEmptyException если действие {@link Action#POP}, а извлекать нечего
     * @throws StorageOverflowException если действие {@link Action#PUSH}, но место закончилось
     */
    private int next(Action action, Side side, int valueIdx, int oppositeIdx) {
        if (valueIdx == oppositeIdx)
            switch (action) {
                case POP:
                    if (empty) throw new StorageIsEmptyException();
                    empty = true;
                    return valueIdx;
                case PUSH:
                    if (empty) {
                        empty = false;
                        return valueIdx;
                    }
            }

        int result = (buffer.length + valueIdx + (action.next * side.next)) % buffer.length;
        if (action ==  Action.PUSH && result == oppositeIdx)
            throw new StorageOverflowException();

        return result;
    }

    @Override
    public boolean isEmpty() {
        return empty;
    }

    @Override
    public int size() {
        if (empty) return 0;
        return (buffer.length + right - left) % buffer.length + 1;
    }

    private enum Side {
        LEFT(-1),
        RIGHT(1);
        final int next;
        Side(int next) { this.next = next; }
    }
    private enum Action {
        PUSH(1),
        POP(-1);
        final int next;
        Action(int next) { this.next = next;}
    }
}
