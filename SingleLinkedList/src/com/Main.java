package com;

import java.util.Iterator;

public class Main {

    public static void main(String[] args) {
	// write your code here
        SingleLinkedList<String > l = new SingleLinkedList<>();
        SingleLinkedList<String > l2 = new SingleLinkedList<>();

        for(int i = 0; i < 10; i++) {
            l.add(String.valueOf(i));
            l2.add(String.valueOf(i));
        }

        for(var i : l.toArray())
            System.out.print(i + " ");
        System.out.println();

        l2.reverse();
        //l2 = SingleLinkedList.reverse(l);



        Iterator it = l2.iterator();
        for(int i = 0; i < l2.size(); i++)
            System.out.print(it.next() + " ");
        System.out.println();

        System.out.println(l2.getThirdFromTail());



    }
}
