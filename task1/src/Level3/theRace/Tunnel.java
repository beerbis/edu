package Level3.theRace;

import java.util.concurrent.Semaphore;

public class Tunnel extends Road {
    private final Semaphore semaphore;
    public Tunnel(int capacity) {
        super(80, "Тоннель");
        semaphore = new Semaphore(capacity);
    }

    @Override
    protected void doOnBeforeGoing(Car c) throws InterruptedException {
        super.doOnBeforeGoing(c);
        if (!semaphore.tryAcquire()) {
            System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
            semaphore.acquire();
        }
    }

    @Override
    protected void doOnAfterGoing(Car c) {
        super.doOnAfterGoing(c);
        semaphore.release();
    }
}
