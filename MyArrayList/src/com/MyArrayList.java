package com;

import java.util.*;

public class MyArrayList<T> implements List<T> {



    private class MyIterator implements Iterator {
        private int index;

        MyIterator() { index = 0; }

        @Override
        public boolean hasNext() {
            boolean result = true;
            if (MyArrayList.this.data[index + 1] == null)
                result = false;
            return result;
        }

        @Override
        public T next() {
            T result = null;
            try {
                if(index >= MyArrayList.this.listSize || index < 0)
                    throw new NoSuchElementException();
                result = data[index++];
            }
            catch (NoSuchElementException ex) { System.out.println("Error at MyIterator.next()\nError message: " + ex.getMessage()); }
            return result;
        }
    }

    /*

    private class MyListIterator implements ListIterator {
        private Node<T> node;

        MyListIterator() { node = MyLinkedList.this.head.next; }

        @Override
        public boolean hasNext() {
            boolean result = false;
            if (node.next != head)
                result = true;
            return result;
        }

        @Override
        public T next() {
            T result = node.data;
            try {
                node = node.next;
            }
            catch (NoSuchElementException ex) { System.out.println("Error: " + ex.getMessage() + " at MyListIterator.next()"); }
            return result;
        }

        @Override
        public boolean hasPrevious() {
            boolean result = false;
            if (node.prev != head)
                result = true;
            return result;
        }

        @Override
        public T previous() {
            T result = node.data;
            node = node.prev;
            return result;
        }


        public int currentIndex() {
            int result = MyLinkedList.this.indexOf(node.data);
            return result;
        }

        @Override
        public int nextIndex() {
            int result = currentIndex() + 1;
            return result;
        }

        @Override
        public int previousIndex() {
            int result = currentIndex() - 1;
            return result;
        }

        @Override
        public void remove() {
            //T remove =
            MyLinkedList.this.remove(MyLinkedList.this.listSize);
        }

        @Override
        public void set(Object o) {
            MyLinkedList.this.set(MyLinkedList.this.indexOf(node.data), (T)o);
        }

        @Override
        public void add(Object o) {
            MyLinkedList.this.add(MyLinkedList.this.indexOf(node.data), (T)o);
        }
    };

     */
    /**
     *
     */

    private T[] data;
    private int listSize;

    MyArrayList() { data = (T[]) new Object[10]; listSize = 0; }
    MyArrayList(int capacity) { data = (T[]) new Object[capacity]; listSize = 0; }

    @Override
    public int size() { return listSize; }

    @Override
    public boolean isEmpty() {
        boolean result = false;
        if(listSize == 0)
            result = true;
        return result;
    }

    @Override
    public boolean contains(Object o) {
        boolean result = false;
        try {
            for (T i : data)
                if( i == o) {
                    result = true;
                    break;
                }
        }
        catch (Exception ex) { System.err.println("Error at MyArrayList.contains(Object)\nError message: " + ex.getMessage()); }
        return result;
    }

    @Override
    public Iterator<T> iterator() {
        return new MyIterator();
    }

    @Override
    public Object[] toArray() {
        return data;
    }

    //
    //
    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    private void /*boolean*/ ensureCapacity(int capacity) {
        //boolean result = false;
        try {
            if (capacity >=  data.length) {
                T[] oldData = data;
                int oldCapacity = data.length;
                int newCapacity = (oldCapacity * 3) / 2 + 1;
                data = (T[]) new Object[newCapacity];
                System.arraycopy(oldData, 0, data, 0, listSize);
            }
        }
        catch (Exception ex) { System.err.println("Error at MyArrayList.ensureCapacity(int)\nError message: " + ex.getMessage()); }
    }

    @Override
    public boolean add(T t) {
        boolean result;
        try {
            ensureCapacity(listSize + 1);
            data[listSize++] = t;
            result = true;
        }
        catch (Exception ex) {
            result = false;
            System.err.println("Error at MyArrayList.add(T)\nError message: " + ex.getMessage());
        }
        return result;
    }

    // не уверен, проверить
    @Override
    public boolean remove(Object o) {
        boolean result;
        int index;
        try {
            index = indexOf(o); // -
            System.arraycopy(data, index, data, index - 1, listSize - 1);
            listSize--;
            result = true;
        }
        catch (Exception ex) {
            result = false;
            System.err.println("Error at MyArrayList.remove(Object)\nError message: " + ex.getMessage());
        }
        return result;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return false;
    }

    // возникает бесконечный цикл, нужно исправить
    //
    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        boolean result = true;
        int collSize = c.size();
        T[] collData;
        try{
            if (index < 0 || index > listSize)
                throw new IndexOutOfBoundsException("Index: " + index + " lisSize: " + listSize);
            collData = (T[])c.toArray();
            ensureCapacity(listSize + collSize);
            System.arraycopy(data, index, data, index + collSize, listSize - index);
            System.arraycopy(collData, 0, data, index, collSize);
            listSize += data.length;
        }
        catch (Exception ex) {
            result = false;
            System.err.println("Error at MyArrayList.addAll(index, Collection<>)\nError message: " + ex.getMessage());
        }

        return result;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public T get(int index) {
        T result = null;
        try {
            if (index < 0 || index > listSize)
                throw new IndexOutOfBoundsException("Index: " + index + " lisSize: " + listSize);
            result = data[index];
        }
        catch (Exception ex) { System.err.println("Error at MyArrayList.get(int)\nError message: " + ex.getMessage()); }
        return result;
    }

    @Override
    public T set(int index, T element) {
        T result = null;
        try {
            if (index < 0 || index > listSize)
                throw new IndexOutOfBoundsException("Index: " + index + " lisSize: " + listSize);
            result = data[index];
            data[index] = element;
        }
        catch (Exception ex) { System.err.println("Error at MyArrayList.set(int, T)\nError message: " + ex.getMessage()); }
        return result;
    }

    @Override
    public void add(int index, T element) {
        try {
            if(index < 0 || index > listSize)
                throw new IndexOutOfBoundsException("Index: " + index + " lisSize: " + listSize);
            ensureCapacity(listSize + 1);
            System.arraycopy(data, index, data,index + 1, listSize - 1);
            data[index] = element;
            listSize++;
        }
        catch (Exception ex) { System.err.println("Error at MyArrayList.add(int, T)\nError message: " + ex.getMessage()); }
    }

    @Override
    public T remove(int index) {
        T result = null;

        try {
            if(index < 0 || index > listSize)
                throw new IndexOutOfBoundsException("Index: " + index + " listSize: " + listSize);
            result = data[index];
            int numMoved = listSize - index - 1;
            System.arraycopy(data, index + 1, data, index, numMoved);
            data[--listSize] = null;
        }
        catch (Exception ex) { System.err.println("Error at MyArrayList.remove(int)\nError message: " + ex.getMessage()); }
        return result;
    }

    @Override
    public int indexOf(Object o) {
        int result = -1;
        try {
            for(int i = 0; i < listSize; i++)
                if(data[i] == o) {
                    result = i;
                    break;
                }
        }
        catch (Exception ex) { System.err.println("Error at MyArrayList.indexOf(Object)\nError message: " + ex.getMessage()); }

        return result;
    }

    @Override
    public int lastIndexOf(Object o) {
        int result = -1;
        try {
            for(int i = 0; i < listSize; i++)
                if(data[i] == o)
                    result = i;
        }
        catch (Exception ex) { System.err.println("Error at MyArrayList.lastIndexOf(Object)\nError message: " + ex.getMessage()); }

        return result;
    }

    @Override
    public ListIterator<T> listIterator() {
        return null;
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return null;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return null;
    }
}
