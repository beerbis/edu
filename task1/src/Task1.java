public class Task1 {
    public static void main(String[] args) {
        //Целочисленные, знаковые
        byte number1b = 127;
        short number2b = 30000;
        int number4b = 65554;
        long number8b = 561767561476L;

        //С плавающей точкой
        float floatingPointedNonPrecise = 3216554.321F;
        double floatingPointedWithHigherPrecision = 1341354.65546;

        //Литерал
        char SingleLetter = 'Ы';

        //Строка
        String string = "Вот так да, целая строчищща!";
    }

    static double doSomeCalculation(double a, double b, double c, double d) {
        return a * (b + (c / d));
    }

    static boolean isSumBetween(int numberOne, int numberTwo) {
        int theSumm = numberOne + numberTwo;
        return 10 <= theSumm && theSumm <= 20;
    };

    static void printPositivity(int theNumber) {
        if (theNumber < 0) {
            System.out.println("Число - отрицательное");
        } else {
            System.out.println("Число - положительное");
        }
    }

    static boolean isNegative(int theNumber) {
        return theNumber < 0;
    }

    static void sayHello(String name) {
        System.out.println("Привет, " + name);
    }

    static boolean isLeapYer(int year) {
        return year % 4 == 0
                && (year % 100 != 0 || year % 400 == 0);
    }
}
