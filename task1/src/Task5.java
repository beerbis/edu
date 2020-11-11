public class Task5 {
    public static void main(String[] args) {
        Task5Person[] persons = {
                new Task5Person("Иван иванович Иванов", "страшный инженер", "ivanov@mail.ru", "+3 (963) 9654-96-36", 1000, 56),
                new Task5Person("Ivanov Ivan", "Engineer", "ivivan@mailbox.com", "892312312", 30000, 30),
                new Task5Person("Пушник Александр Сергеевич", "придворный поэт", "pushkin@ya.ru", "", 100, 250),
                new Task5Person("Пукин Владимир В.", "безработный", "pukin@pfr.ru", "03", 1000000, 68),
                new Task5Person("Машенька", "иждивенец", "masha@yandex.ru", "9871647646", 10, 5)
        };

        for (Task5Person person: persons)
            if (person.getAge() > 40)
                System.out.println(person);
    }
}
