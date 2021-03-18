package algorithm.hashmap;

import algorithm.hashmap.chained.ChainedMap;
import algorithm.hashmap.chained.Map;

import java.util.Optional;

@SuppressWarnings("OptionalAssignedToNull")
public class MapTest {
    static final ManualHashKey KEY01 = ManualHashKey.of(1);
    static final ManualHashKey KEY02 = ManualHashKey.of(2);
    static final ManualHashKey KEY03 = ManualHashKey.of(3);
    static final ManualHashKey KEY04 = ManualHashKey.of(4);
    static final ManualHashKey KEY05 = ManualHashKey.of(5);
    static final ManualHashKey KEY11 = ManualHashKey.of(11);
    static final ManualHashKey KEY12 = ManualHashKey.of(12);
    static final ManualHashKey KEY13 = ManualHashKey.of(13);
    static final ManualHashKey KEY14 = ManualHashKey.of(14);
    static final ManualHashKey KEY15 = ManualHashKey.of(15);
    static final ManualHashKey KEY51 = ManualHashKey.of(51);
    static final ManualHashKey KEY62 = ManualHashKey.of(62);
    static final ManualHashKey KEY73 = ManualHashKey.of(73);
    static final ManualHashKey KEY84 = ManualHashKey.of(84);
    static final ManualHashKey KEY95 = ManualHashKey.of(95);

    public static void main(String[] args) {
        Map<ManualHashKey, String> map = new ChainedMap<>(10);

        map.put(KEY01, "azz01-1");
        map.put(KEY02, "azz02-1");
        map.put(KEY03, "azz03-1");
        map.put(KEY04, "azz04-1");
        map.put(KEY05, "azz05-1");
        map.put(KEY11, "azz11-1");
//        map.put(KEY12, "azz12-1");
        map.put(KEY13, "azz13-1");
        map.put(KEY14, "azz14-1");
//        map.put(KEY15, "azz15-1");
        map.put(KEY51, "azz51-1");
        map.put(KEY62, "azz62-1");
        map.put(KEY73, "azz73-1");
        map.put(KEY84, "azz84-1");
        map.put(KEY95, "azz95-1");

        assert map.getCount() == 13;
        assert map.get(KEY01) != null;
        assert "azz01-1".equals(map.get(KEY01).orElse(null));
        assert map.get(KEY15) == null;
        assert map.get(KEY95) != null;
        assert "azz95-1".equals(map.get(KEY95).orElse(null));
        Optional<String> old = map.put(KEY95, "azz95-2");
        assert old != null;
        assert "azz95-1".equals(old.orElse(null));
        assert map.getCount() == 13;

        old = map.put(KEY15, "azz15-2");
        assert old == null;
        assert "azz15-2".equals(map.get(KEY15).orElse(null));
        old = map.put(KEY15, null);
        assert old != null;
        assert "azz15-2".equals(old.orElse(null));
        Optional<String> val = map.get(KEY15);
        assert val != null;
        assert !val.isPresent();
        assert map.getCount() == 14;

        old = map.remove(KEY05);
        assert old != null;
        assert "azz05-1".equals(old.orElse(null));
        assert map.remove(KEY05) == null;
        assert map.remove(KEY12) == null;
        assert map.getCount() == 13;

        System.out.println("Позравления с успешным сценарием!");
    }
}
