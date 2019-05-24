package concurrency;

import org.junit.Test;

import java.util.concurrent.*;

import static org.junit.Assert.*;

public class PrioritySchedulerTest {

    @Test
    public void take() throws Exception {
        PriorityScheduler scheduler = new PriorityScheduler();

        for (int i = 0; i < 10; i++) {
            Task task = new Task(String.valueOf(i));
            scheduler.submit(task, i);
        }

        for (int i = 0; i < 10; i++) {
            PriorityScheduler.TaskFuture future = scheduler.take();
            System.out.println("get result: " + future.getTask().getId());
        }
    }

    @Test(expected = TimeoutException.class)
    public void takeEmpty() throws TimeoutException {
        PriorityScheduler scheduler = new PriorityScheduler();

        ExecutorService monitor = Executors.newSingleThreadExecutor();
        Future future = monitor.submit(() -> {
            try {
                scheduler.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        try {
            future.get(5, TimeUnit.SECONDS);
            fail("failed 1");
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail("failed 2");
        } catch (ExecutionException e) {
            e.printStackTrace();
            fail("failed 3");
        }
    }
}