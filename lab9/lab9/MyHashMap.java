package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  @author Your name here
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int DEFAULT_SIZE = 16;
    private static final double MAX_LF = 0.75;
    private int multiOfDefalutSize = 1;

    private ArrayMap<K, V>[] buckets;
    private int size;

    private int loadFactor() {
        return size / buckets.length;
    }

    public MyHashMap() {
        buckets = new ArrayMap[DEFAULT_SIZE];
        this.clear();
    }

    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = new ArrayMap<>();
        }
    }

    /** Computes the hash function of the given key. Consists of
     *  computing the hashcode, followed by modding by the number of buckets.
     *  To handle negative numbers properly, uses floorMod instead of %.
     */
    private int hash(K key) {
        if (key == null) {
            return 0;
        }

        int numBuckets = buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    /** Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (key == null) {
            throw new UnsupportedOperationException();
        }
        int numBucketKey = hash(key);
        return buckets[numBucketKey].get(key);
    }

    /** Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new UnsupportedOperationException();
        }
        int numBucketKey = hash(key);
        buckets[numBucketKey].put(key, value);
        size += 1;
        if (loadFactor() > MAX_LF) {
            resize();
        }
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    private void resize() {
        multiOfDefalutSize += 1;
        int bucketSize = DEFAULT_SIZE * multiOfDefalutSize;
        Set keySet = keySet();
        ArrayMap reBuckets = new ArrayMap();
        for (Object key : keySet) {
            reBuckets.put(key, get((K) key));
        }
        buckets = new ArrayMap[bucketSize];
        this.clear();
        for (Object key : reBuckets) {
            int hashNOfK = hash((K) key);
            put((K) key, (V) reBuckets.get(key));
        }
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> keyset = new HashSet<>();
        for (int i = 0; i < buckets.length; i += 1) {
            if (this.buckets[i] != null) {
                for (K key : buckets[i]) {
                    keyset.add(key);
                }
            }
        }
        return keyset;
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        if (key == null) {
            throw new UnsupportedOperationException();
        }
        V value = get(key);
        int hashNOfK = hash(key);
        buckets[hashNOfK].remove(key);
        return value;
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        if (key == null) {
            throw new UnsupportedOperationException();
        }
        V valueOfK = get(key);
        if (valueOfK.equals(value)) {
            remove(key);
            return value;
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}
