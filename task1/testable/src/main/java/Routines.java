import java.util.Arrays;

public class Routines {
    public int[] takeAfter4(int... values) {
        for (int i = values.length; --i >= 0;)
            if (values[i] == 4) {
                i++;
                return i < values.length
                        ? Arrays.copyOfRange(values, i, values.length)
                        : null;
            }

        throw new RuntimeException("There are no one four");
    }
}
