package com;

//import

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedStack <T> {

    private class Node<T> {
        private T data;
        private Node<T> next;
        private Node<T> prev;

        Node(T d, Node<T> n, Node<T> p) {
            data = d;
            next = n;
            prev = p;
        }

        @Override
        public String toString() { return data.toString(); }
    }


    private Node<T> head;
    private int stackSize;

    public MyLinkedStack() {
        head = new Node<T>(null, null, null);
        head.next = head;
        head.prev = head;
        stackSize = 0;
    }

    public int size() { return stackSize; }

    public void push(T t) {
        Node<T> newNode = new Node<T>(t, head, head.prev);
        newNode.prev.next = newNode;
        newNode.next.prev = newNode;
        stackSize++;
    }

    public T peek() {
        if(stackSize == 0) throw new NoSuchElementException("Stack is empty!");
        return head.prev.data;
    }

    public T pop() {
        try {
            T result = peek();
            Node<T> lastElement = head.prev;
            if (stackSize > 1) {
                lastElement.prev.next = lastElement.next;
                lastElement.next.prev = lastElement.prev;
                lastElement.next = null;
                lastElement.prev = null;
            }

            lastElement.data = null;
            stackSize--;
            return result;
        } catch (NoSuchElementException ex) {
            System.err.println("Error at pop: " + ex.getMessage());
        }

        // pop() throws NoSuchElementException ?
        return null;
    }

    public Iterator iterator() {
        return new Iterator() {
            Node<T> currentNode = head.next;

            @Override
            public boolean hasNext() { return (currentNode != head); }

            @Override
            public Object next() {
                T result = currentNode.data;
                if(hasNext()) currentNode = currentNode.next;
                else throw new NoSuchElementException("Next element is not exists.");

                return result;
            }
        };
    }

    @Override
    public String toString() {
        if(stackSize == 0) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[");
        for(Iterator it = this.iterator(); it.hasNext(); ) {
            sb.append(it.next().toString() + (it.hasNext() ? ", " : "]") );
        }
        return sb.toString();
    }

}
