package algorithm.hashmap.chained;

import com.sun.istack.internal.Nullable;

import java.util.Optional;

public class ChainedMap<K, V> implements Map<K, V> {
    final Pair<K, V>[] items;
    int count = 0;

    public ChainedMap(int initialCapacity) {
        this.items = new Pair[initialCapacity];
    }

    @Override
    public Optional<V> put(K key, V value) {
        int i = key.hashCode() % items.length;
        if (items[i] == null) {
            items[i] = new Pair<>(key, value);
            count++;
            return null;
        }

        Pair<K, V> actualTop = null;
        Pair<K, V> current = items[i];
        do {
            if (current.key.equals(key)) {
                V tmp = current.value;
                current.value = value;
                return Optional.ofNullable(tmp);
            }

            actualTop = current;
            current = current.next;
        } while (current != null);

        count++;
        actualTop.next = new Pair<>(key, value);
        return null;
    }

    /**
     * Получить значение по ключу. Для значения null, соответствуенно, просто empty Optional.
     * @param key ключ
     * @return значение по ключу. если такой ключь отсутствует в мапе - null.
     */
    @Override
    @Nullable
    public Optional<V> get(K key) {
        int i = key.hashCode() % items.length;
        Pair<K, V> current = items[i];

        while (current != null) {
            if (current.key.equals(key)) {
                return Optional.ofNullable(current.value);
            }

            current = current.next;
        }


        return null;
    }

    @Override
    public Optional<V> remove(K key) {
        int i = key.hashCode() % items.length;
        Pair<K, V> current = items[i];

        if (current == null) return null;

        if (current.key.equals(key)) {
            items[i] = current.next;
            count--;
            return Optional.ofNullable(current.value);
        }

        Pair<K, V> prev = current;
        current = prev.next;
        while (current != null) {
            if (current.key.equals(key)) {
                prev.next = current.next;
                count--;
                return Optional.ofNullable(current.value);
            }

            prev = current;
            current = prev.next;
        }

        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }

    static class Pair<K, V> {
        final K key;
        V value;
        Pair<K, V> next;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
