package Level2.Task1.Witchcraft;

import Level2.Task1.EdConvention.Test;
import Level2.Task1.EdConvention.Testable;

public class PotFilling implements Test {
    @Override
    public boolean doTest(Testable testable) {
        return testable.useResources(0, 0, 10);
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }
}
