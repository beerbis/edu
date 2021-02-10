package alorithm.sort;

import alorithm.sort.lesson.Array;
import alorithm.sort.lesson.ArrayImpl;
import com.sun.istack.internal.NotNull;
import org.omg.CORBA.INTERNAL;

import java.util.Random;
import java.util.function.Consumer;

public class SpeedTest {
    static final int SIZE = 100_000;

    private final Integer[] testData = fillRandomValues(doPart1());

    public static void main(String[] args) {
        SpeedTest test = new SpeedTest();

        //64.8(25.8, греется сильно дольше всех прочих) - мерзко
        test.test(Array::sortBubble, "sortBubble");

        //21.1(15.6) - хорошо, относитель `sortBubble`
        test.test(Array::sortSelect, "sortSelect");

        //22.8(13.3) - разница копеечная, прогревается лучше `sortSelect`.
        //  Непрогретый страдает от трудозатрадности доп. вызова `compare()` на итерацию.
        test.test(Array::sortBiSelect, "sortBiSelect");

        //20.8-22.8(13.3) - практически никакой разницы, относительно `sortSelect`,
        //  то быстрее, то медленнее `sortSelect`,  видимо зависит от набора данных.
        //  На прогретом работает ровно как `sortBiSelect` и чуть быстрее `sortSelect` - всегда.
        test.test(Array::sortInsert, "sortInsert");

        //8.4(не греется) - совсем другое дело
        test.test(Array::sortInsertPerf, "sortInsertPerf");

        //Вывод. Алгоритмы - хорошо; оптимизация, даже тупа и скучная в лоб - рулит.
    }

    /**
     * 1. Создать массив большого размера (миллион элементов).
     *    Исходя из реализации п.2, тип возьмём не простой.
     * @return массив большого размера
     */
    private Integer[] doPart1() {
        return new Integer[SIZE];
    }

    /**
     * 2. Написать методы удаления, добавления, поиска элемента массива.
     *    Возьмём наработки урока. Тем более к реализации методов в задании
     *    вообщ отсутствуют требования, лишено педагогического почина.
     * @return реализация обёрки над массивом, с переданными данными.
     */
    private Array<Integer> doPart2(Integer[] data) {
        return new ArrayImpl<>(data);
    }

    /**
     * 3. Заполнить массив случайными числами.
     * @param data выделенный массив под данные
     * @return указатель на тот же самый входной массив
     */
    private Integer[] fillRandomValues(Integer[] data) {
        Random rnd = new Random();

        for (int i = 0; i < data.length; i++)
            data[i] = rnd.nextInt();

        return data;
    }

    /**
     * Выполнить и описать в консоли замер
     * @param header заголовок замера, опиание
     * @param action замеряемое действие
     */
    private void measure(String header, Runnable action) {
        System.out.print(header);
        long start = System.currentTimeMillis();
        action.run();
        System.out.println(": " + (double)(System.currentTimeMillis() - start) / 1000);
    }

    /**
     * выплнить тест производительности обработки "массива"
     * @param sortMethod метод обработки данных
     * @param methodName описание метода
     */
    public void test(Consumer<Array<Integer>> sortMethod, String methodName) {
        Array<Integer> storage = doPart2(testData);
        measure(methodName, () -> sortMethod.accept(storage));
    }
}
