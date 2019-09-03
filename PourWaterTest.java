
import org.junit.Test;

class WaterLand {

    public int[] pourWater(int[] heights, int V, int K) {
        for(int i = 0; i<V; ++i) {
            int j = K;
            while(j>0 && heights[j-1] <= heights[j]) j--;
            while(j<heights.length-1 && heights[j+1] <= heights[j]) j++;
            while(j>K && heights[j-1] <= heights[j]) j--;
            heights[j] += 1;
        }
        return heights;


    }
}

public class PourWaterTest {
    @Test
    public void test1() {
        WaterLand sol = new WaterLand();
        int[] waterLand = new int[]{5, 4, 2, 1, 2, 3, 2, 1, 0, 1, 2, 4};
        sol.pourWater(waterLand, 5, 1);
        sol.pourWater(waterLand, 5, 5);
        sol.pourWater(waterLand, 5, 10);
        sol.pourWater(waterLand, 5, 20);
        sol.pourWater(waterLand, 5, 30);
        sol.pourWater(waterLand, 5, 50);
        sol.pourWater(waterLand, 5, 100);

        waterLand = new int[]{5, 4, 2, 1, 3, 2, 2, 1, 0, 1, 4, 3};
        sol.pourWater(waterLand, 4, 1);
        sol.pourWater(waterLand, 4, 5);
        sol.pourWater(waterLand, 4, 10);
        sol.pourWater(waterLand, 4, 20);
        sol.pourWater(waterLand, 4, 30);
        sol.pourWater(waterLand, 4, 50);
        sol.pourWater(waterLand, 4, 100);
    }

}
