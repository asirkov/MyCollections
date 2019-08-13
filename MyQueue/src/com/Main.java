package com;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        MyQueue<String> q = new MyQueue<>();

        for(int i = 0; i < 10; i++) {
            q.offer(String.valueOf(i));
        }

        System.out.println(Arrays.toString(q.toArray()));

        System.out.println(q.poll() );
        System.out.println(q.poll() );
        System.out.println(q.poll() );

        System.out.println(Arrays.toString(q.toArray()));

        q.offer("-1");
        q.offer("-2");
        q.offer("-3");

        System.out.println(Arrays.toString(q.toArray()));

        q.trimToSize();

        System.out.println(Arrays.toString(q.toArray()));

    }
}
