package Level3.theRace;

public class Road extends Stage {
    private final int length;

    public Road(int length) {
        this(length, "Дорога");
    }

    protected Road(int length, String roadKindName) {
        super(roadKindName + " " +length + " метров");
        this.length = length;
    }

    @Override
    public final void go(Car c) {
        try {
            doOnBeforeGoing(c);

            try {
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
                System.out.println(c.getName() + " закончил этап: " + description);
            } finally {
                doOnAfterGoing(c);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println(c.getName() + " сошёл с этапа: " + description);
        }
    }

    protected void doOnBeforeGoing(Car c) throws InterruptedException {}
    protected void doOnAfterGoing(Car c) {}
}
