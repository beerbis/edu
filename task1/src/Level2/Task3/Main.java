package Level2.Task3;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        testUniqueWords();
        testPhoneNumbers();
    }

    private static void testPhoneNumbers() {
        PhoneNumbers phones = new PhoneNumbers();
        phones.add("Пупкин", "123658");
        phones.add("Пупкин", "98561");
        phones.add("пупкин", "123");
        phones.add("Зелибоба", "89431");
        phones.add("Улюкаев", "64265414");
        phones.add("Улюкаев", "65414");

        if (phones.get("Зелибоба") == null) throw new TestFailedException();
        if (phones.get("Зелибоба").size() != 1) throw new TestFailedException();
        if (!phones.get("Зелибоба").contains("89431")) throw new TestFailedException();

        if (phones.get("Улюкаев") == null) throw new TestFailedException();
        if (phones.get("улюкаев").size() != 2) throw new TestFailedException();
        if (!phones.get("Улюкаев").contains("64265414")) throw new TestFailedException();
        if (!phones.get("улюкаев").contains("65414")) throw new TestFailedException();

        if (phones.get("Пупкин") == null) throw new TestFailedException();
        if (phones.get("пупкин").size() != 3) throw new TestFailedException();
        if (!phones.get("Пупкин").contains("123658")) throw new TestFailedException();
        if (!phones.get("пупкин").contains("98561")) throw new TestFailedException();
        if (!phones.get("Пупкин").contains("123")) throw new TestFailedException();

        if (phones.get("Карпускулис") != null) throw new TestFailedException();
    }

    private static void testUniqueWords() {
        String[] nonUniqueWords = {
                "one", "two", "thee", "one", "fore", "five", "two", "two", "six", "seven", "five", "eight", "twenty"
        };

        Map<String, Integer> uniques = new HashMap<>();
        for (String s: nonUniqueWords) {
            Integer actual = uniques.get(s);
            uniques.put(s, actual == null ? 1 : actual + 1);
        }

        uniques.forEach((s, x) -> System.out.println(s + ": " + x));
    }
}
