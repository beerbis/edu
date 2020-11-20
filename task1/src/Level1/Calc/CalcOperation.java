package Level1.Calc;

public abstract class CalcOperation {
    private char id;
    private String name;
    abstract public double perform(double a, double b);

    public CalcOperation(char id, String name) {
        this.id = id;
        this.name = name;
    }

    public char getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static CalcOperation plus = new CalcOperation('+', "plus") {
        @Override
        public double perform(double a, double b) {
            return a + b;
        }
    };

    public static CalcOperation minus = new CalcOperation('-', "minus") {
        @Override
        public double perform(double a, double b) {
            return a - b;
        }
    };

    public static CalcOperation div = new CalcOperation('/', "divide") {
        @Override
        public double perform(double a, double b) {
            return a / b;
        }
    };

    public static CalcOperation mul = new CalcOperation('*', "multiply") {
        @Override
        public double perform(double a, double b) {
            return a * b;
        }
    };

    public static CalcOperation getOperationById(char Id) {
        switch (Id) {
            case '+': return plus;
            case '-': return minus;
            case '*': return mul;
            case '/': return div;
        }

        return null;
    }
}
