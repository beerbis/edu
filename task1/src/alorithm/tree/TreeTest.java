package alorithm.tree;

import alorithm.tree.lesson.Tree;
import alorithm.tree.lesson.TreeImpl;

import java.util.ArrayList;
import java.util.List;

public class TreeTest {
    private static final int DEPTH_LIMIT = 4;
    private static final int RANGE_LOW = -25;
    private static final int RANGE_HIGH = 25;

    public static void main(String[] args) {
        List<Tree<Integer>> trees = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            trees.add(makeRandomTree());
        }

        System.out.printf("Сбаллансировано деревьев: %d. Из %d-ти."
                , trees.stream().filter(Tree::isBalanced).count()
                , trees.size());
    }

    public static Tree<Integer> makeRandomTree() {
        Tree<Integer> tree = new TreeImpl<>(DEPTH_LIMIT);

        for (int i = 0; i < 15; i++) {
            try {
                tree.add(randomBetween(RANGE_LOW, RANGE_HIGH));
            } catch (Tree.GodItIsTooDeepException e) {
                //Забить и ладно, не было сказано что делать c избыточной глубиной
            }
        }

        return tree;
    }

    private static int randomBetween(int low, int high) {
        int idxInRange = (int)((high - low + 1) * Math.random());
        return idxInRange + low;
    }
}
