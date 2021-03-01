package algorithm.sort.lesson;

public class SortedArrayImpl<E extends Comparable<? super E>> extends ArrayImpl<E> {

    public SortedArrayImpl() {
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public SortedArrayImpl(int initialCapacity) {
        this.data = (E[]) new Comparable[initialCapacity];
    }


    // O(logN)
    @Override
    public int indexOf(E value) {
        int low = 0;
        int high = size - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            if (data[mid].equals(value)) {
                return mid;
            } else if (value.compareTo(data[mid]) > 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return -1;
    }

    // O(n)
    @Override
    public void add(E value) {
        checkAndGrow();

        int index = size;
        for (int i = 0; i < size; i++) {
            if (data[i].compareTo(value) > 0) {
                index = i;
                break;
            }
        }

        if (index == size) {
            data[size++] = value;
        } else {
            doInsert(value, index);
        }
    }

    private void doInsert(E value, int index) {
        System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = value;
        size++;
    }
}
