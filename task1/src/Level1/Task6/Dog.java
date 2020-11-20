package Level1.Task6;

public class Dog extends Animal {
    public Dog(String name) {
        super(name);
    }

    @Override
    protected double getBasicRunLimit() {
        return 500;
    }

    @Override
    protected double getBasicJumpLimit() {
        return 0.5;
    }

    @Override
    protected double getBasicSwimLimit() {
        return 10;
    }
}
