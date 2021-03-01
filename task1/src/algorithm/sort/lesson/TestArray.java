package algorithm.sort.lesson;

public class TestArray {

    public static void main(String[] args) {
        int[] array = {1, 5, 4, 6};
        for (int value : array) {
            System.out.println(value);
        }

        System.out.println("-----");

        for (int i = 0; i <= array.length - 1; i++) {
            System.out.println(array[i]);
        }
        System.out.println("-----");

//        System.out.println(array[-1]);
//        System.out.println(array[array.length]);

//        array[4] = 153;
        int[] newArray = new int[array.length * 2]; // length = 8
        System.arraycopy(array, 0, newArray, 0, array.length);

        array = newArray;
        array[4] = 153;

        for (int value : newArray) {
            System.out.println(value);
        }

    }
}
