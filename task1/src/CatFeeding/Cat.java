package CatFeeding;

public class Cat {
    private String name;
    private int appetite;
    private boolean satiety = false;

    public Cat(String name, int appetite) {
        this.name = name;
        this.appetite = appetite;
    }

    public void doFeed(Plate plate) {
        satiety = plate.feedOut(appetite);
    }

    public void printSatietyInfo() {
        System.out.println(name + ": " + (satiety ? "сыт" : "голоден"));
    }
}
