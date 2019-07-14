class CollatzConjecture {
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
