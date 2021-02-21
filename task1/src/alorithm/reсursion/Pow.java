package alorithm.reсursion;

public class Pow {
    private static final double PRECISION_RATE = 0.000_000_001;

    public static void main(String[] args) {
        test(2, 8, 256);
        test(2, -2, 0.25);
        test(-0.25, -2, 16);
    }

    private static void test(double value, int pow, double expected) {
        double result = pow(value, pow);
        System.out.printf("%s. %f^%d = %f, expected=%f%n", almostEqual(result, expected), value, pow, result, expected);
    }

    /**
     * Возвести число в степень, рекурсивная реализация.
     * Никаких дробный степеней, извлекать корни как-то не хочется.
     *
     * @param value возводимое
     * @param pow степень
     * @return возведённое в степень
     */
    private static double pow(double value, int pow) {
        if (pow == 0) return 1;

        if (pow < 0) return 1 / pow(value, -pow);

        return value * pow(value, pow - 1);
    }

    private static boolean almostEqual(double d1, double d2) {
        return Math.abs(d1 - d2) < PRECISION_RATE;
    }
}
