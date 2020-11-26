package Level2.Task3;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PhoneNumbers {
    private final Map<String, Set<String>> map = new HashMap<>();

    public PhoneNumbers() {
    }


    public void add(String lastName, String number) {
        Set<String> numbers = map.get(lastName.toLowerCase());
        if (numbers == null) {
            numbers = new HashSet<>();
            map.put(lastName.toLowerCase(), numbers);
        }
        numbers.add(number);
    }

    public Set<String> get(String lastName) {
        return map.get(lastName.toLowerCase());
    }
}
