package com;


import java.util.ListIterator;

public class Main {

    public static void main(String[] args) {
	    // write your code here
        MyLinkedList<String> l = new MyLinkedList<String>();
        l.add("0");
        l.add("1");
        l.add("2");
        l.add("3");
        l.add("4");
        l.add("5");
        l.add("6");
        l.add("7");
        l.add("8");
        l.add("9");

        l.add(3, "-1");

        for(int i = 0; i < l.size(); i++)
            System.out.println(i + ": " + l.get(i));
        l.set(5, "2");
        l.set(6, "2");


        System.out.println("\nind of \"2\" : " + l.indexOf("2"));
        System.out.println("\nlind of \"2\": " + l.lastIndexOf("2"));

        l.remove(5);

        System.out.println("\nind of -3: " + l.lastIndexOf(-3));

        System.out.println("\nit: ");
        ListIterator it = l.listIterator();

        while ( it.hasNext() )
            System.out.println(it.next());

    }
}
