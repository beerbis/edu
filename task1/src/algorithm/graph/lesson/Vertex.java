package algorithm.graph.lesson;

import java.util.Objects;

public class Vertex {

    private final String label;
    private boolean isVisited;

    /**
     * Используется при поиске пути, и после остаётся грязной, но это не критично, т.к.
     * в конце следующего поиска все шаги в зад от конечной вершины всё равно будут переписанными и актуальны.
     */
    private Vertex stepBack;

    public Vertex(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }


    public Vertex getStepBack() {
        return stepBack;
    }

    public void setStepBack(Vertex stepBack) {
        this.stepBack = stepBack;
    }

    public void markSteppedTo(Vertex steppedTo) {
        steppedTo.setStepBack(this);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return Objects.equals(label, vertex.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "label='" + label + '\'' +
                '}';
    }
}
