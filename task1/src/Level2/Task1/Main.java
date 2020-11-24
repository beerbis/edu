package Level2.Task1;

import Level2.Task1.Witchcraft.ExpectoPatronum;
import Level2.Task1.Witchcraft.Lumus;
import Level2.Task1.Witchcraft.PotFilling;

public class Main {
    public static void main(String[] args) {
        Course c = new Course(new PotFilling(), new Lumus(), new ExpectoPatronum());

        Team team = new Team(
                new Student("ms Granger", 15, 150, 5),
                new Student("mr Harry", 30, 110, 15),
                new Student("mr Wizly", 20, 30, 10),
                new Student("mr Longbottom", 1, 0, 10)
        ); // Создаем команду

        c.doIt(team); // Просим команду пройти полосу
        team.showResults(); // Показываем результаты
    }
}
