package Level1.CatFeeding;

public class Main {
    public static void main(String[] args) {
        Plate plate = new Plate(20);
        Cat[] cats = {
                new Cat("Tihon", 2),
                new Cat("Maha", 5),
                new Cat("Klizma", 10),
                new Cat("Straus", 4),
                new Cat("Homa", 6),
                new Cat("Plush", 7),
        };

        plate.info();

        for (Cat cat: cats)
            cat.doFeed(plate);

        for (Cat cat: cats)
            cat.printSatietyInfo();

        plate.info();
        plate.addFood(15);
        plate.info();
    }
}
