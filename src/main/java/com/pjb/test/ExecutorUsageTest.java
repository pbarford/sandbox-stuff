package com.pjb.test;

import java.util.Calendar;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorUsageTest {
    private static ExecutorService executor = null;
    private static volatile Future<String> taskOneResults = null;
    private static volatile Future taskTwoResults = null;

    public static void main(String[] args) {
        executor = Executors.newFixedThreadPool(2);
        while (true) {
            try {
                checkTasks();
                Thread.sleep(100);
            } catch (Exception e) {
                System.err.println("Caught exception: " + e.getMessage());
            }
        }
    }

    private static void checkTasks() throws Exception {
        if (taskOneResults == null
                || taskOneResults.isDone()
                || taskOneResults.isCancelled()) {
            if(taskOneResults!=null && taskOneResults.isDone())
                System.out.println(taskOneResults.get());
            taskOneResults = executor.submit(new TestOne());
        }

        if (taskTwoResults == null
                || taskTwoResults.isDone()
                || taskTwoResults.isCancelled()) {
            taskTwoResults = executor.submit(new TestTwo());
        }
    }
}

class TestOne implements Callable<String> {
    public String call() {
        System.out.println("Executing task one");
        try {
            Thread.sleep(2000);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return "TEST_ONE_DONE @ " + Calendar.getInstance().getTime().toString();
    }
}

class TestTwo implements Runnable {
    public void run() {
        System.out.println("Executing task two");
        try {
            Thread.sleep(6000);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}