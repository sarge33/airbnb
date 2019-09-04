
import org.junit.Test;

import static org.junit.Assert.assertEquals;

// [3, 6, 4] => 7
// [4, 10, 3, 1, 5] => 15
class MaxBookingRule {
    int getMaxBookingDays(int[] arr) {
        if(arr == null || arr.length == 0) return 0;
        int inclusive = arr[0];
        int exclusive = 0;
        for (int i = 1; i<arr.length; ++i) {
            int tempInclusive = exclusive;
            exclusive = Math.max(inclusive, exclusive);
            inclusive = tempInclusive + arr[i];
        }
        return Math.max(inclusive, exclusive);
    }
}
public class MaxBookingRuleTest {
    @Test
    public void test1() {
        MaxBookingRule sol = new MaxBookingRule();

        int[] test1 = {5, 6, 3, 1};
        assertEquals(8, sol.getMaxBookingDays(test1));
        int[] test2 = {6, 5, 0, 1, 0, 9};
        assertEquals(16, sol.getMaxBookingDays(test2));
        int[] test3 = {5, 1, 1, 5};
        assertEquals(10, sol.getMaxBookingDays(test3));
        int[] test4 = {3, 6, 4};
        assertEquals(7, sol.getMaxBookingDays(test4));
        int[] test5 = {4, 10, 3, 1, 5};
        assertEquals(15, sol.getMaxBookingDays(test5));
    }
}

