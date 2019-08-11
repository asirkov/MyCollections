package com;

import java.util.*;



public class MyLinkedList<T> implements List<T> {

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

    private class MyIterator implements Iterator {
        Node<T> node;

        MyIterator() { node = MyLinkedList.this.head.next; }

        @Override
        public boolean hasNext() {
            boolean result = true;
            if (node.next == MyLinkedList.this.head)
                result = false;
            return result;
        }

        @Override
        public T next() {
            T result = node.data;
            try {
                node = node.next;
            }
            catch (NoSuchElementException ex) { System.err.println("Error: " + ex.getMessage() + " at MyIterator.next()"); }
            return result;
        }
    }

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
            catch (NoSuchElementException ex) { System.err.println("Error: " + ex.getMessage() + " at MyListIterator.next()"); }
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


    /**
     *
     */

    private int listSize;
    private Node<T> head;

    MyLinkedList() {
        head = new Node<T>(null, null, null);
        head.next = head;
        head.prev = head;
        listSize = 0;
    }

    @Override
    public int size() { return listSize; }

    @Override
    public boolean isEmpty() {
        boolean result = false;
        if (listSize == 0)
            result = true;
        return result;
    }

    @Override
    public boolean contains(Object o) {
        boolean result = true;
        if(indexOf(o) == -1)
            result = false;
        return result;
    }

    @Override
    public Iterator iterator() {
        Iterator it = new MyIterator();
        return it;
    }

    @Override
    public boolean add(T e) {
        boolean result;
        try {
            Node<T> newNode = new Node<T>(e, head, head.prev);
            newNode.prev.next = newNode;
            newNode.next.prev = newNode;
            listSize++;
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
        //if(listSize == 0)
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
                    listSize--;
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
            while (listSize > 0)
                remove(listSize - 1);
        }
        catch (Exception ex) { System.err.println("Error: " + ex.getMessage() + " at void clear()"); }
    }


    private Node<T> getNode(int index) {
        Node<T> e = head;
        try {
            if(index < 0 || index >= listSize)
                throw new IndexOutOfBoundsException("Index: " + index + " size: " + listSize);

            for(int i = 0; i <= index; i++)
                e = e.next;
        }
        catch (Exception ex) { System.err.println("Error: " + ex.getMessage() + " at T get(int index)"); }

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
        catch (Exception ex) { System.err.println("Error: " + ex.getMessage() + " at T get(int index)"); }

        return result;
    }


    @Override
    public T set(int index, T element) {
        Node<T> e = head;
        try {
            if(index < 0 || index >= listSize)
                throw new IndexOutOfBoundsException("Index: " + index + " size: " + listSize);
            for(int i = 0; i < index; i++)
                e = e.next;
            T result = e.data;
            e.data = element;
        }
        catch (Exception ex) { System.err.println("Error: " + ex.getMessage() + " at T set(int index, T element)"); }
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
            listSize++;
        }
        catch (Exception ex) { System.err.println("Error: " + ex.getMessage() + " at T set(int index, T element)"); }
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
            listSize--;
        }
        catch (Exception ex) { System.err.println("Error: " + ex.getMessage() + " at T remove(int index)"); }

        return result;
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        Node<T> e = head;
        try {
            // Тут стоит вставить проверку на null, и проверку соответствие типов

            for(; index <= listSize; index++) {
                if(e.data == o)
                    break;
                e = e.next;
            }
        }
        catch (Exception ex) { System.err.println("Error: " + ex.getMessage() + " at indexOf(Object o)"); }

        if((index == listSize && head.prev.data != o) || (index == 0 && head.next.data != o) )
            index = -1;
        return index;
    }

    @Override
    public int lastIndexOf(Object o) {
        int index = 0;
        int index2 = -1;
        Node<T> e = head;
        try {
            // Тут стоит вставить проверку на null и проверку соответствия типов

            for(; index <= listSize; index++) {
                if(e.data == o)
                    index2 = index;
                e = e.next;
            }
        }
        catch (Exception ex) { System.err.println("Error: " + ex.getMessage() + " at indexOf(Object o)"); }


        if(index2 == listSize && head.prev.data != o)
            index2 = -1;
        return index2;
    }

    @Override
    public ListIterator<T> listIterator() {
        MyListIterator it = new MyListIterator();
        return it;
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        MyListIterator it = new MyListIterator();
        try {
            if(index < 0 || index >= MyLinkedList.this.listSize)
                throw new IndexOutOfBoundsException("Index: " + index + " listSize: " + listSize);
            while(it.nextIndex() <= index)
                it.next();
        }
        catch (Exception ex) { System.err.println("Error: " + ex.getMessage() + " at listIterator(int index);"); }
        return it;
    }

    
    // можно было сделать и в обратном порядке (от listSize до 0) 
    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        MyLinkedList<T> l = new MyLinkedList<T>();
        
        try {
            if(fromIndex < 0 || toIndex < 0 || fromIndex > listSize || toIndex > listSize || fromIndex > toIndex)
                throw new IndexOutOfBoundsException("Index: " + fromIndex + ", " + toIndex + " listSize: " + listSize);
            for(int i = fromIndex; i <= toIndex; i++)
                l.add( get(i) );
        }
        catch (Exception ex) {System.out.println("Error: " + ex.getMessage() + " at subList(int fromIndex, int toIndex);"); }
        return l;
    }

    @Override
    public boolean retainAll(Collection c) {
        boolean result = true;
        try {
            for (var i : this) {
                if ( !c.contains(i) )
                    this.remove(i);
            }
        }
        catch(Exception ex) {
            result = false;
            System.err.println("Error: " + ex.getMessage() + " at retainAll(Collection c);");
        }

        return result;
    }

    @Override
    public boolean removeAll(Collection c) {
        boolean result = true;
        try {
            for (var i : c) {
                if (indexOf(i) == -1) {
                    result = false;
                    break;
                }
                else {
                    while(indexOf(i) != -1) {
                        remove(i);
                    }
                }
            }
        }
        catch(Exception ex) {
            result = false;
            System.err.println("Error: " + ex.getMessage() + " at removeAll(Collection c);");
        }

        return result;
    }

    @Override
    public boolean containsAll(Collection c) {
        boolean result = true;
        try {
            for (var i : c) {
                if (indexOf(i) == -1) {
                    result = false;
                    break;
                }
            }
        }
        catch(Exception ex) {
            result = false;
            System.err.println("Error: " + ex.getMessage() + " at containsAll(Collection c);");
        }

        return result;
    }


    // не знаю как перевести список в массив
    //

    @Override
    public T[] toArray() {
        /*T[] arr = (T[])new Object[listSize];
        // T[] arr = new T[listSize];

        ListIterator<T> it = MyLinkedList.this.listIterator();

        for (int i = 0; i < listSize; i++ )
            if(it.hasNext())
                arr[i] = it.next();

        return arr;
         */
        return null;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }


}
