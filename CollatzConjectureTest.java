package com.example.demo;

import java.util.*;

class CollatzConjecture {
    Map<Integer, Integer> map = new HashMap<>();

    private int findSteps(int num) {
        if (num <= 1) return 1;
        if (map.containsKey(num)) return map.get(num);
        if (num % 2 == 0) num = num / 2;
        else num = 3 * num + 1;
        if (map.containsKey(num)) return map.get(num) + 1;
        int t = findSteps(num);
        map.put(num, t);
        return t + 1;
    }

    public int findLongestSteps(int num) {
        if (num < 1) return 0;

        int res = 0;
        for (int i = 1; i <= num; i++) {
            int t = findSteps(i);
            map.put(i, t);
            res = Math.max(res, t);
        }

        return res;
    }
}


public class CollatzConjectureTest {

    public static void main(String[] args) {

        CollatzConjecture sol = new CollatzConjecture();
        System.out.println(1 + " == " + sol.findLongestSteps(1));
        System.out.println(20 + " == " + sol.findLongestSteps(10));
        System.out.println(112 + " == " + sol.findLongestSteps(37));
        System.out.println(119 + " == " + sol.findLongestSteps(101));
        System.out.println(" ======= " );

    }

}
