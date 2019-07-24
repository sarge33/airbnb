// https://leetcode.com/problems/sort-colors

class Solution {
    public void sortColors(int[] nums) {
        // 3 way partitions
        int p0 = 0, p1 = 0, p2 = nums.length-1;   // p1 is current position
        while(p1 <= p2) {
            if(nums[p1] == 0) {        // condition to swap with p0
                int tmp = nums[p0];
                nums[p0] = nums[p1];
                nums[p1] = tmp;
                p0++;
                p1++;                  // Note: this is important, because at this point, 
                                       //       p0 is smaller than p1, so everything is already in order
                                       //       so we need to increase p1
            } else if(nums[p1] == 2) { // condition to swap with p0
                int tmp = nums[p2];
                nums[p2] = nums[p1];
                nums[p1] = tmp;
                p2--;
            } else {                   // everything is in order, increase p1
                p1++;
            }
        }
    }
}
