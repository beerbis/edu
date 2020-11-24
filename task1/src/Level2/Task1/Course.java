package Level2.Task1;

import Level2.Task1.EdConvention.Test;

public class Course {
    private Test[] tests;

    public Course(Test... tests) {
        this.tests = tests;
    }

    public void doIt(Team team) {
        for (Student student : team.getMates()) {
            for (Test test : tests) {
                if (!test.doTest(student)) {
                    team.fail(student, test);
                    break;
                }
            }
        }
    }
}
