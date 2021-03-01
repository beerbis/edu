package algorithm.tree.lesson;

import java.util.TreeMap;

public class Test6 {

    public static void main(String[] args) {
        TreeMap<String, Integer> treeMap = new TreeMap<>();
        treeMap.put("s1", 23);
        treeMap.put("s2", 24);
        treeMap.put("s3", 25);
        treeMap.put("s1", 26);

//        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
//        treeMap.put(23, 23);
//        treeMap.put(24, 24);
//        treeMap.put(25, 25);
//        treeMap.put(26, 26);

        System.out.println(treeMap);

        Tree<Integer> tree = new TreeImpl<>();
        tree.addMayLoose(60);
        tree.addMayLoose(50);
        tree.addMayLoose(66);
        tree.addMayLoose(40);
        tree.addMayLoose(55);
        tree.addMayLoose(70);
        tree.addMayLoose(31);
        tree.addMayLoose(45);
        tree.addMayLoose(42);
        tree.addMayLoose(43);
        tree.addMayLoose(67);
        tree.addMayLoose(81);

        System.out.println("Find 70: " + tree.contains(70));
        System.out.println("Find 700: " + tree.contains(700));

        tree.display();
        tree.remove(40);
        tree.display();

//        tree.traverse(Tree.TraverseMode.IN_ORDER);
//        tree.traverse(Tree.TraverseMode.PRE_ORDER);
//        tree.traverse(Tree.TraverseMode.POST_ORDER);

    }
}
