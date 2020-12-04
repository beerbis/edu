package Level2.Concurency;

import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    static final int size = 10_000_000;
    static final int h = size / 2;

    public static void main(String[] args) throws InterruptedException {
        float[] f1 = method1();
        printCompared(f1, method2(), "method2");

        //однозначной разницы в производитльности methodAlternative/methodAlternative2 не вижу, то один быстрее на 100-200мс, то другой.
        printCompared(f1, methodAlternative(), "methodAlternative"); //выбор редакции, с использованием AtomicInteger - делит пополам не объём данных на потоки, а именно время обсчёта
        printCompared(f1, methodAlternative2(), "methodAlternative2"); //Если без Atomic, и честно использовать уже synchronized()
    }

    private static void printCompared(float[] f1, float[] f2, String message) {
        for (int i = 0; i < size; i++) {
            if (f1[i] != f2[i]) {
                System.out.println(message + ", не равны: " + i);
                return;
            }
        }
        System.out.println(message + " - равны");
    }

    static float[] method1() {
        float arr[] = getArr();
        long time = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            arr[i] = calc(arr[i], i);
        }

        System.out.println(String.format("Время выполнения method1: %sms.", System.currentTimeMillis() - time));
        return arr;
    }

    static float[] method2() throws InterruptedException {
        float[] arr = getArr();
        long time = System.currentTimeMillis();

        float[] a1 = new float[h];
        float[] a2 = new float[h];
        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < h; i++) {
                a1[i] = calc(a1[i], i);
            }
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < h; i++) {
                //не уверен, предполагалось ли что в методе первом и втором мы должны получить одинаковые массивы на выходе,
                //но я всё же добавлю поправку на сбой индекса относительно изменившегося алгоритма рассчтёа в методе два:
                // `i` -> `h+1`.
                // Примечание: добавка `h+` сильно влияет на итоговое время рассчётов. Если первому потоку(i in [0..5KK])
                // достаточно около 1 сек, меньше; то второму(i in [5KK..10KK]) на одни только рассчты - 3.7-4 сек.
                a2[i] = calc(a2[i], h + i);
            }
        });
        t2.start();

        //  На что сетую: join не упоминался на лекции, и в методичке он также не упоминается. Максимум, на лекции была показана
        //    картинка с "объединением потоков", но не раскрывался секрет как это сделать. Из чего прихожу к выводу, что в задании
        //    использование join не предполагается, но альтернатив с использованием synchronized я не вижу.
        //  Если не пользоваться join - я не понял как предполагается дождаться выполнения потоков -
        //    на ком синхронизироваться? Если треды даже в самом своём начале за-synchronized-ся допустим
        //    на a1/a2 - это не гарантия успеха для главного потока. Главный поток может попробовать синхнуться, пусть и после `t*.start()`,
        //    на a1/a2, ровно вместо join-ов, но это не гарантия, возможно к том моменту какой-то поток, или оба,
        //    ещё даже не успели выполнить свои synchronized(a1)/synchronized(a2).
        //  Если есть недоверие к a1/a2 -
        //    можно создать по отдельному объекту для мониторов вместо них, это не изменит ровно ничего.
        t1.join();
        //сместим копирование a1 - поставим до t2.join: t2 - считается, a1 - копируется; экономим крохи на времени копирования
        System.arraycopy(a1, 0, arr, 0, h);

        t2.join();
        System.arraycopy(a2, 0, arr, h, h);

        //тонкий момент. время обсчёта каждого куска должно входить в итоговое? или всё же время обсчёта всех кусков(a1/a2).
        System.out.println(String.format("Время выполнения method2: %sms.", System.currentTimeMillis() - time));
        return arr;
    }

    private static float calc(float v, int i) {
        return (float)(v * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
    }

    private static float[] getArr() {
        float[] arr = new float[size];
        for (int i = 0; i < size; i++)
            arr[i] = 1;
        return arr;
    }

    static float[] methodAlternative() throws InterruptedException {
        float arr[] = getArr();
        long time = System.currentTimeMillis();

        AtomicInteger idx = new AtomicInteger(0);
        Runnable t = () -> {
            while (true) {
                int currentIdx = idx.getAndIncrement();
                if (currentIdx >= size) break;
                arr[currentIdx] = calc(arr[currentIdx], currentIdx);
            }
        };

        Thread t1 = new Thread(t);
        Thread t2 = new Thread(t);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(String.format("Время выполнения methodAlternative: %sms.", System.currentTimeMillis() - time));
        return arr;
    }


    /***
     * Как бы ссылка на Integer, но без дурацких Java-вских сайдэффектов, в том числе можно спокойно передать в замыкание(лямбду)
      */
    private static class IntStorage {
        int value;

        public IntStorage(int value) {
            this.value = value;
        }
    }

    static float[] methodAlternative2() throws InterruptedException {
        float arr[] = getArr();
        long time = System.currentTimeMillis();

        IntStorage idx = new IntStorage(0);
        Runnable t = () -> {
            while (true) {
                int currentIdx = 0;
                synchronized (idx) {
                    currentIdx = idx.value;
                    idx.value++;
                }
                if (currentIdx >= size) break;

                arr[currentIdx] = calc(arr[currentIdx], currentIdx);
            }
        };

        Thread t1 = new Thread(t);
        Thread t2 = new Thread(t);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(String.format("Время выполнения methodAlternative2: %sms.", System.currentTimeMillis() - time));
        return arr;
    }
}
