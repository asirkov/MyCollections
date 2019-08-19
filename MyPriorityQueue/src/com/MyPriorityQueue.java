package com;

import java.lang.reflect.Array;
import java.util.*;

public class MyPriorityQueue<T> implements Queue {

    private class MyIterator implements Iterator {
        private int index;

        MyIterator() { index = 0; }

        @Override
        public boolean hasNext() {
            return (index < size);
        }

        @Override
        public T next() {
            return queue[index++].data;
        }
    }

    private class Entry implements Comparable {
        private T data;
        public int priority;

        Entry() {
            this(null, 0);
        }

        Entry(T d, int p) {
            data = d;
            priority = (priority <= MIN_PRIORITY) ? MIN_PRIORITY : (priority >= MAX_PRIORITY ? MAX_PRIORITY : p);
        }

        @Override
        public int compareTo(Object o) {
            if(o == null) {
                throw new NullPointerException();
            }
            if(o.getClass() == this.getClass()) {
                Entry e = (Entry) o;
                return (e.priority - this.priority);
            } else {
                throw new ClassCastException();
            }
        }
    }


    private final int MAX_PRIORITY = 5;
    private final int MIN_PRIORITY = 0;

    private Entry[] queue;
    private int size;
    private Comparator<T> comparator;


    public MyPriorityQueue() {
        this(11);
    }

    public MyPriorityQueue(int initialCapacity) {
        this(initialCapacity, null);
    }

    public MyPriorityQueue(int initialCapacity, Comparator<T> c) {
        if(initialCapacity < 1) {
            throw new IllegalArgumentException();
        }

        /*
        @SuppressWarnings("unchecked")
        final Entry[] e = Entry[].class.cast( Array.newInstance(Entry.class.getComponentType(), initialCapacity) );
        //(Entry[]) new Object[initialCapacity];
        */

        //Object o = Array.newInstance(Entry.class, initialCapacity);
        //queue = (Entry[]) Array.newInstance(Entry.class, initialCapacity);
        queue = (Entry[]) Array.newInstance(Entry.class, initialCapacity);
        comparator = c;
    }

    private void ensureCapacity(int capacity) {
        if(capacity <= queue.length) {
            Entry[] oldQueue = queue;
            int oldCapacity = queue.length;
            int newCapacity = (int) (oldCapacity * 3) / 2 + 1;
            queue = (Entry[]) Array.newInstance(Entry.class, newCapacity);//(Entry[]) new Object[newCapacity];
            System.arraycopy(oldQueue, 0, queue, 0, size);
        }
    }

    @Override
    public int size() { return size; }

    @Override
    public boolean isEmpty() { return (size == 0); }

    @Override
    public boolean contains(Object o) {
        for(Entry i : queue) {
            if(i.data == o) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator iterator() {
        return new MyIterator();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(queue, size);
    }

    @Override
    public Object[] toArray(Object[] a) {
        a = new Object[queue.length];
        System.arraycopy(queue, 0, a, 0, size);
        return a;
    }

    private void siftUp(int index, T element) {
        if(comparator != null) {
            siftUpUsingComparator(index, element);
        } else {
            siftUpComparable();
        }
    }

    private void siftUpUsingComparator(int index, T element) {
        while(index > 0) {
            int parent = ( index - 1 ) >>> 1;
            T e = queue[parent].data;
            if(comparator.compare(element, e) >= 0) {
                break;
            }
            queue[index].data = e;
            index = parent;
        }
        queue[index].data = element;
    }

    private void siftUpComparable() {
        // Был такой вариант реализации.
        //Arrays.sort(queue, Entry::compareTo);

        int index = queue.length - 1;
        while(index > 1) {
            if(queue[index].compareTo(queue[index - 1]) >= 0) {
                break;
            }
            Entry buffer = queue[index];
            queue[index] = queue[index - 1];
            queue[index - 1] = buffer;
            index--;
        }
    }

    @Override
    public boolean add(Object o) {
        return offer(o, 0);
    }

    @Override
    public boolean offer(Object o) {
        return offer(o, 0);
    }

    public boolean offer(Object o, int priority) {
        ensureCapacity(size + 1);
        siftUp(size, (T) o);
        queue[size - 1] = new Entry((T) o, priority);
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int i = 0;
        for(; i < size; i++) {
            if(queue[i].equals(o))
                break;
        }
        if(i == (size - 1) && ! (queue[i].data == o) ) {
            return false;
        }
        System.arraycopy(queue, i + 1, queue, i, size - i - 1);
        queue[size--] = null;
        return true;
    }

    @Override
    public boolean addAll(Collection c) {
        if(c == null) {
            throw new NullPointerException();
        }
        boolean result = true;
        for(var i : c) {
            // always true
            result &= offer(i);
        }
        return result;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++)
            queue[i] = null;
        size = 0;
    }

    @Override
    public boolean retainAll(Collection c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection c) {
        if(c == null) {
            throw new NullPointerException();
        }
        boolean result = true;
        for(var i : c) {
            if(contains(i)) {
                result &= remove(i);
            }
        }
        return result;
    }

    @Override
    public boolean containsAll(Collection c) {
        if(c == null) {
            throw new NullPointerException();
        }
        boolean result = true;
        for(var i : c) {
            result &= contains(i);
        }
        return result;
    }

    @Override
    public Object remove() {
        if(size == 0) {
            throw new NoSuchElementException();
        }
        return  poll();
    }

    @Override
    public Object poll() {
        T e = queue[size - 1].data;
        queue[--size] = null;
        return e;
    }

    @Override
    public Object element() {
        if(size == 0) {
            throw new NoSuchElementException();
        }
        return peek();
    }

    @Override
    public Object peek() {
        return (size > 1 ? queue[size - 1].data : null);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for(Entry i : queue) {
            sb.append("[").append(i.toString()).append(i == queue[size - 1] ? "]" : ", ");
        }
        return sb.toString();
    }
}
