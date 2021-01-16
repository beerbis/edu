package Level3.theRace;

public abstract class Stage {
    public final String description;

    public Stage(String description) {
        this.description = description;
    }

    public abstract void go(Car c);
}
