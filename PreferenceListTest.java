import org.junit.Test;
import java.util.*;
import static org.junit.Assert.assertEquals;

class PreferenceList {

    public List<Integer> getPreference(List<List<Integer>> preferences) {
        Map<Integer, Integer> indegree = new HashMap<>();
        List<Integer> res = new ArrayList<>();
        Map<Integer, Set<Integer>> map =  new HashMap<>();
        Set<Integer> set = new HashSet<>();
        for(List<Integer>list: preferences) {
            for(int i =0; i<list.size(); ++i) {
                set.add(list.get(i));
                map.putIfAbsent(list.get(i), new HashSet<>());
                if(i+1 < list.size()) {
                    map.get(list.get(i)).add(list.get(i + 1));
                }
            }
        }

        // Note: we have to get the indegree from the edge map to dedup the indergree
        for(int i:map.keySet()) {
            for(int j: map.get(i))
                indegree.put(j, indegree.getOrDefault(j, 0) + 1);

        }

        Queue<Integer> queue = new LinkedList<>();
        for(int i: set) {
            if(!indegree.containsKey(i)) {
                queue.offer(i);
            }
        }

        while(!queue.isEmpty()) {
            int top = queue.poll();
            res.add(top);
            for (int i : map.get(top)) {
                indegree.put(i, indegree.get(i) - 1);
                if (indegree.get(i) == 0) {
                    queue.offer(i);
                }
            }
        }

        return res;
    }
}

public class PreferenceListTest {
    @Test
    public void test1() {
        PreferenceList sol = new PreferenceList();
        List<List<Integer>> preferences = new ArrayList<>();
        List<Integer> p1 = new ArrayList<Integer>() {{
            add(2);
            add(3);
            add(5);
        }};
        List<Integer> p2 = new ArrayList<Integer>() {{
            add(4);
            add(2);
            add(1);
        }};
        List<Integer> p3 = new ArrayList<Integer>() {{
            add(4);
            add(1);
            add(5);
            add(6);
        }};
        List<Integer> p4 = new ArrayList<Integer>() {{
            add(4);
            add(7);
        }};
        preferences.add(p1);
        preferences.add(p2);
        preferences.add(p3);
        preferences.add(p4);
        List<Integer> res = sol.getPreference(preferences);
        // System.out.println(res);
        assertEquals(7, res.size());
        assertEquals(4, (int) res.get(0));
        assertEquals(2, (int) res.get(1));
        assertEquals(7, (int) res.get(2));
        assertEquals(1, (int) res.get(3));
        assertEquals(3, (int) res.get(4));
        assertEquals(5, (int) res.get(5));
        assertEquals(6, (int) res.get(6));

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        p1 = new ArrayList<Integer>() {{
            add(3);
            add(5);
            add(8);
            add(7);
            add(9);
        }};
        p2 = new ArrayList<Integer>() {{
            add(2);
            add(3);
            add(8);
        }};
        p3 = new ArrayList<Integer>() {{
            add(5);
            add(8);
            add(7);
        }};
        preferences = new ArrayList<>();
        preferences.add(p1);
        preferences.add(p2);
        preferences.add(p3);
        res = sol.getPreference(preferences);
        assertEquals(6, res.size());
    }
}
