package com.sd.test.my_test2;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import static org.junit.Assert.*;

class RunnableTask implements Runnable{

    String name;
    public RunnableTask(String path) {
        name = "Runnable " + path;
    }
    @Override
    public void run() {
        System.out.println("Thread " +Thread.currentThread().getId() +": " + name);
    }
}

class CallableTask implements Callable<String> {

    String name;
    public CallableTask(String path) {
        name = "Callable " + path;
    }

    @Override
    public String call(){
        return name;
    }
}

class FileSystem {
    Map<String, Integer> pathMap;
    Map<String, Runnable> callbackMap;

    public FileSystem() {
        pathMap = new HashMap<>();
        callbackMap = new HashMap<>();
        pathMap.put("", 0);
    }

    public boolean create(String path, int value) {
        if(pathMap.containsKey(path)) return false;
        if(path.lastIndexOf("/") == -1) return false;
        if(!pathMap.containsKey(path.substring(0,path.lastIndexOf("/")))) return false;
        pathMap.put(path, value);
        callbackMap.put(path, new RunnableTask(path));
//        callbackMap.put(path, new CallableTask(path));
        return true;
    }

    public Integer get(String path) {
        return pathMap.getOrDefault(path, null);
    }


    public boolean set(String path, int value) {
        if(!pathMap.containsKey(path))
            return false;
        pathMap.put(path, value);
        int index = path.length();
        List<Thread> threads = new ArrayList<>();
        while (index != -1) {
            Runnable runnable = callbackMap.getOrDefault(path, null);
            if(runnable == null) break;
            index = path.lastIndexOf('/');
            path = path.substring(0, index);
            Thread thread = new Thread(runnable);
            threads.add(thread);
        }
        for(Thread thread: threads) {
            thread.start();
        }
        for(Thread thread: threads) {
            try {
                thread.join();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
        System.out.println("main thread: " + Thread.currentThread().getId());
        return true;
    }


    // Note if calling thread.run() directly instead of using thread.start(), they are still in the same thread
    public boolean set2(String path, int value) {
        if(!pathMap.containsKey(path))
            return false;
        pathMap.put(path, value);
        int index = path.length();
        List<Thread> threads = new ArrayList<>();
        while (index != -1) {
            Runnable runnable = callbackMap.getOrDefault(path, null);
            if(runnable == null) break;
            index = path.lastIndexOf('/');
            path = path.substring(0, index);
            Thread thread = new Thread(runnable);
            threads.add(thread);
        }

        // fake threads
        for(Thread thread: threads) {
            thread.run();
        }
        for(Thread thread: threads) {
            try {
                thread.join();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
        System.out.println("main thread: " + Thread.currentThread().getId());
        return true;
    }

    public boolean set3(String path, int value) {
        if(!pathMap.containsKey(path))
            return false;
        pathMap.put(path, value);
        int index = path.length();
        List<Runnable> tasks = new ArrayList<>();
        while (index != -1) {
            Runnable runnable = callbackMap.getOrDefault(path, null);
            if(runnable == null) break;
            index = path.lastIndexOf('/');
            path = path.substring(0, index);
            tasks.add(runnable);
        }

        // fake threads
        for(Runnable runnable: tasks) {
            runnable.run();
        }

        System.out.println("Same Main thread: " + Thread.currentThread().getId());
        return true;
    }

}

public class FileSystemTest {
    @Test
    public void test1() {

        FileSystem sol = new FileSystem();
        assertTrue(sol.create("/a",1));
        assertEquals(1, (int)sol.get("/a"));
        assertTrue(sol.create("/a/b",2));
        assertEquals(2, (int)sol.get("/a/b"));
        assertTrue(sol.set("/a/b",3));
        assertEquals(3, (int)sol.get("/a/b"));
        assertFalse(sol.create("/c/d",4));
        assertFalse(sol.set("/c/d",4));

        sol = new FileSystem();
        assertTrue(sol.create("/NA",1));
        assertTrue(sol.create("/EU",2));
        assertEquals(1, (int)sol.get("/NA"));
        assertTrue(sol.create("/NA/CA",101));
        assertEquals(101, (int)sol.get("/NA/CA"));
        assertTrue(sol.set("/NA/CA",102));
        assertEquals(102, (int)sol.get("/NA/CA"));
        assertTrue(sol.create("/NA/US",101));
        assertEquals(101, (int)sol.get("/NA/US"));
        assertFalse(sol.create("/NA/CA",101));
        assertFalse(sol.create("/SA/MX",103));
        assertFalse(sol.set("SA/MX",103));

        sol = new FileSystem();
        boolean res = sol.create("/NA/CA",101);
        System.out.println(res);

        res = sol.create("/NA",101);
        System.out.println(res);

        res = sol.create("/NA/CA",103);
        System.out.println(res);

        // set using differnt thread ,  call thread.start()
        System.out.println("\nUse different thread as call back");
        sol.set("/NA/CA",50000);
        Integer result = sol.get("/NA/CA");
        System.out.println(result);


        // set2 using same thread, call thread.run() directly
        System.out.println("\nUse same  main thread as call back");
        sol.set2("/NA/CA",10000);
        result = sol.get("/NA/CA");
        System.out.println(result);


        // set3 using same thread, call thread.run() directly
        System.out.println("\nUse Main thread to run all runnable call back");
        sol.set3("/NA/CA",90000);
        result = sol.get("/NA/CA");
        System.out.println(result);

    }
}
