package my_test;

import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;

class CollatzConjecture2 {
    Map<Integer, Integer> map = new HashMap<>();
    public int findLongestSteps(int n) {
        int maxSteps = 0;
        for(int i = 1; i<= n; ++i) {
            maxSteps = Math.max(maxSteps, doFindLongestSteps(map, n));
        }
        return maxSteps;
    }
    private int doFindLongestSteps(Map<Integer, Integer> map, int n) {
        if(n <= 1) return 1;
        if(map.containsKey(n)) return map.get(n);
        int steps = 0;
        if(n % 2 == 0) steps = doFindLongestSteps(map, n/2) + 1;
        else steps = doFindLongestSteps(map, n*3 + 1) + 1;
        map.put(n, steps);
        System.out.println("n = " + n +", steps = " +steps);
        return steps;
    }
}

class Solution_2 {
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
class CollatzConjecture {
    public int getSteps(int n) {
        Map<Integer, Integer> map = new HashMap<>();
        int steps = 0;
        for(int i = 1; i<=n; ++i)
            steps = Math.max(getSteps(map, i), steps);
        return steps;
    }
    private int getSteps(Map<Integer, Integer> map, int n) {
        if(map.containsKey(n)) return map.get(n);
        if(n == 1)
            return 1;
        int newVal = transform(n);
        int steps = 1 + getSteps(map, newVal);
        map.put(n, steps);
        return steps;
    }

    private int transform(int n) {
        if(n % 2 == 1) {
            return 3 * n + 1;
        } else {
            return n / 2;
        }
    }
}
public class CollatzConjectureTest {

    @Test
    public void testRejectedExecutionException() {
        CollatzConjecture collatzConjecture = new CollatzConjecture();
        int[] vals = {1341, 2244, 543, 324};
        for(int i: vals) {
            System.out.println(collatzConjecture.getSteps(i));
        }
        System.out.println();
        Solution_2 collatzConjecture2 = new Solution_2();
        for(int i: vals) {
            int testVal = collatzConjecture.getSteps(i);
            System.out.println();
            assertEquals(collatzConjecture2.findLongestSteps(i), testVal);
        }
    }

}
