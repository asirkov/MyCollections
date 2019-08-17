package com;

import java.util.*;

public class MyHashMap<K, V> implements Map {

    private class Entry implements Map.Entry {
        private K key;
        private V value;
        private int hash;
        private Entry next;

        Entry(K k, V v, int h, Entry n) {
            key = k;
            value = v;
            hash = h;
            next = n;
        }

        @Override
        public String toString() {
            return "[" + key + " : " + value + ", hash = " + hash + "]";
        }

        @Override
        public K getKey() { return key; }

        @Override
        public V getValue() { return value; }

        @Override
        public Object setValue(Object v) {
            // В таблицу можно добавить и null, закоментил этот блок
            /*
            if(v == null) {
                throw new NullPointerException("Value is null!");
            }
            */

            V oldValue = value;
            value = (V) v;
            return oldValue;
        }
    }

    private abstract class MyIterator<T> implements Iterator {
        private Entry next;
        private Entry current;
        private int currentIndex;

        MyIterator() {
            if(MyHashMap.this.mapSize > 0) {
                Entry[] t = table;
                while(currentIndex < MyHashMap.this.mapSize && (next = t[currentIndex++]) == null) ;
            }
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        public Entry nextEntry() {
            Entry e = next;
            if(e == null) {
                throw new NoSuchElementException();
            }

            if((next = e.next) == null) {
                Entry[] t = table;
                while (currentIndex < t.length && (next = t[currentIndex++]) == null) ;
            }
            current = e;
            return e;
        }

        @Override
        public void remove() {
            if(current == null) {
                throw new IllegalStateException();
                // throw new NoSuchElementException();
            }
            K k = current.key;
            current = null;
            MyHashMap.this.removeForKey(k);
        }
    }

    private class ValueIterator extends MyIterator<V> {
        @Override
        public V next() {
            return nextEntry().getValue();
        }
    }

    private class KeyIterator extends MyIterator<K> {
        @Override
        public K next() {
            return nextEntry().getKey();
        }
    }

    private class EntryIterator extends MyIterator<Entry> {
        @Override
        public Entry next() {
            return nextEntry();
        }
    }

    public Iterator<K> newKeyIterator() { return new KeyIterator(); }
    public Iterator<V> newValueIterator() { return new ValueIterator(); }
    public Iterator<Entry> newEntryIterator() { return new EntryIterator(); }


    private Entry[] table;
    private int mapSize;

    private float loadFactor;
    private float threshold;

    MyHashMap(int capacity) {
        if(capacity < 0) {
            throw new IllegalArgumentException("Illegal capacity " + capacity);
        }
        loadFactor = 0.75f;
        threshold = (int) (capacity * loadFactor);
        int newCapacity = 1;
        while(newCapacity < capacity) {
            // Приравнивает новую вместительность к разряду 2-ки. Побитовый сдвиг на 1 разряд влево ( умножение на 2 )
            newCapacity <<= 1;
        }
        table = (Entry[]) new Object[newCapacity];
        mapSize = 0;
    }

    MyHashMap() {
        this(16);
    }

    private static int hash(int h) {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    @Override
    public int size() {
        return mapSize;
    }

    @Override
    public boolean isEmpty() {
        return (mapSize == 0);
    }

    private Entry getEntry(Object key) {
        int hash = (key == null) ? 0 : hash(key.hashCode());
        for(Entry e = table[indexFor(hash, table.length)]; e != null; e = e.next) {
            K k = e.key;
            if(e.hash == hash && (key == k || key.equals(k)) ) {
                return e;
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(Object key) {
        return getEntry(key) != null;
    }

    private boolean containsNullValue() {
        Entry[] t = table;
        for(int i = 0; i < t.length; i++) {
            for(Entry e = t[i]; e != null; e = e.next) {
                if(e.value == null) {
                    return  true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        if(value == null) {
            return containsNullValue();
        }

        Entry[] t = table;
        for(int i = 0; i < t.length; i++) {
            for(Entry e = t[i]; e != null; e = e.next) {
                if(value.equals(e.value)) {
                    return  true;
                }
            }
        }
        return false;
    }

    private V getForNullKey() {
        for(Entry e = table[0]; e != null; e = e.next) {
            if(e.key == null) {
                return e.value;
            }
        }
        return null;
    }

    @Override
    public V get(Object key) {
        if(key == null) {
            getForNullKey();
        }
        int hash = hash(key.hashCode());
        for(Entry e = table[indexFor(hash, table.length)]; e != null; e = e.next) {
            K k = e.key;
            if(e.hash == hash && (k == key || key.equals(k)) ) {
                return e.value;
            }
        }
        return null;
    }

    static int indexFor(int h, int length) {
        return h & (length - 1);
    }

    private void addEntry(K key, V value, int hash, int index) {
        Entry e = table[index];
        table[index] = new Entry(key, value, hash, e);
        if(mapSize++ >= threshold) {
            resize(table.length * 2);
        }
    }

    private V putForNullKey(V v) {
        for(Entry e = table[0]; e != null; e = e.next) {
            if(e.key == null) {
                V oldValue = e.value;
                e.value = (V) v;
                return oldValue;
            }
        }
        addEntry(null, v, 0, 0);
        return null;
    }

    @Override
    public V put(Object key, Object value) {
        if(key == null) {
            return putForNullKey((V) value);
        }

        int hash = hash(key.hashCode());
        int i = indexFor(hash, table.length);
        for(Entry e = table[i]; e != null; e = e.next) {
            Object k = e.key;
            if(hash == e.hash && (key == k && key.equals(k) )) {
                V oldValue = e.value;
                e.value = (V) value;
                return oldValue;
            }
        }
        addEntry((K) key, (V) value, hash, i);
        return  null;
    }

    private void transfer(Entry[] newTable) {
        Entry[] src = table;
        int newCapacity = newTable.length;

        for(int j = 0; j < src.length; j++) {
            Entry e = src[j];
            if(e != null) {
                src[j] = null;
                do {
                    Entry next = e.next;
                    int i = indexFor(e.hash, newCapacity);
                    e.next = newTable[i];
                    newTable[i] = e;
                    e = next;
                } while(e != null);
            }
        }
    }

    public void resize(int newCapacity) {
        Entry[] newTable = (Entry[]) new Object[newCapacity];
        transfer(newTable);
        table = newTable;
        threshold = (int)(newCapacity * loadFactor);
    }

    private Entry removeForKey(Object key) {
        int hash = (key == null) ? 0 : hash(key.hashCode());
        int i = indexFor(hash, table.length);

        Entry prev = table[i];
        Entry e = prev;

        while(e != null) {
            Entry next = e.next;
            K k = e.key;
            if(e.hash == hash && (key == k || key.equals(k)) ) {
                mapSize--;

                if(prev == e) {
                    table[i] = next;
                } else {
                    prev.next = prev;
                }
                return e;
            }
            prev = e;
            e = next;
        }
        return null;
    }

    @Override
    public Object remove(Object key) {
        Entry e = removeForKey(key);
        return (e == null) ? null : e.value;
    }

    @Override
    public void putAll(Map m) {

    }

    @Override
    public void clear() {
        Entry[] t = table;
        for(int i = 0; i < t.length; i++) {
            t[i] = null;
        }
        mapSize = 0;
    }

    @Override
    public Set keySet() {

        return null;
    }

    @Override
    public Collection values() {
        return null;
    }

    @Override
    public Set<Entry> entrySet() {
        return null;
    }
}
