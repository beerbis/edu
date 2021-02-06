package alorithm.sort.lesson;

public interface Array<E> {

    void add(E value);

    E get(int index);

    boolean remove(E value);

    E remove(int index);

    int indexOf(E value);

    boolean contains(E value);

    boolean isEmpty();

    int size();

    void display();

    void sortBubble();
    void sortSelect();
    void sortInsert();
    void sortInsertPerf();
    void sortBiSelect();
}
