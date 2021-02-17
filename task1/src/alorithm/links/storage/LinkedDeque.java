package alorithm.links.storage;

import alorithm.filifo.buffers.Deque;
import alorithm.filifo.buffers.StorageIsEmptyException;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

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
        class LeftIterator<E> extends DequeIterator<E> {

            public LeftIterator(LinkedDeque<E> deque) {
                super(deque, deque.left);
            }

            @Override
            protected Node<E> getTextNode(Node<E> node) {
                return node.right;
            }
        }

        return new LeftIterator<>(this);
    }

    static class Node<E> {
        E data;
        Node<E> left;
        Node<E> right;

        public Node(E data, Node<E> left, Node<E> right) {
            this.data = data;
            setLeft(left);
            setRight(right);
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

    abstract private static class DequeIterator<E> extends NodeIterator<E> {
        private final LinkedDeque<E> deque;

        public DequeIterator(LinkedDeque<E> deque, Node<E> initial) {
            super(initial);
            this.deque = deque;
        }

        @Override
        public E insertAsLeft(E element) {
            E result = super.insertAsLeft(element);
            if (node.left == null) deque.left = node;
            deque.count++;
            return result;
        }

        @Override
        public E insertAsRight(E element) {
            E result = super.insertAsRight(element);
            if (node.right == null) deque.right = node;
            deque.count++;
            return result;
        }

        @Override
        public void remove() {
            Node<E> removed = node;
            super.remove();
            if (removed == deque.left) deque.left = node;
            if (removed == deque.right) deque.right = node;
            deque.count--;
        }
    }

    public abstract static class NodeIterator<E> implements Iterator<E> {

        protected Node<E> node;
        protected Node<E> prev;

        @Override
        public boolean hasNext() {
            return node != null;
        }

        @Override
        public E next() {
            if (node == null) throw new NoSuchElementException();

            E result = node.data;
            node = getTextNode(node);
            return result;
        }

        abstract protected Node<E> getTextNode(Node<E> node);

        public E insertAsLeft(E element) {
            if (node == null) throw new NoSuchElementException();
            new Node<E>(element, node.left, node);
            return element;
        }

        public E insertAsRight(E element) {
            if (node == null) throw new NoSuchElementException();
            new Node<E>(element, node, node.right);
            return element;
        }

        public void remove() {
            if (node == null) throw new NoSuchElementException();
            node = node.linkOff().left;
        }

        public NodeIterator(Node<E> node) {
            this.node = node;
        }
    }
}
