package Level2.Task2;

import javafx.scene.chart.ScatterChart;

public class Main {
    public static void main(String[] args) {
        String[][] goodArray = {
                {"1", "02", "03", "04"},
                {"11", "12", "13", "14"},
                {"21", "22", "23", "24"},
                {"31", "32", "33", "34"}
        };
        String[][] badArraySize1 = {
                {"11", "12", "13", "14"},
                {"21", "22", "23", "24"},
                {"31", "32", "33", "34"}
        };
        String[][] badArraySize2 = {
                {"01", "02", "03", "04"},
                {"11", "12", "13", "14"},
                {"21", "22", "24"},
                {"31", "32", "33", "34"}
        };
        String[][] badArrayValue1 = {
                {"01", "02", "03", "04"},
                {"11", "12", null, "14"},
                {"21", "22", "23", "24"},
                {"31", "32", "33", "34"}
        };
        String[][] badArrayValue2 = {
                {"01", "02", "03", "04"},
                {"11", "12", "13", "14"},
                {"21", "22", "23kakashka", "24"},
                {"31", "32", "33", "34"}
        };

        calcArraySumHandled(goodArray, "goodArray");
        calcArraySumHandled(badArraySize1, "badArraySize1");
        calcArraySumHandled(badArraySize2, "badArraySize2");
        calcArraySumHandled(badArrayValue1, "badArrayValue1");
        calcArraySumHandled(badArrayValue2, "badArrayValue2");
    }

    private static void calcArraySumHandled(String[][] array, String name) {
        System.out.println("Calculating on " + name);
        try {
            System.out.println("\tis " + calcArraySum(array));
        } catch (MyArrayDataException|MyArraySizeException e) {
            e.printStackTrace();
        }
    }


    static int calcArraySum(String[][] array) throws MyArrayDataException {
        if (array.length != 4)
            throw new MyArraySizeException("Размер первого уровня массива не равен 4");
        for (int i = 0; i < 4; i++)
            if (array[i].length != 4)
                throw new MyArraySizeException("Размер элемента второго уровня, элемента " + i + " - не равен 4");

        int sum = 0;
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                try {
                    sum += Integer.valueOf(array[i][j]);
                } catch (Exception e) {
                    throw new MyArrayDataException("Приведение `array[" + i + ", " + j + "]` к Integer", e);
                }

        return sum;
    }
}
