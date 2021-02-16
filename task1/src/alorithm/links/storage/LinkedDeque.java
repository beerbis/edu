package alorithm.links.storage;

import alorithm.filifo.buffers.Deque;
import alorithm.filifo.buffers.StorageIsEmptyException;

import java.util.Iterator;
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
    public Iterator<E> iterator() {
        return new RightNodeIterator<E>(right);
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

    static class RightNodeIterator<E> implements Iterator<E> {

        private Node<E> node;

        @Override
        public boolean hasNext() {
            return node != null;
        }

        @Override
        public E next() {
            E result = node.data;
            node = node.left;
            return result;
        }

        public RightNodeIterator(Node<E> node) {
            this.node = node;
        }
    }
}
