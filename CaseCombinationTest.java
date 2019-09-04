mport org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

// [3, 6, 4] => 7
// [4, 10, 3, 1, 5] => 15
class CaseCombination {
    List<String> getCombinations(String s) {
        List<String> reslist = new ArrayList<>();
        helper(s, 0, "", reslist);
        return reslist;
    }

    void helper(String s, int k, String res, List<String> reslist) {
        if(k == s.length()) {
            reslist.add(res);
            return;
        }
        helper(s, k+1, res+ (Character.toLowerCase(s.charAt(k))), reslist);
        helper(s, k+1, res+ (Character.toUpperCase(s.charAt(k))), reslist);
    }
}

public class CaseCombinationTest {
    @Test
    public void test1() {
        CaseCombination sol = new CaseCombination();

        List<String> res = sol.getCombinations("AirBnB");
        assertEquals(64, res.size());
//        for(String s: res) System.out.println(s);
//        assertEquals("airbnb", res.get(0));
//        assertEquals("Airbnb", res.get(1));
//        assertEquals("AIRBNb", res.get(62));
//        assertEquals("AIRBNB", res.get(63));
    }
}
