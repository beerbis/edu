package Level2.Task1;

import Level2.Task1.EdConvention.Test;

public class Team {
    private final Student mates[] = new Student[4];
    private final Test failed[] = new Test[4];

    public Student[] getMates() {
        return mates;
    }

    public Team(Student mate1, Student mate2, Student mate3, Student mate4) {
        this.mates[0] = mate1;
        this.mates[1] = mate2;
        this.mates[2] = mate3;
        this.mates[3] = mate4;
    }

    public void showResults() {
        for (int i = 0; i < mates.length; i++)
            System.out.println(mates[i].getName() + (failed[i] == null? " прошёл" : " не прошёл " + failed[i].getName()));
    }

    private int getStudentId(Student student) {
        for (int i = 0; i < mates.length; i++)
            if (mates[i] == student) return i;

        throw new StudentNotFound("Студент " + student.getName() + " не найден");
    }

    public void fail(Student student, Test test) {
        failed[getStudentId(student)] = test;
    }
}
