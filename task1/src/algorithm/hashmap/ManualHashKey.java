package algorithm.hashmap;

public class ManualHashKey {
    final int key;

    public ManualHashKey(int key) {
        this.key = key;
    }

    static ManualHashKey of(int n) {
        return new ManualHashKey(n);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ManualHashKey that = (ManualHashKey) o;
        return key == that.key;
    }

    @Override
    public int hashCode() {
        return key;
    }
}
