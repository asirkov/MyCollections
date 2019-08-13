package com;

import java.util.*;

public class MyQueue<T> implements Queue<T> {

    private class MyIterator implements Iterator {

        private int index;

        MyIterator() { index = 0; }

        @Override
        public boolean hasNext() { return (index < MyQueue.this.queueSize); }

        @Override
        public T next() { // throws NoSuchElementException {
            try {
                // Не знаю, надо ли явно делать throw. И стоит ли ловить исключение внутри
                /*
                if(index < 0 || index >= MyQueue.this.queueSize) {
                    throw new NoSuchElementException(index: " + index + ", queue size:" + MyQueue.this.queueSize + ".");
                }
                */
                return MyQueue.this.data[index];
            } catch (NoSuchElementException ex) {
              System.err.println("Error: index: " + index + ", queue size:" + MyQueue.this.queueSize + ".");
            }
            return null;
        }
    }


    private int queueSize;
    private T[] data;

    /* */
    MyQueue() {
        data = (T[]) new Object[10];
        queueSize = 0;
    }

    /* */
    MyQueue(int newSize) {
        data = (T[]) new Object[newSize];
        queueSize = 0;
    }

    private void ensureCapacity(int capacity) {
        try {
            if (capacity >=  data.length) {
                T[] oldData = data;
                int oldCapacity = data.length;
                int newCapacity = (oldCapacity * 3) / 2 + 1;
                data = (T[]) new Object[newCapacity];
                System.arraycopy(oldData, 0, data, 0, queueSize);
            }
        }
        catch (Exception ex) { System.err.println("Error at MyArrayList.ensureCapacity(int)\nError message: " + ex.getMessage()); }
    }

    @Override
    public int size() { return queueSize; }

    @Override
    public boolean isEmpty() { return (queueSize == 0); }

    @Override
    public boolean contains(Object o) {
        try {
            for(T i : data) {
                // Не уверен, можно ли здесь использовать equals, можно изменить на ==
                if(i.equals(o)) return true;
            }
        } catch (Exception ex) {
            System.err.println("Error at contains(Object),\nError message: " + ex.getMessage());
            return false;
        }
        return false;
    }

    @Override
    public MyIterator iterator() { return new MyIterator();}

    @Override
    public T[] toArray() { return data; }

    // Не знаю, как реализовать этот toArray
    @Override
    public <T1> T1[] toArray(T1[] a) {
        return (T1[]) new Object[0];
    }

    @Override
    public boolean add(T t) throws IllegalStateException {
        if((queueSize + 1) >= data.length) {
            throw new IllegalStateException("No space is currently available!");
        }
        try {
            ensureCapacity(queueSize + 1);
            System.arraycopy(data, 0, data, 1, queueSize);
            data[0] = t;
            queueSize++;
        } catch (Exception ex) {
            System.err.println("Error at add(T ),\nError message: " + ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean remove(Object o) {
        try {
            int i = 0;
            for(; i < queueSize; i++) {
                if(data[i].equals(o))
                    break;
            }
            if(i == (queueSize - 1) && (!data[i].equals(o)) ) {
                return false;
            }
            System.arraycopy(data, i, data, i - 1, queueSize);
            queueSize--;

        } catch(Exception ex) {
            System.err.println("Error at remove(T ),\nError message: " + ex.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        boolean result = true;
        try {
            for(var i : c) {
                if (i == null) {
                    throw new NullPointerException("Collection contains null element(s).");
                }
                if( !contains(i) ) {
                    result = false;
                    break;
                }
            }
        }
        catch (Exception ex) {
            result = false;
            System.err.println("Error at containsAll(Collection<>)\nError message: " + ex.getMessage());
        }
        return result;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean result = true;
        int newSize = c.size();
        T[] newData;
        try {
            newData = (T[]) c.toArray();

            // Check not null
            ensureCapacity(queueSize + newSize);
            System.arraycopy(newData, 0, data, queueSize - 1, newSize);
            queueSize += newSize;
        }
        catch (Exception ex) {
            result = false;
            System.err.println("Error at addAll(Collection<>)\nError message: " + ex.getMessage());
        }
        return result;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean result = true;
        try {
            for(var i : c) {
                if (i == null) {
                    throw new NullPointerException("Collection contains null element(s).");
                }
                while (contains(i)) {
                    remove(i);
                }
            }
        }
        catch (Exception ex) {
            result = false;
            System.err.println("Error at removeAll(Collection<>)\nError message: " + ex.getMessage());
        }
        return result;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean result = true;
        try {
            for(var i : data) {
                if (i == null) {
                    throw new NullPointerException("Collection contains null element(s).");
                }
                if (!c.contains(i)) {
                    remove(i);
                }
            }
        }
        catch (Exception ex) {
            result = false;
            System.err.println("Error at removeAll(Collection<>)\nError message: " + ex.getMessage());
        }
        return result;
    }

    @Override
    public void clear() {
        try {
            data = (T[]) new Object[0];
            queueSize = 0;
        } catch (UnsupportedOperationException ex) {
            System.err.println("Error at clear(),\nError message: " + ex.getMessage());
        }
    }

    @Override
    public boolean offer(T t) {
        try {
            if(t == null) {
                throw new NullPointerException();
            }

            ensureCapacity(queueSize + 1);
            System.arraycopy(data, 0, data, 1, queueSize);
            data[0] = t;
            queueSize++;
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    @Override
    public T remove() throws NoSuchElementException {
        if (queueSize > 0) {
            T result = data[queueSize - 1];
            data[queueSize - 1] = null;
            queueSize--;
            return result;
        }
        else {
            throw new NoSuchElementException("Queue is empty!");
        }
    }

    @Override
    public T poll() {
        T result = null;
        if(queueSize > 0) {
            result = data[queueSize - 1];
            data[queueSize - 1] = null;
            queueSize--;
        }
        return result;
    }

    @Override
    public T element() throws NoSuchElementException {
        if(queueSize > 0) {
            return data[queueSize - 1];
        } else {
            throw new NoSuchElementException("Queue is empty!");
        }
    }

    @Override
    public T peek() {
        if(queueSize > 0) return data[queueSize - 1];
        return null;
    }

    /* */
    public void trimToSize() {
        T[] oldData = (T[]) new Object[queueSize];
        System.arraycopy(data, 0, oldData, 0, queueSize);
        data = oldData;
    }
}
