package Level1.Task6;

public class Cat extends Animal {
    public Cat(String name) {
        super(name);
    }

    @Override
    protected double getBasicRunLimit() {
        return 200;
    }

    @Override
    protected double getBasicJumpLimit() {
        return 2;
    }

    @Override
    protected double getBasicSwimLimit() {
        return 0;
    }
}
