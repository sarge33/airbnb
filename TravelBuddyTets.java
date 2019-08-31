package com.sd.test.my_test2;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

class Buddy implements Comparable<Buddy>{
    String name;
    int similarity;
    Set<String> myWishList;
    public Buddy(String name,
            int similarity,
            Set<String> myWishList) {
        this.name = name;
        this.similarity = similarity;
        this.myWishList = myWishList;
    }

    @Override
    public int compareTo(Buddy b1) {
        return b1.similarity - this.similarity;
    }
}
class TravelBuddy {
    String name;
    Set<String> myWishList;
    Map<String, Set<String>> friendWishList;
    List<Buddy> friends;
    public TravelBuddy(String name,
            Set<String> myWishList,
            Map<String, Set<String>> friendWishList) {
        this.name = name;
        this.myWishList = myWishList;
        this.friendWishList = friendWishList;
        this.friends = new ArrayList<>();
        for(String key: friendWishList.keySet()) {
            if(key.equals(name)) continue;
            Set<String> intersect =  new HashSet<>(friendWishList.get(key));
            intersect.retainAll(myWishList);
            if(intersect.size() >= myWishList.size() / 2) {
                Buddy buddy = new Buddy(key, intersect.size(), friendWishList.get(key));
                friends.add(buddy);
            }
        }
    }

    public List<Buddy> getSortedBuddies() {
        Collections.sort(friends);
        List<Buddy> res = new ArrayList<>(friends);
        return res;
    }

    public List<String> recommendCities(int k) {
        List<String> res = new ArrayList<>();
        List<Buddy> buddies = getSortedBuddies();
        int i = 0;
        while (k > 0 && i < buddies.size()) {
            Set<String> diff=  new HashSet<>(buddies.get(i).myWishList);
            diff.removeAll(myWishList);
            if(diff.size() < k) {
                res.addAll(diff);
                k -= diff.size();
                i++;
            } else {
                for(String name: diff) {
                    res.add(name);
                    k--;
                    if(k == 0) break;
                }
            }
        }
        return res;
    }

}
public class TravelBuddyTets {
    @Test
    public void test1() {
        Set<String> myWishList = new HashSet<>(Arrays.asList(new String[]{"a", "b", "c", "d"}));
        Set<String> wishList1 = new HashSet<>(Arrays.asList(new String[]{"a", "b", "e", "f"}));
        Set<String> wishList2 = new HashSet<>(Arrays.asList(new String[]{"a", "c", "d", "g"}));
        Set<String> wishList3 = new HashSet<>(Arrays.asList(new String[]{"c", "f", "e", "g"}));
        Map<String, Set<String>> friendWishLists = new HashMap<>();
        friendWishLists.put("Buddy1", wishList1);
        friendWishLists.put("Buddy2", wishList2);
        friendWishLists.put("Buddy3", wishList3);
        TravelBuddy sol = new TravelBuddy("me", myWishList, friendWishLists);
        List<String> res = sol.recommendCities(10);
        assertEquals(3, res.size());
        assertEquals("g", res.get(0));
        assertEquals("e", res.get(1));
        assertEquals("f", res.get(2));
    }
}
