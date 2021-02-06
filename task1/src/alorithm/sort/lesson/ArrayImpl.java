package alorithm.sort.lesson;

import java.util.ArrayList;
import java.util.Arrays;

public class ArrayImpl<E extends Comparable<? super E>> implements Array<E> {

    protected static final int DEFAULT_CAPACITY = 8;

    protected E[] data;
    protected int size;

    public ArrayImpl() {
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public ArrayImpl(int initialCapacity) {
        this.data = (E[]) new Comparable[initialCapacity];
    }

    public ArrayImpl(E[] data) {
        this.data = data.clone();
        size = data.length;
    }

    //O(1) -> O(n)
    @Override
    public void add(E value) {
        checkAndGrow();
        data[size++] = value;
    }


    // O(1)
    @Override
    public E get(int index) {
        checkIndex(index);
        return data[index];
    }

    @Override
    public boolean remove(E value) {
        return remove(indexOf(value)) != null;
    }

    // O(n)
    @Override
    public E remove(int index) {
        checkIndex(index);

        E removedValue = data[index];
        if ((size - index - 1) >= 0) {
            System.arraycopy(data, index + 1, data, index, size - index - 1);
        }

        data[--size] = null; // clear to let GC do its work
        return removedValue;
    }

    @Override
    public boolean contains(E value) {
        return indexOf(value) != -1;
    }

    // O(n)
    @Override
    public int indexOf(E value) {
        for (int i = 0; i < size; i++) {
            if (value.equals(data[i])) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void display() {
        System.out.println(this);
    }

    // O(n^2) - compare
    // O(n^2) - exchange
    @Override
    public void sortBubble() {
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - 1 - i; j++) {
                if (data[j].compareTo(data[j + 1]) > 0) {
                    swap(j, j + 1);
                }
            }
        }
    }

    private void swap(int indexA, int indexB) {
        E temp = data[indexA];
        data[indexA] = data[indexB];
        data[indexB] = temp;
    }

    // O(n^2) - compare
    // O(n) = exchange
    @Override
    public void sortSelect() {
        for (int i = 0; i < size - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < size; j++) {
                if (data[j].compareTo(data[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            swap(minIndex, i);
        }
    }

    public void sortBiSelect() {
        int lowIndex = 0;
        int highIndex = size - 1;
        while (lowIndex < highIndex) {
            int minIndex = lowIndex;
            int maxIndex = lowIndex;
            for (int j = lowIndex + 1; j <= highIndex; j++) {
                if (data[j].compareTo(data[minIndex]) < 0) {
                    minIndex = j;
                }
                if (data[j].compareTo(data[maxIndex]) > 0) {
                    maxIndex = j;
                }
            }
            if (minIndex == maxIndex) break;
            swap(minIndex, lowIndex);
            if (maxIndex == lowIndex) maxIndex = minIndex;
            swap(maxIndex, highIndex);

            lowIndex++;
            highIndex--;
        }
    }

    // O(n^2) --> O(n) - compare
    // O(n) --> O(0) = exchange
    @Override
    public void sortInsert() {
        for (int i = 1; i < size; i++) {
            E temp = data[i];
            int in = i;
            while (in > 0 && data[in - 1].compareTo(temp) >= 0) {
                data[in] = data[in - 1];
                in--;
            }
            data[in] = temp;
        }
    }

    public void sortInsertPerf() {
        for (int i = 1; i < size; i++) {
            E temp = data[i];
            int in = i;
            while (in > 0 && data[in - 1].compareTo(temp) >= 0) {
//                data[in] = data[in - 1];
                in--;
            }
            if (i != in ) {
                System.arraycopy(data, in, data, in + 1, i - in);
                data[in] = temp;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size - 1; i++) {
            sb.append(data[i]);
            sb.append(", ");
        }
        if (size > 0) {
            sb.append(data[size - 1]);
        }
        sb.append("]");
        return sb.toString();
    }

    protected void checkAndGrow() {
        if (data.length == size) {
            data = Arrays.copyOf(data, calculateNewLength());
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            String errMsg = String.format("Incorrect 'index' value: %d; max value is %d", index, size - 1);
            throw new IndexOutOfBoundsException(errMsg);
        }
    }

    private int calculateNewLength() {
        return size == 0 ? 1 : size * 2;
    }
}
