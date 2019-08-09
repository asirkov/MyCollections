package com;

public class Main {
    public static void main(String[] args) {
	    MyArrayList<String> l = new MyArrayList<String>();
	    for(int i = 0; i < 10; i++) {
            l.add("s");
            System.out.print(l.get(i) + " ");
	    }
        System.out.println();

        MyArrayList<String> l2 = new MyArrayList<String>();
        for(int i = 0; i < 3; i++) {
            l2.add("k");
            System.out.print(l2.get(i) + " ");
        }
        System.out.println();

        l.addAll(3, l2);
        for(int i = 0; i < l.size(); i++) {
            l.add("s");
            System.out.print(l.get(i) + " ");
        }
        System.out.println();




    }
}
