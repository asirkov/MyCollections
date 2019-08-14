package com;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
	    /*
        MyStack<String> s = new MyStack<>();

	    System.out.println("Array: ");
	    for(int i = 0; i < 10; i++) {
	        s.push(String.valueOf(i));
        }

	    System.out.println(Arrays.toString(s.toArray()));
        System.out.println(s.pop());
        System.out.println(s.pop());
        System.out.println(s.pop());
        System.out.println(Arrays.toString(s.toArray()));

        s.clear();
        */

        MyLinkedStack<String> ls = new MyLinkedStack<>();

        System.out.println("\nlinked:");
        for(int i = 1; i < 10; i++) {
            ls.push(String.valueOf( i ));
        }

        System.out.println(ls.toString());
        System.out.println(ls.pop());
        System.out.println(ls.pop());
        System.out.println(ls.pop());
        System.out.println(ls.toString());




    }
}
