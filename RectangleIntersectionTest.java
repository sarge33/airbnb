import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

class RectangleIntersection {
    private boolean intersect(int[][] r1, int[][] r2) {
        int x1 = r1[0][0], y1 = r1[0][1],
            x2 = r1[1][0], y2 = r1[1][1],
            x3 = r2[0][0], y3 = r2[0][1],
            x4 = r2[1][0], y4 = r2[1][1];
        return x1 < x4 && x3 < x2 && y1 < y4 && y3< y2;

    }

    int find(int[] parent, int i) {
        if(parent[i] != i) {
            parent[i] = find(parent, parent[i]);
        }
        return parent[i];
    }

    boolean union(int[]parent, int[] rank, int i, int j) {
        int pi =find(parent, i);
        int pj =find(parent, j);
        if(pi == pj) return false;
        if(rank[pi] >= rank[pj]) {
            parent[pj] = pi;
            rank[pi]++;
        } else {
            parent[pi] = pj;
            rank[pj]++;
        }
        return false;
    }


    public int countIntersection(int[][][] rectangles) {
        int n = rectangles.length;
        int[] parent = new int[n];
        int[] ranks = new int[n];
        for(int i=0; i<n; ++i) parent[i] = i;
        for(int i =0; i< n; ++i) {
            for(int j =i+1; j< n; ++j) {
                if(intersect(rectangles[i], rectangles[j])) {
                    union(parent, ranks, i, j);
                }
            }
        }
        Set<Integer> set = new HashSet<>();
        for(int i =0; i< n; ++i) {
            set.add(find(parent, i));
        }
        return set.size();
    }
}

public class RectangleIntersectionTest {
    @Test
    public void test1() {
        RectangleIntersection sol = new RectangleIntersection();
        int[][][] rectangles = {
                {{-3, -2}, {2, 1}},
                {{10, 8}, {15, 10}},
                {{1, 0}, {7, 4}},
                {{12, 9}, {16, 12}},
                {{-2, -1}, {5, 3}}
        };
        assertEquals(2, sol.countIntersection(rectangles));
    }
}
