package com;

import java.util.*;



public class MyLinkedList<T> implements List<T> {
    /**
     *
     */

    private static class Node <T> {
        public Node<T> next;
        public Node<T> prev;
        T data;

        Node(T obj, Node n, Node p) {
            data = obj;
            next = n;
            prev = p;
        }
    }

    /**
     *
     */

    private int listsize;
    private Node<T> head;

    MyLinkedList() {
        head = new Node<T>(null, null, null);
        head.next = head;
        head.prev = head;
        listsize = 0;
    }

    @Override
    public int size() { return listsize; }

    @Override
    public boolean isEmpty() {
        boolean result = false;
        if (listsize == 0)
            result = true;
        return result;
    }

    @Override
    public boolean contains(Object o) {
        boolean result = false;
        //
        return result;
    }

    @Override
    public Iterator iterator() {
        //
        return null;
    }

    @Override
    public boolean add(T e) {
        boolean result;
        try {
            Node<T> newNode = new Node<T>(e, head, head.prev);
            newNode.prev.next = newNode;
            newNode.next.prev = newNode;
            listsize++;
            result = true;
        }
        catch (Exception ex) {
            result = false;
            System.err.println("Error: " + ex.getMessage());
        }
        return result;
    }

    @Override
    public boolean remove(Object o) {
        boolean result = false;
        //if(listsize == 0)
        //    return result;

        Node<T> i = head;
        try {
            do {
                if (i.data == o) {
                    i.prev.next = i.next;
                    i.next.prev = i.prev;

                    i.next = null;
                    i.prev = null;
                    i.data = null;
                    listsize--;
                    result = true;
                    break;
                }
                i = i.next;
            }
            while (i.next != head);
        }
        catch (Exception ex) {
            result = false;
            System.out.println("Error: " + ex.getMessage() + " at boolean remove(Object o)");
        }

        return result;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean result = true;

        try {
            for (T i : c) {
                if (!add(i)) {
                    result = false;
                }
            }
        }
        catch (Exception ex) {
            result = false;
            System.out.println("Error: " + ex.getMessage() + " at boolean addAll(Collection<? extends T> c)");
        }

        return result;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        boolean result = true;
        try {
            for (T e : c) {
                add(index++, e);
            }
        }
        catch (Exception ex) {
            result = false;
            System.out.println("Error: " + ex.getMessage() + " at boolean addAll(int index, Collection<? extends T> c)");
        }

        return result;
    }

    @Override
    public void clear() {
        try {
            while (listsize > 0)
                remove(listsize - 1);
        }
        catch (Exception ex) { System.out.println("Error: " + ex.getMessage() + " at void clear()"); }
    }


    private Node<T> getNode(int index) {
        Node<T> e = head;
        try {
            if(index < 0 || index >= listsize)
                throw new IndexOutOfBoundsException("Index: " + index + " size: " + listsize);

            for(int i = 0; i <= index; i++)
                e = e.next;
        }
        catch (Exception ex) { System.out.println("Error: " + ex.getMessage() + " at T get(int index)"); }

        return e;
    }

    @Override
    public T get(int index) {
        Node<T> e = null;
        T result = null;
        try {
            e = getNode(index);
            result = e.data;
        }
        catch (Exception ex) { System.out.println("Error: " + ex.getMessage() + " at T get(int index)"); }

        return result;
    }


    @Override
    public T set(int index, T element) {
        Node<T> e = head;
        try {
            if(index < 0 || index >= listsize)
                throw new IndexOutOfBoundsException("Index: " + index + " size: " + listsize);
            for(int i = 0; i < index; i++)
                e = e.next;
            T result = e.data;
            e.data = element;
        }
        catch (Exception ex) { System.out.println("Error: " + ex.getMessage() + " at T set(int index, T element)");}
        return e.data;
    }

    @Override
    public void add(int index, T element) {
        Node<T> e = null;

        try {
            e = getNode(index);

            Node<T> newNode= new Node<T>(element, e, e.prev);
            newNode.prev.next = newNode;
            newNode.next.prev = newNode;
            listsize++;
        }
        catch (Exception ex) { System.out.println("Error: " + ex.getMessage() + " at T set(int index, T element)"); }
    }

    @Override
    public T remove(int index) {
        Node<T> e = null;
        T result = null;
        try {
            e = getNode(index);

            e.prev.next = e.next;
            e.next.prev = e.prev;

            result = e.data;
            e.next = null;
            e.prev = null;
            e.data = null;
            listsize--;
        }
        catch (Exception ex) { System.out.println("Error: " + ex.getMessage() + " at T remove(int index)"); }

        return result;
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        Node<T> e = head;
        try {
            // Тут стоит вставить проверку на null, и проверку соответствие типов

            for(; index <= listsize; index++) {
                if(e.data == o)
                    break;
                e = e.next;
            }
        }
        catch (Exception ex) { System.out.println("Error: " + ex.getMessage() + " at indexOf(Object o)"); }

        if((index == listsize && head.prev.data != o) || (index == 0 && head.next.data != o) )
            index = -1;
        return index;
    }

    @Override
    public int lastIndexOf(Object o) {
        int index = 0;
        int index2 = -1;
        Node<T> e = head;
        try {
            // Тут стоит вставить проверку на null, и проверку соответствия типов

            for(; index <= listsize; index++) {
                if(e.data == o)
                    index2 = index;
                e = e.next;
            }
        }
        catch (Exception ex) { System.out.println("Error: " + ex.getMessage() + " at indexOf(Object o)"); }


        if(index2 == listsize && head.prev.data != o)
            index2 = -1;
        return index2;
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
    public List<T> subList(int fromIndex, int toIndex) {
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
    public T[] toArray() {
        return null;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }


}
