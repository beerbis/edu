package alorithm.links.storage;

import alorithm.filifo.buffers.Deque;
import alorithm.filifo.buffers.StorageIsEmptyException;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringJoiner;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import static java.util.Objects.requireNonNull;

public class LinkedDeque<E> implements Deque<E>, Iterable<E> {
    int count = 0;
    Node<E> left;
    Node<E> right;

    @Override
    public void pushLeft(E element) {
        left = new Node<>(element, null, left);
        if (count++ == 0) right = left;
    }

    @Override
    public void pushRight(E element) {
        right = new Node<>(element, right, null);
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

    @Override
    public NodeIterator<E> iterator() {
        return new NodeIterator<>(left, Node::getRight, Node::getLeft,
                inserted -> {
                    count++;
                    if (inserted.left == right) {
                        right = inserted;
                    } else if(inserted.right == left) {
                        left = inserted;
                    }
                },
                removed -> {
                    count--;
                    if (removed == left) left = removed.right;
                    if (removed == right) right = removed.left;
                });
    }

    static class Node<E> {
        public E data;
        private Node<E> left;
        private Node<E> right;


        public Node(E data, Node<E> left, Node<E> right) {
            this.data = data;
            setLeft(left);
            setRight(right);
        }

        public Node<E> getLeft() {
            return left;
        }

        public Node<E> getRight() {
            return right;
        }

        public void setLeft(Node<E> left) {
            this.left = left;
            if (left != null) left.right = this;
        }

        public void setRight(Node<E> right) {
            this.right = right;
            if (right != null) right.left = this;
        }

        public Node<E> linkOff() {
            if (left != null) left.right = right;
            if (right != null) right.left = left;
            return this;
        }
    }

    public static class NodeIterator<E> implements Iterator<E> {

        protected Node<E> node;
        private final UnaryOperator<Node<E>> getNext;
        private final UnaryOperator<Node<E>> getPrev;
        private final Consumer<Node<E>> onInserted;
        private final Consumer<Node<E>> onBeforeRemove;

        public NodeIterator(Node<E> node,
                            @NotNull UnaryOperator<Node<E>> getNext,
                            @NotNull UnaryOperator<Node<E>> getPrev,
                            @NotNull Consumer<Node<E>> onInserted,
                            @NotNull Consumer<Node<E>> onBeforeRemove) {
            this.node = node;
            this.getNext = requireNonNull(getNext);
            this.getPrev = getPrev;
            this.onInserted = requireNonNull(onInserted);
            this.onBeforeRemove = requireNonNull(onBeforeRemove);
        }

        @Override
        public boolean hasNext() {
            return node != null;
        }

        @Override
        public E next() {
            if (node == null) throw new NoSuchElementException();

            E result = node.data;
            node = getNext.apply(node);
            return result;
        }

        public E insertAsLeft(E element) {
            if (node == null) throw new NoSuchElementException();
            onInserted.accept(new Node<E>(element, node.left, node));
            return element;
        }

        public E insertAsRight(E element) {
            if (node == null) throw new NoSuchElementException();
            onInserted.accept(new Node<E>(element, node, node.right));
            return element;
        }

        public void remove() {
            if (node == null) throw new NoSuchElementException();
            onBeforeRemove.accept(node);
            node = getPrev.apply(node.linkOff());
        }
    }
}
