package CatFeeding;

public class Plate {
    private int food = 0;

    public Plate(int food) {
        addFood(food);
    }

    public void addFood(int food) {
        this.food += food;
    }

    public boolean feedOut(int n) {
        if (food < n) return false;
        food -= n;
        return true;
    }

    public void info() {
        System.out.println("plate: " + food);
    }
}

