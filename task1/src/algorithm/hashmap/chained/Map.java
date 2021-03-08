package algorithm.hashmap.chained;

import java.util.Optional;

public interface Map<K, V> {
    Optional<V> put(K key, V value);
    Optional<V> get(K key);
    Optional<V> remove(K key);
    int getCount();
}
