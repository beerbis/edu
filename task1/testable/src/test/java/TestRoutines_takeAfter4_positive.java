import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * Положительный тест: "4" всегда есть в наборе.
 * */
@RunWith(Parameterized.class)
public class TestRoutines_takeAfter4_positive {

    private int[] paramArray;
    private int[] expected;

    public TestRoutines_takeAfter4_positive(int[] paramArray, int[] expected) {
        this.paramArray = paramArray;
        this.expected = expected;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                //most common case
                {   new int[] {0, 1, 4, 3, 5},  new int[] {3, 5}},
                //крайний случай "0"
                {new int[] {4},                 null},
                //крайний случай, most common
                {new int[] {1,2,3,4},           null},
                //крайний случай, комбинированный
                {new int[] {4,1,2,3,4},         null},

                //эвристический, на не-4, case 1
                {new int[] {1,2,3,4,5,6,7,8,9,0}, new int[] {5,6,7,8,9,0}},
                //эвристический, на не-4, case 2
                {new int[] {0,9,8,7,6,5,4,3,2,1}, new int[] {3,2,1}}
        });
    }

    @Test
    public void testTakeAfter4() {
        Assert.assertArrayEquals(
                expected,
                new Routines().takeAfter4(paramArray)
        );
    }
}
