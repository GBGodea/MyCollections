import mycollections.MyArrayList;
import mycollections.MyDeque;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        MyArrayList<Integer> array = new MyArrayList();
        MyDeque<Integer> deque = new MyDeque<Integer>();

        long start = System.currentTimeMillis();
        for(int i = 0; i < 100000; i++) {
            array.add(0, i);
        }
        System.out.println("Worst case: End time for MyArrayList add operation: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        for(int i = 0; i < 100000; i++) {
            deque.add(0, i);
        }
        System.out.println("Worst case: End time for MyDeque add operation: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        for(int i = 0; i < 100000; i++) {
            array.set(i, i);
        }
        System.out.println("Worst case: End time for MyArrayList set operation: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        for(int i = 0; i < 100000; i++) {
            deque.set(i, i);
        }
        System.out.println("Worst case: End time for MyDeque set operation: " + (System.currentTimeMillis() - start));
    }
}
