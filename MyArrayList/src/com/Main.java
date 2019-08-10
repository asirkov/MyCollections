package com;

import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
	    MyArrayList<String> l = new MyArrayList<String>();

        for(int i = 0; i < 6; i++) {
            l.add(String.valueOf(i));
        }

        MyArrayList<String> l2 = new MyArrayList<String>();
        for(int i = 0; i < 3; i++) {
            l2.add("k");
        }
        l2.add("j");

        for(String i : l)
            System.out.print(i + " ");
        System.out.println(" / l: " + l.size());

        for(String i : l2)
            System.out.print(i + " ");
        System.out.println(" / l: " + l2.size());

        l.addAll(3, l2);
        for(String i : l)
            System.out.print(i + " ");
        System.out.println(" / l: " + l.size());

        l.remove("j");
        for(String i : l)
            System.out.print(i + " ");
        System.out.println(" / l: " + l.size());

        l.remove(2);
        for(String i : l)
            System.out.print(i + " ");
        System.out.println(" / l: " + l.size());







    }
}
