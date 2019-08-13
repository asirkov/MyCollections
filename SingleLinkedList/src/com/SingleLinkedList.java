/*
 *  Были реализованы только методы для работы методов
 *  reverse(), reverse(SingleLinkedList<> ), getThirdFromTail()
 *
 */

package com;

import java.util.*;

public class SingleLinkedList<T> implements List {

    private static class Node<T> {
        private T data;
        Node<T> next;

        Node(T value, Node<T> nextNode) {
            data = value;
            next = nextNode;
        }
    }

    private class MyIterator implements Iterator {
        Node<T> node;

        MyIterator() { node = SingleLinkedList.this.head; }

        @Override
        public boolean hasNext() {
            boolean result = true;
            if(node == SingleLinkedList.this.tail)
                result = false;
            return result;
        }

        @Override
        public T next() {
            T result = node.data;;
            try {
                node = node.next;
            }
            catch (Exception ex) {
                /* */
            }
            return result;
        }
    }


    private Node<T> head;
    private Node<T> tail;
    private int listSize;

    SingleLinkedList() {
        listSize = 0;
        head = tail = null;
    }

    // Нужно проверить, но вроде работает
    private void addFirst(T t) {
        try {
            if(listSize == 0) {
                head = new Node<T>( t, head);
                tail = head;
            }
            else {
                tail.next = new Node<T>(t, head);
                head = tail.next;
            }
            listSize++;
        }
        catch (Exception ex) {
            System.out.println("Error at addFirst(T )");
        }
    }


    public static <T> SingleLinkedList<T> reverse(SingleLinkedList<T> list) {

        SingleLinkedList<T> result = new SingleLinkedList<T>();
        Iterator it = list.iterator();

        for(int i = 0; i < list.size(); i++) {
            result.addFirst((T) it.next());
        }
        return result;
    }


    public void reverse() {
        T[] array = (T[]) this.toArray();
        try {
            for (int i = 0 ; i < this.listSize; i++)
                this.set(i, array[this.listSize - i - 1]);
        }
        catch (Exception ex) { /* */ System.err.println("Err: " + ex.getMessage()); }
    }

    public T getThirdFromTail() {
        T result = null;
        try {
            Iterator it = this.iterator();
            for(int i = 0; i < this.listSize - 3; i++) {
                it.next();
            }
            result = (T) it.next();
        }
        catch (Exception ex) { /* */ }

        return result;
    }

    @Override
    public Object[] toArray() {
        T[] result = (T[]) new Object[listSize];
        Iterator it = iterator();
        try {
            for(int i = 0; i < listSize; i++)
                result[i] = (T) it.next();
        }
        catch (Exception ex) { /* */ System.err.println("Err: " + ex.getMessage()); }
        return result;
    }

    @Override
    public boolean add(Object o) {
        boolean result = true;
        try {
            if(listSize == 0) {
                head = new Node<T>( (T)o, head);
                tail = head;
            }
            else {
                tail.next = new Node<T>( (T)o, head);
                tail = tail.next;
            }
            listSize++;
        }
        catch (Exception ex) {
            result = false;
            /* */
        }
        return result;
    }

    @Override
    public Object set(int index, Object element) {
        Node<T> node = this.head;
        Object result = null;
        try {
            if(index < 0 || index >= this.listSize)
                throw  new IndexOutOfBoundsException();
            for (int i = 0; i <= index; i++ )
                node = node.next;
            result = node.data;
            node.data = (T)element;
        }
        catch (Exception ex) { /* */ }
        return result;
    }

    @Override
    public int size() {
        return listSize;
    }

    @Override
    public boolean isEmpty() {
        boolean result = false;
        if(listSize == 0)
            result = true;
        return result;
    }

    @Override
    public Iterator iterator() {
        return new MyIterator();
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public void add(int index, Object element) {

    }

    @Override
    public Object remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator listIterator() {
        return null;
    }

    @Override
    public ListIterator listIterator(int index) {
        return null;
    }

    @Override
    public List subList(int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public boolean retainAll(Collection c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection c) {
        return false;
    }

    @Override
    public boolean containsAll(Collection c) {
        return false;
    }

    @Override
    public Object[] toArray(Object[] a) {
        return null;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean addAll(Collection c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public Object get(int index) {
        return null;
    }


}
