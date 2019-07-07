package com.example.demo;

import java.util.*;

class Node {
    String val;
    Set<Node> child = new HashSet<>();
    Node(String val) {
        this.val = val;
    }
    void add(Node node) {
        child.add(node);
    }
}
class LCA {
    Map<String, Node> map = new HashMap<>();
    Node buildMap(List<List<String>> input) {
        Node root = null;
        Set<Node> parent = new HashSet<>();
        Set<Node> child = new HashSet<>();
        for(List<String> list: input) {
            map.putIfAbsent(list.get(0), new Node(list.get(0)));
            Node node = map.get(list.get(0));
            parent.add(node);
            if(list.size() > 1) {
                map.putIfAbsent(list.get(1), new Node(list.get(1)));
                Node n1 = map.get(list.get(1));
                child.add(n1);
                node.add(n1);
            }
            if(list.size() > 2) {
                map.putIfAbsent(list.get(2), new Node(list.get(2)));
                Node n2 = map.get(list.get(2));
                node.add(n2);
                child.add(n2);
            }
        }
        parent.removeAll(child);
        System.out.println("parent size " + parent.size());
        return parent.iterator().next();
    }

    public String solve(List<List<String>> input, String p, String q) {
        Node root = buildMap(input);
        System.out.println(" -- root -- " + root.val);
        Node n1 = map.getOrDefault(p, null);
        Node n2 = map.getOrDefault(q, null);
        if(n1 == null || n2 == null) return null;
        Node lcaNode = lca(root, n1, n2);
        System.out.println("root: " + lcaNode.val);
        return lcaNode == null ? "" : lcaNode.val;
    }

    Node lca(Node root, Node n1, Node n2) {
        if(root == null || n1 == root || n2 == root) return root;
        System.out.println("-root: " + root.val);

        Node ancestor = null;

        for(Node child: root.child) {
            Node parent = lca(child, n1, n2);
            if(parent == null) continue;
            if (ancestor == null) {
                System.out.println("--parent: " + parent.val + ", n1: " + n1.val + ", n2: " + n2.val );
                ancestor = parent;
            } else {
                System.out.println("--root: " + root.val);
                return root;
            }
        }
        return ancestor;
    }
}

class Matrix {
    public static int getMinSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[] sum = new int[n];

        for(int j = 0; j<n; ++j) {
            sum[j] = (j > 0 ? sum[j - 1] : sum[j]) + grid[0][j];
            System.out.println(j +": " + sum[j] +", ");
        }
        for(int i: sum) System.out.print(i + " -> ");
        System.out.println();

        for(int i = 1; i<m; ++i)
            for(int j = 0; j<n; ++j) {
                sum[j] = (Math.min(sum[j], j > 0 ? sum[j - 1] : sum[0])) + grid[i][j];
                System.out.println(j +": " + sum[j] +", ");
            }

        return sum[n-1];
    }
}

public class MyTest {

    public static void main(String[] args) {

        List<List<String>> input = new ArrayList<>();
        input.add(new ArrayList<>(Arrays.asList("Earth", "North America", "South America")));
        input.add(new ArrayList<>(Arrays.asList("North America", "United States", "Canada")));
        input.add(new ArrayList<>(Arrays.asList("United States", "New York", "Boston")));
        input.add(new ArrayList<>(Arrays.asList("Canada", "Ontario", "Quebec")));
        input.add(new ArrayList<>(Arrays.asList("South America", "Brazil")));
        LCA s = new LCA();
        System.out.println("==>" + s.solve(input, "Quebec", "New York"));
        System.out.println("==>" + s.solve(input, "Canada", "South America"));
        System.out.println("==>" + s.solve(input, "Canada", "Quebec"));
    }

}

