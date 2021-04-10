package algorithm.graph.lesson;

import com.sun.istack.internal.NotNull;

import java.util.*;
import java.util.function.Consumer;

public class Graph implements IGraph {

    private final List<Vertex> vertexList;
    private final boolean[][] adjMat;

    public Graph(int maxVertexCount) {
        this.vertexList = new ArrayList<>(maxVertexCount);
        this.adjMat = new boolean[maxVertexCount][maxVertexCount];
    }

    @Override
    public void addVertex(String label) {
        vertexList.add(new Vertex(label));
    }

    public void addVertex(String... labels) {
        for (String label : labels) {
            vertexList.add(new Vertex(label));
        }
    }

    @Override
    public void addEdge(String startLabel, String endLabel) {
        int startIndex  = indexOf(startLabel);
        int endIndex    = indexOf(endLabel);

        if (startIndex == -1 || endIndex == -1) {
            throw new IllegalArgumentException("Invalid label for vertex");
        }

        adjMat[startIndex][endIndex] = true;
        adjMat[endIndex][startIndex] = true;
    }

    @Override
    public void addEdges(String startLabel, String secondLabel, String... others) {
        addEdge(startLabel, secondLabel);
        for (String other : others) {
            addEdge(startLabel, other);
        }
    }

    private int indexOf(String startLabel) {
        for (int index = 0; index < getSize(); index++) {
            if (vertexList.get(index).getLabel().equals(startLabel)) {
                return index;
            }
        }

        return -1;
    }

    @Override
    public int getSize() {
        return vertexList.size();
    }

    @Override
    public void display() {
        for (int i = 0; i < getSize(); i++) {
            System.out.print(vertexList.get(i));
            for (int j = 0; j < getSize(); j++) {
                if (adjMat[i][j]) {
                    System.out.print(" -> " + vertexList.get(j));
                }
            }
            System.out.println();
        }
    }

    @Override
    public void dfs(String startLabel) {
        int startIndex = indexOf(startLabel);
        if (startIndex == -1) {
            throw new IllegalArgumentException("Invalid start label");
        }

        Stack<Vertex> stack = new Stack<>();
        Vertex vertex = vertexList.get(startIndex);

        visitVertex(stack, vertex);
        while (!stack.isEmpty()) {
            vertex = getNearUnvisitedVertex(stack.peek());
            if (vertex != null) {
                visitVertex(stack, vertex);
            } else {
                stack.pop();
            }
        }

        resetVertexState();
    }

    @Override
    public void bfs(String startLabel) {
        int startIndex = indexOf(startLabel);
        if (startIndex == -1) {
            throw new IllegalArgumentException("Invalid start label");
        }

        Queue<Vertex> queue = new LinkedList<>();
        Vertex vertex = vertexList.get(startIndex);

        visitVertex(queue, vertex);
        while (!queue.isEmpty()) {
            vertex = getNearUnvisitedVertex(queue.peek());
            if (vertex != null) {
                visitVertex(queue, vertex);
            } else {
                queue.remove();
            }
        }

        resetVertexState();
    }

    public Iterable<String> path(String startLabel, String endLabel) {
        int startIndex = indexOf(startLabel);
        if (startIndex == -1) {
            throw new IllegalArgumentException("Invalid start label");
        }

        int endIndex = indexOf(endLabel);
        if (endIndex == -1) {
            throw new IllegalArgumentException("Invalid end label");
        }

        System.out.println("\n Путь: " + startLabel + " -> " + endLabel);
        if (startIndex == endIndex) return Arrays.asList(startLabel);

        try {
            Queue<Vertex> queue = new LinkedList<>();
            Vertex vertex = vertexList.get(startIndex);
            vertex.setStepBack(null);
            visitVertex(queue, vertex, t -> {});
            while (!queue.isEmpty()) {
                vertex = getNearUnvisitedVertex(queue.peek());
                if (vertex != null) {
                    visitVertex(queue, vertex, queue.peek()::markSteppedTo);
                    if (vertex.getLabel().equals(endLabel)) return backTraverseReversed(vertex);
                } else {
                    queue.remove();
                }
            }
        } finally {
            resetVertexState();
        }
        return Collections.emptyList();
    }

    @NotNull
    private Queue<String> backTraverseReversed(@NotNull Vertex vertex) {
        Objects.requireNonNull(vertex, "vertex should be passed...");
        ArrayDeque<String> deque = new ArrayDeque<>();
        do {
            deque.addFirst(vertex.getLabel());
            vertex = vertex.getStepBack();
        } while (vertex != null);
        return deque;
    }


    private void resetVertexState() {
        for (Vertex vertex : vertexList) {
            vertex.setVisited(false);
        }
    }

    private Vertex getNearUnvisitedVertex(Vertex current) {
        int currentIndex = vertexList.indexOf(current);
        for (int i = 0; i < getSize(); i++) {
            if (adjMat[currentIndex][i] && !vertexList.get(i).isVisited()) {
                return vertexList.get(i);
            }
        }
        return null;
    }

    private void visitVertex(Stack<Vertex> stack, Vertex vertex) {
        System.out.println(vertex);
        vertex.setVisited(true);
        stack.push(vertex);
    }
    private void visitVertex(Queue<Vertex> queue, Vertex vertex) {
        visitVertex(queue, vertex, System.out::println);
    }
    private void visitVertex(Queue<Vertex> queue, Vertex vertex, Consumer<Vertex> consumer) {
        consumer.accept(vertex);
        vertex.setVisited(true);
        queue.add(vertex);
    }
}
