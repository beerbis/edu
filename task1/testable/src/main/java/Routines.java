import java.util.Arrays;

public class Routines {
    public static final int THE_ONE = 1;
    public static final int THE_FOUR = 4;

    public int[] takeAfter4(int... values) {
        for (int i = values.length; --i >= 0;)
            if (values[i] == THE_FOUR) {
                i++;
                return i < values.length
                        ? Arrays.copyOfRange(values, i, values.length)
                        : null;
            }

        throw new RuntimeException("There are no one four");
    }

    public static boolean checkIsOneAndFourArray(int... array) {
        boolean gotOne = false;
        boolean gotFour = false;

        for (int i = array.length; --i >= 0;)
            if (array[i] == THE_ONE) {
                gotOne = true;
            } else if (array[i] == THE_FOUR) {
                gotFour = true;
            } else {
                return false;
            }

        return gotOne && gotFour;
    }
}
