package Level3.task1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Integer[] ints = {2, 3, 5, 7, 13, 17};

        swap(ints, 1, 5);
        System.out.println(Arrays.toString(ints));

        List<Integer> list = toArrayList(ints);
        System.out.println(list);


    }

    private static <T> void swap(T[] elems, int i1, int i2) {
        T tmp = elems[i2];
        elems[i2] = elems[i1];
        elems[i1] = tmp;
    }

    private static <T> List<T> toArrayList(T[] elems) {
        return new ArrayList<T>(Arrays.asList(elems));
    }


}
