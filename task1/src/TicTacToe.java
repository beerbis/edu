import java.util.Scanner;

public class TicTacToe {
    final static int SIZE = 3;
    final static char EMPTY = '#';
    private static Scanner scanner = new Scanner(System.in);

    private static class Point {
        int x;
        int y;
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {
        char[][] field = new char[SIZE][SIZE];
        emptifyField(field);


        while (true) {
            printField(field);
            Point userMovement;
            do {
                userMovement = queryMovement(field);
                if (putMovement(field, userMovement, 'X')) break;
                System.out.println("Клетка занята. Выбрите другую.");
            } while (true);

            if (checkWining(field, userMovement)) {
                printField(field);
                System.out.println("Поздравляю! Вы выиграли.");
                return;
            }
            if (isEndOfField(field)) {
                printField(field);
                System.out.println("Игра окончилась ничьей");
                return;
            }


            Point aiMovement = getAiMovement(field, 'X', 'O');
            putMovement(field, aiMovement, 'O');
            if (checkWining(field, aiMovement)) {
                printField(field);
                System.out.println("Соболезную! Компуктер победил.");
                return;
            }
            if (isEndOfField(field)) {
                printField(field);
                System.out.println("Игра окончилась ничьей");
                return;
            }
        }
    }

    private static Point getAiMovement(char[][] field, char userSign, char aiSign) {
        Point[] points = {};
        int maxScore = 0;
        for (int x = 0; x < field.length; x++)
            for (int y = 0; y < field.length; y++) {
                Point point = new Point(x, y);
                int score = getPointScore(field, point, userSign, aiSign);

                if (score < maxScore) continue;
                if (score > maxScore) {
                    points = new Point[0];
                    maxScore = score;
                }

                points = appendPoints(points, point);
            }
        return chooseAnyPoint(points);
    };

    private static int getPointScore(char[][] field, Point point, char enemySign, char mySign) {
        if (field[point.x][point.y] != EMPTY) return -1;
        if (testWining(field, point, enemySign)) return 100500;
        if (testWining(field, point, mySign)) return 100501;
        return 1;
    }

    private static Point[] appendPoints(Point[] points, Point point) {
        Point[] newPoints;
        if (points == null) {
            newPoints = new Point[1];
            newPoints[0] = point;
        } else {
            newPoints = new Point[points.length + 1];
            for (int i = 0; i < points.length; i++)
                newPoints[i] = points[i];

            newPoints[newPoints.length - 1] = point;
        }

        return newPoints;
    }

    private static Point chooseAnyPoint(Point[] points) {
        return points[(int)(Math.random() * points.length)];
    }

    private static  boolean testWining(char[][] field, Point point, char sign) {
        if (field[point.x][point.y] != EMPTY)
            return false;

        field[point.x][point.y] = sign;
        boolean result = checkWining(field, point);
        field[point.x][point.y] = EMPTY;

        return result;
    }

    private static boolean isEndOfField(char[][] field) {
        for (int x = 0; x < field.length; x++)
            for (int y = 0; y < field.length; y++) {
                if (field[x][y] == EMPTY) return false;
            }
        return true;
    }

    private static boolean checkWining(char[][] field, Point point) {
        if (field[point.x][point.y] == EMPTY) return false;

        boolean result = true;
        for (int x = 1; x < field.length; x++)
            if (field[x][point.y] != field[x-1][point.y]) {
                result = false;
                break;
            }
        if (result) return result;

        result = true;
        for (int y = 1; y < field[point.x].length; y++)
            if (field[point.x][y] != field[point.x][y-1]){
                result = false;
                break;
            }
        if (result) return result;

        if (point.x == point.y) {
            result = true;
            for (int i = 1; i < field.length; i++)
                if (field[i][i] != field[i - 1][i - 1]) {
                    result = false;
                    break;
                }
            if (result) return result;
        }

        if (point.x == field.length - point.y - 1) {
            result = true;
            for (int i = 1; i < field.length; i++)
                if (field[i][field.length - 1 - i] != field[i - 1][field.length - i]) {
                    result = false;
                    break;
                }
        }

        return result;
    }

    private static void emptifyField(char[][] field) {
        for (int x = 0; x < field.length; x++)
            for (int y = 0; y < field[x].length; y++)
                field[x][y] = EMPTY;
    }

    private static void printField(char[][] field) {
        for (int x = 0; x < field.length; x++) {
            for (int y = 0; y < field[x].length; y++)
                System.out.print(" " + field[x][y]);
            System.out.println("");
        }
    }

    private static int queryIntInRange(String message, int low, int high) {
        int value;
        do {
            System.out.print(message + "(" + low + "-" + high + ")?: ");
            value = scanner.nextInt();
        } while (value < low || value > high);

        return value;
    }

    private static Point queryMovement(char[][] field) {
        int x = queryIntInRange("Номер строки", 1, SIZE) - 1;
        int y = queryIntInRange("Номер колонки", 1, SIZE) - 1;
        return new Point(x, y);
    }

    private static boolean putMovement(char[][] field, Point point, char sign) {
        if (field[point.x][point.y] != EMPTY)
            return false;

        field[point.x][point.y] = sign;
        return true;
    }
}
