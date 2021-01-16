package Level3.waitingQueue;

public class WaitingPrinter {

    private static final int COUNT = 5;
    private static final char NO_CHAR = 0;

    private volatile char lastPrinted = NO_CHAR;

    public static void main(String[] args) {
        WaitingPrinter printer = new WaitingPrinter();

        new Thread(() -> printer.print('A', 'C', true)).start();
        new Thread(() -> printer.print('B', 'A', false)).start();
        new Thread(() -> printer.print('C', 'B', false)).start();
    }

    private synchronized void print(char literal, char trigger, boolean triggerOnNoChar) {
        for (int i = 1; i <= COUNT; i++) {
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
}
