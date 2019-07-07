package com.example.demo;

import java.util.*;

class QueueWithFixedArray {
    private int fixedSize;

    private int count;
    private int head;
    private int tail;
    private List<Object> headList;
    private List<Object> tailList;

    public QueueWithFixedArray(int fixedSize) {
        this.fixedSize = fixedSize;
        this.count = 0;
        this.head = 0;
        this.tail = 0;
        this.headList = new ArrayList<>();
        this.tailList = this.headList;
    }

    public void offer(int num) {
        if(tail == fixedSize - 1) {
            List<Object> list = new ArrayList<Object>();
            list.add(num);
            tailList.add(list);
            tailList = list;
            tail = 0;
        } else {
            tailList.add(num);
        }
        tail++;
        count++;
    }

    public Integer poll() {
        if (count == 0) {
            return null;
        }

        if(head == fixedSize - 1) {
            List<Object> temp = (List<Object> )headList.get(fixedSize);
            headList.clear();
            headList = temp;
            head = 0;
        }

        int num = (int) headList.get(head);
        head++;
        count--;
        return num;
    }

    public int size() {
        return count;
    }
}


public class QueueWithFixedArrayTest {

    public static void main(String[] args) {

        QueueWithFixedArray queue = new QueueWithFixedArray(5);
        queue.offer(1);
        queue.offer(2);
        int res = queue.poll();
        System.out.println(res + " ==  1" );
        queue.offer(3);
        queue.offer(4);
        queue.offer(5);
        queue.offer(6);
        queue.offer(7);
        queue.offer(8);
        queue.offer(9);
        res = queue.poll();
        System.out.println(res + " ==  2" );
        res = queue.poll();
        System.out.println(res + " ==  3" );

    }

}
