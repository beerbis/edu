package Level3.theRace;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class Race {
    private SyncStage start;
    private SyncStage finish;
    private ArrayList<Stage> stages;
    public ArrayList<Stage> getStages() { return stages; }

    public Race(int racerCount, Stage... stages) {
        start = new SyncStage("Старт!", racerCount, true);
        finish = new SyncStage("Финиш!", racerCount, false);

        this.stages = new ArrayList<>(stages.length + 2);
        this.stages.add(start);
        for (int i = 0; i < stages.length; i++) this.stages.add(stages[i]);
        this.stages.add(finish);
    }

    public void waitStarted() { start.waitFinished(); }
    public void waitFinished() { finish.waitFinished(); }

    private static class SyncStage extends Stage {
        private final CountDownLatch sync;
        private boolean syncingRaceStages;

        public SyncStage(String description, int racerCount, boolean syncingRaceStages) {
            super(description);
            sync = new CountDownLatch(racerCount);
            this.syncingRaceStages = syncingRaceStages;
        }

        @Override
        public void go(Car c) {
            sync.countDown();
            if (syncingRaceStages)
                waitFinished();
        }

        public boolean waitFinished() {
            try {
                sync.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
                return false;
            }
            return true;
        }
    }
}
