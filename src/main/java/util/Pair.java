package util;

/**
 * Created by bharat on 8/4/18.
 */
public class Pair<K,V>{
    final K first;
    final V sec;

    public Pair(K first, V sec) {
        this.first = first;
        this.sec = sec;
    }

    public K getFirst() {
        return first;
    }

    public V getSec() {
        return sec;
    }

    public String toString(){
        return "("+first+ ", " + sec +")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair<?, ?> pair = (Pair<?, ?>) o;

        if (!first.equals(pair.first)) return false;
        return sec.equals(pair.sec);
    }

    @Override
    public int hashCode() {
        int result = first.hashCode();
        result = 31 * result + sec.hashCode();
        return result;
    }
}
