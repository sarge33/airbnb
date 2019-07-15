import java.util.*;
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
    
    /*
    Note: TO call remove() methode, next() method need to be called first:
       to remove item by iterator, the iterator need to call next to move the iterator first then start remove
       List<String> listOfBooks = new ArrayList<>();  
       listOfBooks.add("Programming Pearls");
       listOfBooks.add("Clean Code");
       listOfBooks.add("Effective Java");
       listOfBooks.add("Code Complete");
        
       Iterator<String> iterator = listOfBooks.iterator();
       while(iterator.hasNext()){
           String book = iterator.next();
           listOfBooks.remove(book);
       }
    
    */
    @Override
    public void remove() {
        if (itemIterator != null)
    }
}

class ListListIterator implements Iterator<Integer> {
    private Iterator<List<Integer>> rowIter;
    private Iterator<Integer> colIter;

    public ListListIterator(List<List<Integer>> vec2d) {
        rowIter = vec2d.iterator();
        colIter = Collections.emptyIterator();
    }

    @Override
    public Integer next() {
        return colIter.next();
    }

    @Override
    public boolean hasNext() {
        while ((colIter == null || !colIter.hasNext()) && rowIter.hasNext())
            colIter = rowIter.next().iterator();
        return colIter != null && colIter.hasNext();
    }

    @Override
    public void remove() {
        while (colIter == null && rowIter.hasNext())
            colIter = rowIter.next().iterator();
        if (colIter != null)
            colIter.remove();
    }
}

public class MyUnitTest {
   @Test
    public void test() {
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
