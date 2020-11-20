package Task6;

public abstract class Animal {
    private double runLimit;
    private double jumpLimit;
    private double swimLimit;
    private String name;

    abstract protected double getBasicRunLimit();
    abstract protected double getBasicJumpLimit();
    abstract protected double getBasicSwimLimit();
    protected int getDistortionPercent() {
        return 20;
    }

    public Animal(String name) {
        this.name = name;
        runLimit = getDistortedValue(getBasicRunLimit(), getDistortionPercent());
        jumpLimit = getDistortedValue(getBasicJumpLimit(), getDistortionPercent());
        swimLimit = getDistortedValue(getBasicSwimLimit(), getDistortionPercent());
    }

    private final double getDistortedValue(double value, int percent) {
        double distortion = value / 100 * (double)percent;
        return value
                + distortion * Math.random()
                - distortion * Math.random();
    }

    @Override
    public String toString() {
        return name + "{run=" + runLimit + "; jump=" + jumpLimit + "; swim=" + swimLimit + "}";
    }

    public void run(double amount) {
        System.out.println(this + ".run(" + amount + "): результат: run: " + (amount <= runLimit));
    }

    public void jump(double amount) {
        System.out.println(this + ".jump(" + amount + "): результат: jump: " + (amount <= jumpLimit));
    }

    public void swim(double amount) {
        System.out.println(this + ".swim(" + amount + "): результат: swim: " + (amount <= swimLimit));
    }
}
