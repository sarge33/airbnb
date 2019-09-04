import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

class TrieNode {
    public TrieNode[] child;
    public boolean isLeaf;
    public TrieNode() {
        child = new TrieNode[26];
        isLeaf = false;
    }
}
class Trie {
    TrieNode root;
    public Trie() {
        root = new TrieNode();
    }
    public boolean isWord(String word) {
        TrieNode curr = root;
        for(char ch: word.toCharArray()) {
            if(curr.child[ch-'a'] != null) {
                curr = curr.child[ch-'a'];
            } else {
                return false;
            }
        }
        return curr.isLeaf;
    }
    public void insert(String word) {
        TrieNode curr = root;
        for(char ch: word.toCharArray()) {
            if(curr.child[ch-'a'] == null) {
                curr.child[ch - 'a'] = new TrieNode();
            }
            curr = curr.child[ch - 'a'];
        }
        curr.isLeaf = true;
    }
}

class EditDistance {
    public List<String> getKEditDistance(String[] words, String target, int k) {
        List<String> res = new ArrayList<>();
        if (words == null || words.length == 0 || target == null ||
                target.length() == 0 || k < 0) {
            return res;
        }
        Trie trie = new Trie();
        for (String word : words) {
            trie.insert(word);
        }

        // The edit distance from curr to target
        int[] prev = new int[target.length() + 1];
        for (int i = 0; i < prev.length; i++) {
            prev[i] = i;
        }

        TrieNode root = trie.root;
        find("", target, prev, k, root, res);
        return res;
    }

    private void find(String curr, String target, int[] prev,  int k, TrieNode root,  List<String> res) {
        if(root.isLeaf){
            if(prev[prev.length - 1] <= k) {
                res.add(curr);
            } else {
                return;
            }
        }
        for(int i = 0; i<26; ++i) {
            TrieNode node =  root.child[i];
            if(node == null) continue;
            int[]currDist = new int[target.length() +1];
            currDist[0] = target.length() + 1;
            for(int j = 1; j<prev.length; ++j) {
                if(target.charAt(j-1) == (char)(i+'a')) { // note here is i not j
                    currDist[j] = prev[j-1];
                } else {
                    currDist[j] = 1+ Math.min(Math.min(prev[j-1], prev[j]), currDist[j-1]);
                }
            }
            find(curr + (char)(i +'a'), target, currDist, k, node, res);
        }
    }

}

public class EditDistanceTest {
    @Test
    public void test1() {
//        Trie trie = new Trie();
//        String s1  = "mike";
//        String s2  = "john";
//        String s3  = "johe";
//        String s4  = "m";
//        String s5  = "";
//        trie.insert(s1);
//        trie.insert(s2);
//        System.out.println("trie has " + s1+ ": " + trie.isWord(s1));
//        System.out.println("trie has " + s2+ ": " + trie.isWord(s2));
//        System.out.println("trie has " + s3+ ": " + trie.isWord(s3));
//        System.out.println("trie has " + s4+ ": " + trie.isWord(s4));
//        System.out.println("trie has " + s5+ ": " + trie.isWord(s5));
        EditDistance sol = new EditDistance();
        String[] input = new String[]{"abcd", "abc", "abd", "ad", "c", "cc"};
        String target = "abcd";
        int k = 2;
        List<String> res = sol.getKEditDistance(input, target, k);
        assertEquals(4, res.size());
    }
}
