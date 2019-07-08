class FindMedianByBinarySearch {

    int findMedian(int[] nums, int k, int low, int high) {
        int guess = low + (high - low) / 2;
        int cnt = 0;
        int res = 0;
        boolean start = false;
        for(int i: nums) {
            if(i <= guess) {
                if(start == false) {
                    res = i;
                    start = true;
                }
                cnt++;
                res = Math.max(res, i);
            }
        }
        if(k == cnt) {
            return res;
        }
        else if(k < cnt) { // more large ones
            return findMedian(nums, k, res, high);
        } else {
            return findMedian(nums, k, low, res);
        }
    }
    int findMedian(int[] nums) {
        int len = nums.length;
        int min = Integer.MIN_VALUE;
        int max = Integer.MAX_VALUE;
        if(len %2 == 0) {
            return (findMedian(nums, len/2, min, max) + findMedian(nums, len/2 + 1, min, max)) / 2;
        } else
            return findMedian(nums, len/2 + 1, min, max);
    }
}


public class FindMedianByBinarySearchTest {


    @Test
    public void test2() {

        FindMedianByBinarySearch bs = new FindMedianByBinarySearch();
        int[] arr1 = new int[] {11, 21, 33, 41, 51, 61, 71, 81};
        int v1 = bs.findMedian(arr1);
        System.out.println(v1);

        int[] arr2 = new int[] {21, 11, 71, 41, 61, 51, 33, 81};
        int v2 = bs.findMedian(arr2);
        System.out.println(v2);

    }
}
