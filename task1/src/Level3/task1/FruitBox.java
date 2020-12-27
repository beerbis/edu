package Level3.task1;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class FruitBox<T extends Fruit> {
    private static final float MEASUREMENT_ERROR = 0.000001f;
    private ArrayList<T> content;

    public FruitBox(T... fruits) {
        content = new ArrayList<>(Arrays.asList(fruits));
    }

    public float getWeight() {
        return content.size() * getSingleFruitWeight();
    }

    protected abstract float getSingleFruitWeight();

    public boolean compare(FruitBox<?> anotherBox) {
        return Math.abs(getWeight() - anotherBox.getWeight()) < MEASUREMENT_ERROR;
    }

    public void pourOver(FruitBox<T> intoBox) {
        intoBox.content.addAll(content);
        content.clear();
    }

    public void add(T fruit) {
        content.add(fruit);
    }
}
