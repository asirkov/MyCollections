package com;

public class Main {

    public static void main(String[] args) {

        MyPriorityQueue<String> pq1 = new MyPriorityQueue<>();
        pq1.offer("0", 1);
        pq1.offer("1", 2);
        pq1.offer("2", 12);
        pq1.offer("3", 4);
        pq1.offer("4", 2);

        System.out.println(pq1.toString());

    }
}
