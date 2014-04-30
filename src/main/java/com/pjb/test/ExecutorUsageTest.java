package com.pjb.test;

import java.util.Calendar;
import java.util.concurrent.*;

public class ExecutorUsageTest {
    private static ExecutorService executor = null;
    private static volatile Future<String> taskOneResults = null;

    public static void main(String[] args) {
        executor = Executors.newFixedThreadPool(2);

        try {
            submitTasks().await();
            System.out.println(Thread.currentThread().getName() + " - " + Calendar.getInstance().getTime().toString() + " --> " + taskOneResults.get());
            executor.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static CountDownLatch submitTasks() throws Exception {
        CountDownLatch latch = new CountDownLatch(2);
        CyclicBarrier barrier = new CyclicBarrier(2, new TasksComplete());
        taskOneResults = executor.submit(new TestOne(latch, barrier));
        executor.submit(new TestTwo(latch, barrier));
        return latch;
    }
}

class TestOne implements Callable<String> {
    private final CountDownLatch latch;
    private final CyclicBarrier barrier;
    public TestOne(CountDownLatch latch,
                   CyclicBarrier barrier) {
        this.latch = latch;
        this.barrier = barrier;
    }

    public String call() {
        System.out.println(Thread.currentThread().getName() + " - Executing task one");
        try {
            for(int i=0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " - " + i);
                Thread.sleep(200);
            }
            System.out.println(Thread.currentThread().getName() + " - waiting all tasks to be completed");
            String timeDone = Calendar.getInstance().getTime().toString();
            barrier.await();
            return "TEST_ONE_DONE @ " + timeDone;
        } catch (Throwable e) {
            e.printStackTrace();
            return "TEST_ONE_DONE EXCEPTION @ " + Calendar.getInstance().getTime().toString();
        }
        finally {
            latch.countDown();
        }
    }
}

class TestTwo implements Runnable {
    private final CountDownLatch latch;
    private final CyclicBarrier barrier;
    public TestTwo(CountDownLatch latch,
                   CyclicBarrier barrier) {
        this.latch = latch;
        this.barrier = barrier;
    }
    public void run() {
        System.out.println(Thread.currentThread().getName() + " - Executing task two");
        try {
            Thread.sleep(6000);
            System.out.println(Thread.currentThread().getName() + " - waiting all tasks to be completed");
            barrier.await();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        finally {
            latch.countDown();
        }
    }
}

class TasksComplete implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " - Tasks are complete");
    }
}