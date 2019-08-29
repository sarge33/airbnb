package my_test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

class QueueByList {
    private List<Object> head;
    private List<Object> tail;
    int n = 0;
    int h = 0;
    int t = 0;
    public QueueByList(int n) {
        this.n = n;
    }

    public void offer(int num) {
        if(head == null) {
            head = new ArrayList<Object>();
            tail = head;
        } else if(t == n-1) {
            List<Object> tempList = new ArrayList<Object>();
            tail.add(tempList);
            tail = tempList;
            t = 0;
        }
        tail.add(num);
        t++;
    }

    public Integer poll() {
        if(head == null) return null;
        if(head != tail) {
            if(h == n-1) {
                List<Object> tempList = (List<Object>)(head.get(h));
                head = tempList;
                h = 0;
            }
            Integer num = (Integer)(head.get(h));
            h++;
            return num;
        } else {
            if(h == t) {
                head = tail = null;
                h = t = 0;
                return null;
            } else {
                Integer num = (Integer)(head.get(h));
                h++;
                return num;
            }
        }
    }

    public boolean isEmpty() {
        return head == null;
    }
}
class QueueByArray {
    private Object[] head;
    private Object[] tail;
    int n = 0;
    int h = -1;
    int t = -1;

    public QueueByArray(int n) {
        head = null;
        tail = head;
        this.n = n;
    }

    void offer(Integer v) {
        if(head == null) {
            head = new Object[n];
            tail = head;
            h = 0;
            t = 0;
        } else if(t == n-1)  {
            Object[] arr = new Object[n];
            tail[t] = arr;
            tail = arr;
            t = 0;
        }
        tail[t] = v;
        t++;
    }
    Integer poll() {
        if(h == -1) return null;
        Integer value = null;
        if(head != tail) {
            if(h == n-1) {
                Object[] tempHead = (Object[])head[h];
                head[h] = null;
                head = tempHead;
                h = 0;
            }
            value = (Integer)head[h];
            h++;
            return value;
        }
        if (h < t)  {
            value = (Integer)head[h];
            h++;
        }
        if(h == t) {
            head = tail = null;
            h = t = -1;
        }
        return value;
    }

    boolean isEmpty() {
        return head == null && tail == null;
    }
}

public class QueueByFixedArrayTest {

    @Test
    public void testRejectedExecutionException() {
        QueueByArray queue = new QueueByArray(5);
        QueueByList queue2 = new QueueByList(5);
        for(int i = 0; i< 40; ++i) {
            if(i % 4 == 0 || i % 3 == 0) {
                queue.poll();
                queue2.poll();
            }
            queue.offer(i);
            queue2.offer(i);
        }

        while(!queue.isEmpty()) {
            int n1 = queue.poll();
            int n2 = queue2.poll();
            System.out.print(n1 + " ");
            assertEquals(n1, n2);
        }
        System.out.println();
    }

}
