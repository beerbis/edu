package Level2.Task1;

import Level2.Task1.EdConvention.Testable;

public class Student implements Testable {
    private String name;
    private int hp;
    private int sp;
    private int str;

    public Student(String name, int hp, int sp, int str) {
        this.name = name;
        this.hp = hp;
        this.sp = sp;
        this.str = str;
    }

    @Override
    public boolean useResources(int hp, int sp, int str) {
        if (!(this.hp > hp && this.sp >= sp && this.str >= str)) return false;

        this.sp -= sp;
        this.hp -= hp;
        return true;
    }

    public String getName() {
        return name;
    }
}
