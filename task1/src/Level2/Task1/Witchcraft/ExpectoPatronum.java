package Level2.Task1.Witchcraft;

import Level2.Task1.EdConvention.Test;
import Level2.Task1.EdConvention.Testable;

public class ExpectoPatronum implements Test {
    @Override
    public boolean doTest(Testable testable) {
        return testable.useResources(10, 100, 10);
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }
}
