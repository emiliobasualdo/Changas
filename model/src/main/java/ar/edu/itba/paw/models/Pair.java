package ar.edu.itba.paw.models;

import java.util.Map;
import java.util.Objects;

public class Pair<K, V> {

    private K key;
    private V value;

    private Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public static <K, V> Pair<K, V> buildPair(K key, V value) {
        return new Pair<K, V>(key, value);
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }


    public boolean equalsS(final Object obj) {
              if (obj == this) {
                     return true;
               }
              if (obj instanceof Map.Entry<?, ?>) {
                     final Map.Entry<?, ?> other = (Map.Entry<?, ?>) obj;
                      return Objects.equals(getKey(), other.getKey())
                              && Objects.equals(getValue(), other.getValue());
              }
               return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;

        return key.equals(pair.key) && value.equals(pair.value);
    }

}
