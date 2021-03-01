package algorithm.tree.lesson;

import java.util.Stack;
import java.util.function.Consumer;

public class TreeImpl<E extends Comparable<? super E>> implements Tree<E> {

    private class NodeAndParent {
        final Node<E> current;
        final Node<E> parent;
        final int depth;

        public NodeAndParent(Node<E> current, Node<E> parent, int depth) {
            this.current = current;
            this.parent = parent;
            this.depth = depth;
        }
    }

    private static final int NO_DEPTH_LIMIT = -1;
    private final int maxDepth;
    private int size;
    private Node<E> root;

    public TreeImpl(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public TreeImpl() {
        this(NO_DEPTH_LIMIT);
    }

    @Override
    public void add(E value) throws GodItIsTooDeepException {
        Node<E> newNode = new Node<>(value);

        if (isEmpty()) {
            root = newNode;
            size++;
            return;
        }

        NodeAndParent nodeAndParent = doFind(value);
        if (nodeAndParent.current != null) {
            // nodeAndParent.current.setValue(value)
            return;
        }

        if (maxDepth != NO_DEPTH_LIMIT && nodeAndParent.depth > maxDepth) {
            throw new Tree.GodItIsTooDeepException();
        }

        Node<E> previous = nodeAndParent.parent;

        Consumer<Node<E>> setter = previous.isLeftChild(value)
                ? previous::setLeftChild
                : previous::setRightChild;
        setter.accept(newNode);
        size++;
    }

    @Override
    public void addMayLoose(E value) {
        try {
            add(value);
        } catch (GodItIsTooDeepException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean contains(E value) {
        NodeAndParent nodeAndParent = doFind(value);
        return nodeAndParent.current != null;
    }

    private NodeAndParent doFind(E value) {
        Node<E> current = root;
        Node<E> previous = null;
        int depth = 0;
        while (current != null) {
            if (current.getValue().equals(value)) {
                return new NodeAndParent(current, previous, depth);
            }
            depth++;
            previous = current;
            current = current.isLeftChild(value)
                    ? current.getLeftChild()
                    : current.getRightChild();
        }

        return new NodeAndParent(null, previous, depth);
    }

    @Override
    public boolean remove(E value) {
        NodeAndParent nodeAndParent = doFind(value);
        Node<E> removedNode = nodeAndParent.current;
        Node<E> parentNode = nodeAndParent.parent;

        if (removedNode == null) {
            return false;
        }

        if (removedNode.isLeaf()) {
            if (removedNode == root) {
                root = null;
            } else if (parentNode.isLeftChild(value)) {
                parentNode.setLeftChild(null);
            } else {
                parentNode.setRightChild(null);
            }
        }
        else if (removedNode.hasOnlyOneChild()) {
            Node<E> childNode = removedNode.getLeftChild() != null
                    ? removedNode.getLeftChild()
                    : removedNode.getRightChild();

            if (removedNode == root) {
                root = childNode;
            } else if (parentNode.isLeftChild(value)) {
                parentNode.setLeftChild(childNode);
            } else {
                parentNode.setRightChild(childNode);
            }
        }
        else {
            Node<E> successor = getSuccessor(removedNode);
            if (removedNode == root) {
                root = successor;
            } else if (parentNode.isLeftChild(value)) {
                parentNode.setLeftChild(successor);
            } else {
                parentNode.setRightChild(successor);
            }

            successor.setLeftChild(removedNode.getLeftChild());
        }

        size--;
        return true;
    }

    private Node<E> getSuccessor(Node<E> removedNode) {
        Node<E> successor = removedNode;
        Node<E> successorParent = null;
        Node<E> current = removedNode.getRightChild();

        while (current != null) {
            successorParent = successor;
            successor = current;
            current = current.getLeftChild();
        }

        if (successor != removedNode.getRightChild() && successorParent != null) {
            successorParent.setLeftChild(successor.getRightChild());
            successor.setRightChild(removedNode.getRightChild());
        }

        return successor;
    }

    @Override
    public void traverse(TraverseMode mode) {
        switch (mode) {
            case IN_ORDER:
                inOrder(root);
                break;
            case PRE_ORDER:
                preOrder(root);
                break;
            case POST_ORDER:
                postOrder(root);
                break;
            default:
                throw new IllegalArgumentException("Unknown traverse mode: " + mode);
        }
    }

    private void inOrder(Node<E> current) {
        if (current == null) {
            return;
        }

        inOrder(current.getLeftChild());
        System.out.println(current.getValue());
        inOrder(current.getRightChild());
    }

    private void preOrder(Node<E> current) {
        if (current == null) {
            return;
        }

        System.out.println(current.getValue());
        preOrder(current.getLeftChild());
        preOrder(current.getRightChild());
    }

    private void postOrder(Node<E> current) {
        if (current == null) {
            return;
        }

        postOrder(current.getLeftChild());
        postOrder(current.getRightChild());
        System.out.println(current.getValue());
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
    public boolean isBalanced() {
        return isBalanced(root);
    }

    private static boolean isBalanced(Node node) {
        return (node == null) ||
                isBalanced(node.getLeftChild()) &&
                        isBalanced(node.getRightChild()) &&
                        Math.abs(height(node.getLeftChild()) - height(node.getRightChild())) <= 1;
    }

    private static int height(Node node) {
        return node == null ? 0 : 1 + Math.max(height(node.getLeftChild()), height(node.getRightChild()));
    }

    @Override
    public void display() {
        Stack<Node<E>> globalStack = new Stack<>();
        globalStack.push(root);
        int nBlanks = 64;

        boolean isRowEmpty = false;
        System.out.println("................................................................");

        while (!isRowEmpty) {
            Stack<Node<E>> localStack = new Stack<>();

            isRowEmpty = true;
            for (int i = 0; i < nBlanks; i++) {
                System.out.print(" ");
            }

            while (!globalStack.isEmpty()) {
                Node<E> tempNode = globalStack.pop();
                if (tempNode != null) {
                    System.out.print(tempNode.getValue());
                    localStack.push(tempNode.getLeftChild());
                    localStack.push(tempNode.getRightChild());
                    if (tempNode.getLeftChild() != null || tempNode.getRightChild() != null) {
                        isRowEmpty = false;
                    }
                } else {
                    System.out.print("--");
                    localStack.push(null);
                    localStack.push(null);
                }
                for (int j = 0; j < nBlanks * 2 - 2; j++) {
                    System.out.print(" ");
                }
            }

            System.out.println();

            while (!localStack.isEmpty()) {
                globalStack.push(localStack.pop());
            }

            nBlanks /= 2;
        }
        System.out.println("................................................................");
    }
}
