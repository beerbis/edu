package Level1;

public class Task2 {
    public static void main(String[] args) {
        //exercise #1
        byte[] bins = {1, 1, 0, 0, 1, 0, 1, 1, 0, 0};
        for (int i = bins.length - 1; i >= 0; i--) {
            bins[i] = bins[i]==0 ? (byte)1 : (byte)0;
        }

        //exercise #2
        int[] thirds = new int[8];
        for (int i = 0; i < 8; i++)
            thirds[i] = i * 3;

        //exercise #3
        int[] arrForDoubling ={1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1};
        for (int i = arrForDoubling.length - 1; i >= 0; i--) {
            if (arrForDoubling[i] < 6)
                arrForDoubling[i] *= 2;
        }

        //exercise #4
        makeSquareArray(7);

        //exercise #5
        int[] minMaxArray = {1, 666, 3, 2, 11, 4, 5, 2, 4, 8, 9, -777};
        int minValue = minMaxArray[0];
        int maxValue = minMaxArray[0];
        for (int i = minMaxArray.length; --i > 0;){
            minValue = Math.min(minValue, minMaxArray[i]);
            maxValue = Math.max(maxValue, minMaxArray[i]);
        }
        System.out.println("Минимум массива:" + minValue);
        System.out.println("Максимум массива:" + maxValue);

        //exercise #6
        int[] mirrorArray = {2, 2, 2, 1, 2, 2, 10, 1};
        int[] mirrorArray2 = {1, 1, 1, 2, 1};
        int[] nonMirrorArray = {3, 3, 3, 2, 1, 1, 11, 3};
        System.out.println("Массив с зеркальной суммой? case1: " + isMirrorSummedArray(mirrorArray));
        System.out.println("Массив с зеркальной суммой? case2: " + isMirrorSummedArray(mirrorArray2));
        System.out.println("Массив с зеркальной суммой? case3: " + isMirrorSummedArray(nonMirrorArray));

        //exercise #7
        int[] x = {1,2,3};
        shiftElements(x, 0);
        shiftElements(x, 4);
        shiftElements(x, -4);
        shiftElements(x, 3);
        shiftElements(x, -3);
        shiftElements(x, 1);
        shiftElements(x, -1);
    }

    private static void shiftElements(int[] array, int n) {
        if (n == 0) return;

        if (n > 0) {
            for (int i = array.length - 1; i >= n; i--)
                array[i] = array[i - n];

            for (int i = Math.min(n, array.length) - 1; i >= 0; i--)
                array[i] = 0;
        } else {
            for (int i = 0; i < array.length + n; i++)
                array[i] = array[i - n];

            for (int i = Math.max(array.length + n, 0); i < array.length ; i++)
                array[i] = 0;
        }
    }

    private static boolean isMirrorSummedArray(int[] array) {
        int leftSum = 0;
        int rightSum = 0;

        for (int i = array.length; --i >= 0;)
            rightSum += array[i];

        for (int i = array.length; --i >= 0;) {
            rightSum -= array[i];
            leftSum += array[i];

            if (leftSum == rightSum) return true;
        }

        return false;
    }

    static void makeSquareArray(int size){
        byte[][] squareArray = new byte[size][size];

        for (int i = 0; i < size; i++) {
            squareArray[i][i] = 1;
            squareArray[i][size - 1 - i] = 1;
        }
    }
}
