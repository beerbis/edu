package alorithm.tree.lesson;

public interface Tree<E extends Comparable<? super E>> {

    enum TraverseMode {
        IN_ORDER,
        PRE_ORDER,
        POST_ORDER,
    }

    void add(E value) throws GodItIsTooDeepException;
    void addMayLoose(E value);


    boolean contains(E value);

    boolean remove(E value);

    boolean isEmpty();

    int size();

    void display();

    void traverse(TraverseMode mode);

    class GodItIsTooDeepException extends Exception {
    }

}
