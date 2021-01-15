package Level3.waitingQueue;

public class WaitingPrinter {

    private static final int COUNT = 5;
    private static final char NO_CHAR = 0;

    private volatile char lastPrinted = NO_CHAR;

    public static void main(String[] args) {
        WaitingPrinter printer = new WaitingPrinter();

        new Thread(() -> printer.printThemAll('A', 'C', true)).start();
        new Thread(() -> printer.printThemAll('B', 'A', false)).start();
        new Thread(() -> printer.printThemAll('C', 'B', false)).start();
    }

    private void printThemAll(char literal, char trigger, boolean triggerOnNoChar) {
        for (int i = 1; i <= COUNT; i++) printOne(literal, trigger, triggerOnNoChar);
    }

    private synchronized void printOne(char literal, char trigger, boolean triggerOnNoChar) {
        while (lastPrinted != trigger && (!triggerOnNoChar || lastPrinted != NO_CHAR)) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
                return;
            }
        }

        System.out.print(literal);
        lastPrinted = literal;
        notifyAll();
    }
}
