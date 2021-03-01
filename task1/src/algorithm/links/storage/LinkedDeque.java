package algorithm.links.storage;

import algorithm.filifo.buffers.Deque;
import algorithm.filifo.buffers.StorageIsEmptyException;
import com.sun.istack.internal.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringJoiner;
import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;

public class LinkedDeque<E> implements Deque<E>, Iterable<E> {
    int count = 0;
    BiNode<E> left;
    BiNode<E> right;

    @Override
    public void pushLeft(E element) {
        left = new BiNode<>(element, null, left);
        if (count++ == 0) right = left;
    }

    @Override
    public void pushRight(E element) {
        right = new BiNode<>(element, right, null);
        if (count++ == 0) left = right;
    }

    @Override
    public E popLeft() {
        if (count == 0) throw new StorageIsEmptyException();
        E result = left.data;
        left = left.linkOff().right;
        if (--count <= 1) right = left;
        return result;
    }

    @Override
    public E popRight() {
        if (count == 0) throw new StorageIsEmptyException();
        E result = right.data;
        right = right.linkOff().left;
        if (--count <= 1) left = right;
        return result;
    }

    @Override
    public E peekLeft() {
        if (count == 0) throw new StorageIsEmptyException();
        return left.data;
    }

    @Override
    public E peekRight() {
        if (count == 0) throw new StorageIsEmptyException();
        return right.data;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "[", "]");
        for (E node: this) joiner.add(node.toString());
        return "LinkedDeque" + joiner;
    }

    private void onExternalBeforeRemoved(BiNode<E> removed) {
        count--;
        if (removed == left) left = removed.right;
        if (removed == right) right = removed.left;
    }

    private void onExternalInserted(BiNode<E> inserted) {
        count++;
        if (inserted.left == right) {
            right = inserted;
        } else if(inserted.right == left) {
            left = inserted;
        }
    }

    public BIterator<E> leftBIterator() {
        return new BIterator<>(left, this::onExternalInserted, this::onExternalBeforeRemoved);
    }

    public BIterator<E> rightBIterator() {
        return new BIterator<>(right, this::onExternalInserted, this::onExternalBeforeRemoved);
    }

    @Override
    public Iterator<E> iterator() {
        return new LeftIterator<>(left);
    }

    private static class LeftIterator<E> implements Iterator<E> {

        private BiNode<E> node;

        public LeftIterator(BiNode<E> node) {
            this.node = node;
        }

        @Override
        public boolean hasNext() {
            return node != null;
        }

        @Override
        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            E data = node.data;
            node = node.right;
            return data;
        }
    }

    static class BiNode<E> {
        public E data;
        private BiNode<E> left;
        private BiNode<E> right;


        public BiNode(E data, BiNode<E> left, BiNode<E> right) {
            this.data = data;
            setLeft(left);
            setRight(right);
        }

        private void setLeft(BiNode<E> left) {
            this.left = left;
            if (left != null) left.right = this;
        }

        private void setRight(BiNode<E> right) {
            this.right = right;
            if (right != null) right.left = this;
        }

        public BiNode<E> linkOff() {
            if (left != null) left.right = right;
            if (right != null) right.left = left;
            return this;
        }
    }

    public static class BIterator<E> {

        protected BiNode<E> node;
        private final Consumer<BiNode<E>> onInserted;
        private final Consumer<BiNode<E>> onBeforeRemove;

        public BIterator(BiNode<E> node,
                         @NotNull Consumer<BiNode<E>> onInserted,
                         @NotNull Consumer<BiNode<E>> onBeforeRemove) {
            this.node = node;
            this.onInserted = requireNonNull(onInserted);
            this.onBeforeRemove = requireNonNull(onBeforeRemove);
        }

        public boolean isEmpty() {
            return node == null;
        }

        public void checkEmpty() {
            if (node == null) throw new NoSuchElementException();
        }

        public E current() {
            checkEmpty();
            return node.data;
        }

        public boolean stepLeft() {
            checkEmpty();
            if (node.left == null) return false;
            node = node.left;
            return true;
        }

        public boolean stepRight() {
            checkEmpty();
            if (node.right == null) return false;
            node = node.right;
            return true;
        }

        public E insertAsLeft(E element) {
            if (node == null) throw new NoSuchElementException();
            onInserted.accept(new BiNode<E>(element, node.left, node));
            return element;
        }

        public E insertAsRight(E element) {
            if (node == null) throw new NoSuchElementException();
            onInserted.accept(new BiNode<E>(element, node, node.right));
            return element;
        }

        public boolean removeAndTryLeft() {
            if (node == null) throw new NoSuchElementException();
            onBeforeRemove.accept(node);
            if (node.left != null) {
                node = node.linkOff().left;
                return true;
            }

            node = node.linkOff().right;
            return false;
        }

        public boolean removeAndTryRight() {
            if (node == null) throw new NoSuchElementException();
            onBeforeRemove.accept(node);
            if (node.right != null) {
                node = node.linkOff().right;
                return true;
            }

            node = node.linkOff().left;
            return false;
        }
    }
}
