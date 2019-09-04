
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

class Wizard implements Comparable<Wizard>{
    int id;
    int dist;
    Wizard(int id) {
        this.id = id;
        this.dist = Integer.MAX_VALUE;
    }
    @Override
    public int compareTo (Wizard that){
        return this.dist - that.dist;
    }
}

class TenWizards{
    public List<Integer> getShortestPath(List<List<Integer>> wizards, int source, int target) {
        LinkedList<Integer> res = new LinkedList<>();
        int n = wizards.size();
        int[] parents = new int[n];       // used to record the path
        Map<Integer, Wizard> map = new HashMap<>();
        for(int i=0; i<n; ++i) {
            parents[i] = i;
            map.put(i, new Wizard(i));
        }

        map.get(source).dist = 0;
        Queue<Wizard> queue = new LinkedList<>();
        queue.offer(map.get(source));
        while(!queue.isEmpty()) {
            Wizard curr = queue.poll();
            List<Integer> neighbors = wizards.get(curr.id);
            for(int neighbor: neighbors) {
                Wizard next = map.get(neighbor);
                int weight = (int)Math.pow(next.id - curr.id, 2);
                if(curr.dist + weight < next.dist) {
                    parents[next.id] = curr.id;
                    next.dist = curr.dist + weight;
                    queue.offer(next);
                }
//                queue.offer(next);
            }
        }

        int t = target;
        while(t != source) {
            res.addFirst(t);
            t = parents[t];
        }
        res.addFirst(source);

        return res;
    }

}

public class TenWizardsTest {
    @Test
    public void test1() {
        TenWizards sol = new TenWizards();
        int[][] ids = {{1, 5, 9}, {2, 3, 9}, {4}, {}, {}, {9}, {}, {}, {}, {}};
        List<List<Integer>> wizards = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            List<Integer> wizard = new ArrayList<>();
            for (int j = 0; j < ids[i].length; j++) {
                wizard.add(ids[i][j]);
            }
            wizards.add(wizard);
        }
        List<Integer> res = sol.getShortestPath(wizards, 0, 9);
        assertEquals(3, res.size());
        assertEquals(0, (int) res.get(0));
        assertEquals(5, (int) res.get(1));
        assertEquals(9, (int) res.get(2));
    }

    @Test
    public void test2() {
        TenWizards sol = new TenWizards();
        int[][] ids = {{1, 5, 9}, {2, 3, 9}, {4}, {}, {}, {9}, {}, {}, {}, {}};
        List<List<Integer>> wizards = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            List<Integer> wizard = new ArrayList<>();
            for (int j = 0; j < ids[i].length; j++) {
                wizard.add(ids[i][j]);
            }
            wizards.add(wizard);
        }
        List<Integer> res = sol.getShortestPath(wizards, 0, 9);
        assertEquals(3, res.size());
        assertEquals(0, (int) res.get(0));
        assertEquals(5, (int) res.get(1));
        assertEquals(9, (int) res.get(2));
    }
}
