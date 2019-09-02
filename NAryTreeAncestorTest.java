import org.junit.Test;

import java.util.*;

class NAryTree {
    String root;
    Map<String, Set<String>> map = new HashMap<>();
    Map<String, String> parentMap = new HashMap<>();

    public NAryTree(List<List<String>> lists) {
        init(lists);
    }

    public String getParent(String name) {
        return parentMap.getOrDefault(name, "-");
    }
    public Set<String> getChild(String name) {
        return map.getOrDefault(name, new HashSet<>());
    }


    public void init(List<List<String>> lists) {
        for (List<String> list : lists) {
            String name = list.get(0);
            map.putIfAbsent(name, new HashSet<>());
//            map.get(name).addAll(list.subList(1, list.size()));
            for (int i = 1; i < list.size(); ++i) {
                map.get(name).add(list.get(i));
                parentMap.put(list.get(i), name);
            }
        }
    }

    public String findRoot() {
        for (String s : map.keySet()) {
            if (!parentMap.containsKey(s)) {
                return s;
            }
        }
        return null;
    }

    public String getRoot() {
        if (root == null) {
            root = findRoot();
        }
        return root;
    }

    public String lowestCommonAncestor(String a, String b) {
        String root = getRoot();
        return lowestCommonAncestor(root, a, b);
    }

    public String lowestCommonAncestorByParent(String a, String b) {
        Set<String> parent = new HashSet<>();
        while(parentMap.containsKey(a)) {
            parent.add(parentMap.get(a));
            a = parentMap.get(a);
        }
        while(parentMap.containsKey(b)) {
            if(parent.contains(b)) return b;
            b = parentMap.get(b);
        }
        return null;
    }
    public String lowestCommonAncestor(String root, String a, String b) {
        if (root == null || root.equals(a) || root.equals(b)) return root;
        Set<String> childList = map.get(root);
        boolean foundA = false;
        boolean foundB = false;

        for (String child : map.getOrDefault(root, new HashSet<>())) {
            String p = lowestCommonAncestor(child, a, b);
            // in case one of the child is already the lowest common ancestor
            if(p != null && !p.equals(a) && !p.equals(b))return p;
            if (p != null && p.equals(a))  foundA = true;
            if (p != null && p.equals(b))  foundB = true;
            // current root is the lowest common ancestor
            if (foundA && foundB) return root;
        }
        if (foundA) return a;
        if (foundB) return b;
        return null;
    }
}


public class NAryTreeAncestorTest {
    @Test
    public void test1() {

        List<List<String>> input = Arrays.asList(
                Arrays.asList("Earth", "North America","South America"),
                Arrays.asList("North America", "Mexico", "United States", "Canada"),
                Arrays.asList("South America", "Argentina", "Brazil", "Chile"),
                Arrays.asList("Mexico", "Oaxaca", "Puebla"),
                Arrays.asList("United States", "California", "Wyoming", "New York"),
                Arrays.asList("Canada", "Ontario", "Quebec", "Saskatchewan")
        );

        NAryTree tree = new NAryTree(input);

        System.out.println("tree root: " + tree.getRoot());
        String a = "California";
        String b = "Mexico";

        String ancestor = tree.lowestCommonAncestor(a, b);
        String ancestor2 = tree.lowestCommonAncestorByParent(a, b);

        System.out.println("lowestCommonAncestor(" +  a + " " + b + ") ancestor ==> " + ancestor);
        System.out.println("lowestCommonAncestorByParent(" +  a + " " + b + ") ancestor ==> " + ancestor2);

//        Set<String> child1 = tree.getChild("California");
//        System.out.println("Mexico parent: " + tree.getParent("California"));
//        System.out.println("California child: ");
//        for(String s: child1) System.out.print(s +" ");System.out.println();
//
//        Set<String> child2 = tree.getChild("Mexico");
//        System.out.println("Mexico parent: " + tree.getParent("Mexico"));
//        System.out.println("Mexico child: ");
//        for(String s: child2) System.out.print(s +" ");System.out.println();

    }
}
