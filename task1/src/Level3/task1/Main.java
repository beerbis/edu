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

        playWithBoxes();
    }


    private static <T> void swap(T[] elems, int i1, int i2) {
        T tmp = elems[i2];
        elems[i2] = elems[i1];
        elems[i1] = tmp;
    }

    private static <T> List<T> toArrayList(T[] elems) {
        return new ArrayList<T>(Arrays.asList(elems));
    }

    static private final class AppleBox extends FruitBox<Apple>{

        public AppleBox(Apple... fruits) { super(fruits); }

        @Override
        protected float getSingleFruitWeight() { return 1f; }
    }

    static private final class OrangeBox extends FruitBox<Orange>{

        public OrangeBox(Orange... fruits) { super(fruits); }

        @Override
        protected float getSingleFruitWeight() { return 1.5f; }
    }

    private static void playWithBoxes() {
        final String eq = "Apples weight %s, oranges - %s. Коробки равны по весу";
        final String noteq = "Apples weight %s, oranges - %s. Коробки не равны по весу";

        AppleBox appleBox = new AppleBox(new Apple(), new Apple());
        OrangeBox orangeBox = new OrangeBox(new Orange(), new Orange());
        System.out.println(String.format(appleBox.compare(orangeBox) ? eq : noteq, appleBox.getWeight(), orangeBox.getWeight()));

        appleBox.add(new Apple());
        System.out.println(String.format(appleBox.compare(orangeBox) ? eq : noteq, appleBox.getWeight(), orangeBox.getWeight()));

        AppleBox appleBox1 = new AppleBox();
        appleBox.pourOver(appleBox1);

        System.out.println("old apple box got " + appleBox.getWeight());
        System.out.println("new apple box became " + appleBox1.getWeight());
    }

}
