import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

class MenuCombination {
    List<List<Double>> getCombos(double[] prices, double total) {
        List<List<Double>>  reslist = new ArrayList<>();
        Arrays.sort(prices);
        helper(prices, total, 0, 0, new ArrayList<Double>(), reslist);
        return reslist;
    }

    void helper(double[] prices, double target, int k, double total, List<Double> res,  List<List<Double>> reslist) {
        if (Math.abs(target - total) < 0.000001) {
            reslist.add(new ArrayList<>(res));
            return;
        }
        for(int i = k; i< prices.length; ++i) {
            if(i>0 && Math.abs(prices[i] - prices[i-1]) < 0.00001) continue;
            res.add(prices[i]);
            helper(prices, target, i+1, total+prices[i], res, reslist);
            res.remove(res.size() - 1);
        }
    }
}

public class MenuCombinationTest {
    @Test
    public void test1() {
        MenuCombination sol = new MenuCombination();
        double[] prices = {10.02, 1.11, 3.01, 5.03, 2.22, 3.01, 4.02, 2.00, 5.03};
        List<List<Double>> combos = sol.getCombos(prices, 7.03);
        System.out.println(combos);
        assertEquals(2, combos.size());
    }
}
