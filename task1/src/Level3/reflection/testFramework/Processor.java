package Level3.reflection.testFramework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class Processor {
    private static final String SUITE_CAPTION =      "Набор тестов: %s";
    private static final String TEST_CAPTION =       "[ START] Тест: %s";
    private static final String TEST_OK =            "[  OK  ] Тест: %s";
    private static final String TEST_FAILED =        "[ FAIL ] Тест: %s";
    private static final String SUITE_SUCCESS_RATE = "         Успех теста %d из %d";

    private static final String ERROR_FORMAT_SUITE = "%s: %s";
    private static final String ERROR_FORMAT_METHOD = "%s::%s: %s";
    private static final String ERROR_ANNOTATION_BEFORE_TWICE = "`@BeforeSuite` может быть только один";
    private static final String ERROR_ANNOTATION_AFTER_TWICE = "`@AfterSuite` может быть только один";
    private static final String ERROR_ANNOTATION_NO_TESTS = "не найдено тестов, тестирующие методы должны быть аннотированы `@Test`";
    private static final String ERROR_METHOD_NON_STATIC = "тестирующий метод, не `static`";
    private static final String ERROR_METHOD_PARAMCOUNT = "тестирующий метод(аннотированный `@Test`), ожидалось %d параметров, а не %d";
    private static final String ERROR_METHOD_NON_PUBLIC = "метод теста(аннотированный `@Test\\@BeforeSuite\\@AfterSuite`), не `public`";


    public static void start(String suitName) throws ClassNotFoundException, TestSuitConfigError {
        start(Class.forName(suitName));
    }

    public static void start(Class suit) throws TestSuitConfigError {
        SuitDesc desc = SuitDesc.collect(suit);

        System.out.println(String.format(SUITE_CAPTION, desc.caption));
        int success = desc.execute();
        System.out.println(String.format(SUITE_SUCCESS_RATE, success, desc.tests.size()));
    }

    private static void checkMethod(Method method, int paramCount) throws TestSuitConfigError {
        int mod = method.getModifiers();
        if (!Modifier.isStatic(mod)) throw new TestSuitConfigError(method, ERROR_METHOD_NON_STATIC);
        if (!Modifier.isPublic(mod)) throw new TestSuitConfigError(method, ERROR_METHOD_NON_PUBLIC);
        if (method.getParameterCount() != paramCount) throw new TestSuitConfigError(method
                , String.format(ERROR_METHOD_PARAMCOUNT, paramCount, method.getParameterCount()));
    }

    public static class TestDesc {
        public final String caption;
        public final Method testMethod;
        public final Object[] args;
        public final int priority;

        public TestDesc(Method method, int priority) throws TestSuitConfigError {
            checkMethod(method, 0);
            testMethod = method;
            args = null;
            caption = method.getName();
            this.priority = priority;
        }

        /**
         * Метод работает в лучших традициях: "Выполнить любой ценой".
         * */
        public boolean execute() {
            try {
                testMethod.invoke(null, args);
                return true;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }

            return false;
        }
    }

    public static class SuitDesc {
        public final String caption;
        public final Method before, after;
        public final List<TestDesc> tests;

        public SuitDesc(Class suite, Method before, Method after, List<TestDesc> tests) throws TestSuitConfigError {
            if (tests.size() == 0) throw new TestSuitConfigError(suite, ERROR_ANNOTATION_NO_TESTS);
            if (before != null) checkMethod(before, 0);
            if (after != null) checkMethod(after, 0);

            this.caption = suite.getName();
            this.before = before;
            this.after = after;
            this.tests = tests;
        }

        public static SuitDesc collect(Class suit) throws TestSuitConfigError {
            Method after = null;
            Method before = null;
            List<TestDesc> tests = new ArrayList<>();

            for (Method method : suit.getDeclaredMethods()) {
                if (method.isAnnotationPresent(BeforeSuite.class)) {
                    if (before != null) throw new TestSuitConfigError(suit, ERROR_ANNOTATION_BEFORE_TWICE);
                    before = method;
                } else if (method.isAnnotationPresent(AfterSuite.class)) {
                    if (after != null) throw new TestSuitConfigError(suit, ERROR_ANNOTATION_AFTER_TWICE);
                    after = method;
                } else {
                    Test an = method.getAnnotation(Test.class);
                    if (an != null) tests.add(new TestDesc(method, an.priority()));
                }
            }

            tests.sort((a, b) -> a.priority - b.priority);
            return new SuitDesc(suit, before, after, tests);
        }

        /**
         * @return Количество пройденных тестов
         * */
        public int execute() {
            try {
                if (before != null) before.invoke(null);
            } catch (InvocationTargetException|IllegalAccessException e) {
                e.printStackTrace();
                return 0;
            }

            int result = 0;
            for (TestDesc test : tests) {
                System.out.println(String.format(TEST_CAPTION, test.caption));
                boolean ok = test.execute();
                result += ok ? 1 : 0;
                System.out.println(String.format(ok ? TEST_OK : TEST_FAILED, test.caption));
            }

            try {
                if (after!= null) after.invoke(null);
            } catch (InvocationTargetException|IllegalAccessException e) {
                e.printStackTrace();
            }

            return result;
        }
    }

    public static class TestSuitConfigError extends Throwable {
        public TestSuitConfigError(String message) {
            super(message);
        }

        public TestSuitConfigError(Class suit, String message) {
            super(String.format(ERROR_FORMAT_SUITE
                    , suit.getName()
                    , message));
        }

        public TestSuitConfigError(Method method, String message) {
            super(String.format(ERROR_FORMAT_METHOD
                    , method.getDeclaringClass().getName()
                    , method.getName()
                    , message));
        }
    }
}
