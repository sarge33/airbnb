import java.util.*;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

class ListofListIterator implements Iterator<Integer> {
    Iterator<List<Integer>> listIterator;
    Iterator<Integer> itemIterator;

    public ListofListIterator(List<List<Integer>> list) {
        listIterator =  list.listIterator();
        itemIterator = Collections.emptyIterator();
    }
    @Override
    public boolean hasNext() {
        while((itemIterator == null || !itemIterator.hasNext()) && listIterator.hasNext()) {
            itemIterator = listIterator.next().iterator();
        }
        return itemIterator != null && itemIterator.hasNext();
    }

    @Override
    public Integer next() {
        return itemIterator.next();
    }
    @Override
    public void remove() {
//        if(itemIterator == null) System.out.println("itemIterator = null");
//        while (itemIterator == null && listIterator.hasNext())
//            itemIterator = listIterator.next().iterator();
        if (itemIterator != null)
            itemIterator.remove();
    }
}

public class ListofListIteratorTest {
    @Test
    public void test1() {
        List<List<Integer>> test = new ArrayList<>();
        test.add(new ArrayList<Integer>() {{
            add(1);
            add(2);
        }});
        test.add(new ArrayList<Integer>() {{
            add(3);
        }});
        test.add(new ArrayList<Integer>() {{
            add(4);
            add(5);
            add(6);
        }});

        System.out.println(" +++ 0 +++ ");
        ListofListIterator sol = new ListofListIterator(test);
        while (sol.hasNext()) {
            System.out.println(sol.next() + " ");
        }
        sol = new ListofListIterator(test);
        System.out.println(" +++ 1 +++ ");
        while (sol.hasNext()) {
            System.out.println("  -1- " + sol.next());
            sol.remove();
        }

        System.out.println(" +++ 2 +++ ");
        sol = new ListofListIterator(test);
        while (sol.hasNext()) {
            System.out.println(sol.next() + "  -2- ");
        }
    }
}
