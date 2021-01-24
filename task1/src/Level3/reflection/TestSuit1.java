package Level3.reflection;

import Level3.reflection.testFramework.AfterSuite;
import Level3.reflection.testFramework.BeforeSuite;
import Level3.reflection.testFramework.Test;

public class TestSuit1 {

    @BeforeSuite
    public static void before() {
        System.out.println("сделал бефо");
    }

    @AfterSuite
    public static void after() {
        System.out.println("сделал афтер");
    }

    @Test(priority = 3)
    public static void test1() {
        System.out.println("сделал первый тест");
    }

    @Test(priority = 2)
    public static void test2() {
        throw new RuntimeException("сделал ещё тест");
    }

    @Test(priority = 1)
    public static void test3() {
        System.out.println("сделал и ещё один тест");
    }
}
