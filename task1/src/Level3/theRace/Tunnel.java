package Level3.theRace;

public class Tunnel extends Road {
    public Tunnel() {
        super(80, "Тоннель");
    }

    @Override
    protected void doOnBeforeGoing(Car c) {
        super.doOnBeforeGoing(c);
        System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
    }
}
