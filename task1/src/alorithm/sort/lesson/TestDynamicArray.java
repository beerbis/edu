package alorithm.sort.lesson;

import java.util.ArrayList;

public class TestDynamicArray {

    public static void main(String[] args) {
        Array<Integer> data = new ArrayImpl<>(4);
//        Array<Integer> data = new SortedArrayImpl<>();
        data.add(5);
        data.add(4);
        data.add(3);
        data.add(2);
        data.add(1);

        data.display();
//        data.sortBubble();
//        data.sortSelect();
        data.sortInsert();
        data.display();

        data.remove(Integer.valueOf(3));
        data.remove(2);
        data.display();

        System.out.println("data[1] = " + data.get(1));
        System.out.println("Find 2: " + data.contains(2));
        System.out.println("Find 222: " + data.contains(222));


        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);

        System.out.println(arrayList);

        arrayList.remove((Integer)2);

    }
}
