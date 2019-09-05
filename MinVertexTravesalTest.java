
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

class MinVertexTravesal {

    public List<Integer> getMin(int[][] edges, int n) {
        Map<Integer, Set<Integer>> graph = new HashMap<>();
        Set<Integer> visited = new HashSet<>();
        Set<Integer> res = new HashSet<>();

        for (int i = 0; i < n; ++i) graph.put(i, new HashSet<>());
        for (int[] edge : edges) graph.get(edge[0]).add(edge[1]);
        for (int i = 0; i < n; ++i) {
            if(!visited.contains(i)) {
                res.add(i);
                helper(graph, visited, res, i, i, new HashSet<Integer>());
            }
        }

        return new ArrayList<>(res);
    }

    void helper(Map<Integer, Set<Integer>> graph, Set<Integer> visited, Set<Integer> res,
                int curr, int start, Set<Integer> currVisited ) {

        currVisited.add(curr);
        visited.add(curr);
        for(int next: graph.get(curr)) {
            if(res.contains(next) && next != start) {
                res.remove(next);
            }
            if(!currVisited.contains(next)) {
                helper(graph, visited, res, next, start, currVisited);
            }
        }
    }

}

public class MinVertexTravesalTest {
    @Test
    public void test1() {
        MinVertexTravesal sol = new MinVertexTravesal();
        ////    1->2->3->1, 2->0->0
        ////      0  1  2  3
        ////    0[1, 0, 0, 0]
        ////    1[0, 0, 1, 0]
        ////    2[1, 0, 0, 1]
        ////    3[0, 1, 0, 0]
        int[][] edges = {{0, 0}, {1, 2}, {2, 0}, {2, 3}, {3, 1}};
        List<Integer> res = sol.getMin(edges, 4);
        System.out.println(res);
        assertEquals(1, res.size());

        ////    0->1->0, 2->3->2->1
        ////      0  1  2  3
        ////    0[0, 1, 0, 0]
        ////    1[1, 0, 0, 0]
        ////    2[0, 1, 0, 1]
        ////    3[0, 0, 1, 0]
        edges = new int[][]{{0, 1}, {1, 0}, {2, 1}, {2, 3}, {3, 2}};
        res = sol.getMin(edges, 4);
        System.out.println(res);
        assertEquals(1, res.size());

        ////    3->2->1->0  0->1 3->1
        ////      0  1  2  3
        ////    0[0, 1, 0, 0]
        ////    1[1, 0, 0, 0]
        ////    2[0, 1, 0, 0]
        ////    3[0, 1, 1, 0]
        edges = new int[][]{{0, 1}, {1, 0}, {2, 1}, {3, 1}, {3, 2}};
        res = sol.getMin(edges, 4);
        System.out.println(res);
        assertEquals(1, res.size());

        ////      0  1  2  3  4  5  6  7  8  9
        ////    0[0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
        ////    1[0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
        ////    2[0, 0, 0 ,0, 0, 0, 0, 0, 0, 1]
        ////    3[0, 0, 0, 1, 0, 1, 0, 1, 0, 0]
        ////    4[0, 0, 0, 0, 0, 0 ,0, 0, 1, 0]
        ////    5[0, 0, 0, 0, 0, 0, 0, 0, 1, 0]
        ////    6[0, 0, 0, 0, 0, 0, 1, 0, 0 ,0]
        ////    7[0, 0, 0, 0, 1, 0, 0, 0, 0, 0]
        ////    8[0, 0, 0, 0, 0, 0, 0, 1, 0, 0]
        ////    9[0, 0, 0, 1, 0, 0, 1, 0, 0, 0]
        edges = new int[][]{{2, 9}, {3, 3}, {3, 5}, {3, 7}, {4, 8}, {5, 8}, {6, 6}, {7, 4}, {8, 7}, {9, 3}, {9, 6}};
        res = sol.getMin(edges, 10);
        System.out.println(res);
        assertEquals(3, res.size());
    }
}
