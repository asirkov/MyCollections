package com;

import java.util.*;

public class MyStack<T> extends Vector<T> {

    public MyStack() {
        this(10);
    }

    public MyStack(int capacity) {
        elementData = (T[]) new Object[capacity];
        elementCount = 0;
    }

    private void myEnsureCapacity(int capacity) {
        if (capacity >=  elementData.length) {
            Object[] oldData = elementData;
            int oldCapacity = elementData.length;
            int newCapacity = (oldCapacity * 3) / 2 + 1;
            elementData = new Object[newCapacity];
            System.arraycopy(oldData, 0, elementData, 0, elementCount);
        }
    }

    @Override
    public int size() {
        return elementCount;
    }

    public void push(T t) {
        if(t == null) {
            throw new NullPointerException("Element is null.");
        }
        myEnsureCapacity(elementCount + 1);
        elementData[elementCount] = t;
        elementCount++;
    }

    public T pop() {
        T result = peek();
        elementData[elementCount - 1] = null;
        elementCount--;
        //trimToSize();
        return result;
    }

    public T peek() {
        //if(elementCount == 0)

        if(elementData[elementCount - 1] == null) {
            throw new NoSuchElementException("No such element.");
        }
        return (T) elementData[elementCount - 1];
    }

    @Override
    public synchronized void trimToSize() {
        Object[] oldData = new Object[elementCount];
        System.arraycopy(elementData, 0, oldData, 0, elementCount);
        elementData = oldData;
    }

}
