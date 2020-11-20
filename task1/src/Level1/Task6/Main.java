package Level1.Task6;

public class Main {

    public static void main(String[] args) {
        Animal[] animals1 = {
                new Cat("cat1"),
                new Dog("dog1"),
                new Dog("dog2"),
                new Cat("cat2"),
                new Dog("dog3")
        };

        animals1[1].run(50);
        animals1[3].jump(2);
        animals1[4].swim(10);
        animals1[2].run(300);
        animals1[2].jump(.1);
        animals1[1].swim(10.1);
        animals1[0].run(600);
        animals1[1].jump(.5);
        animals1[2].swim(1);

    }
}
