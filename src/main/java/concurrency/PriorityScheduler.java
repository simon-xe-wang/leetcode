package concurrency;

import java.util.Comparator;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
    submit task with priority
    executed immediately after executor ready
 */
public class PriorityScheduler {

    public class TaskFuture implements Future, Runnable {

        private AtomicInteger state = new AtomicInteger();
        private static final int NEW          = 0;
        private static final int COMPLETED   = 1;
        private static final int EXCEPTIONAL  = 3;
        private static final int CANCELLED    = 4;
        private static final int INTERRUPTED  = 6;

        public Task getTask() {
            return task;
        }

        private Task task;
        private int priority;

        public TaskFuture(Task task, int priority) {
            this.task = task;
            this.priority = priority;
        }

        public int getPriority() {
            return priority;
        }

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            return false;
        }

        @Override
        public boolean isCancelled() {
            return false;
        }

        @Override
        public boolean isDone() {
            return false;
        }

        @Override
        public Object get() throws InterruptedException, ExecutionException {
            return null;
        }

        @Override
        public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return null;
        }

        @Override
        public void run() {
            try {
                task.call();
                done();
            } catch (InterruptedException e) {
                state.compareAndSet(NEW, INTERRUPTED);
            } catch (Exception e) {
                state.compareAndSet(NEW, EXCEPTIONAL);
            }
        }

        private void done() {
            System.out.println(task.getId() + " done");
            state.compareAndSet(NEW, COMPLETED);
            resultQueue.offer(this);
        }
    }

    private ExecutorService executorService;
    private Executor scheduler;
    private PriorityBlockingQueue<TaskFuture> taskQueue;
    private LinkedBlockingQueue<TaskFuture> resultQueue;

    public PriorityScheduler() {
        executorService = Executors.newSingleThreadExecutor();
        taskQueue = new PriorityBlockingQueue<TaskFuture>(10, Comparator.comparingInt(TaskFuture::getPriority));
        resultQueue = new LinkedBlockingQueue<TaskFuture>();
        startScheduler();
    }

    private void startScheduler() {
        scheduler = Executors.newSingleThreadExecutor();
        scheduler.execute(() -> {
            while (true) {
                try {
                    TaskFuture task = taskQueue.take();
                    executorService.execute(task);
                } catch (InterruptedException e) { // for shutdown
                    break;
                }
            }
        });
    }

    public TaskFuture submit(Task task, int priority) {
        TaskFuture taskFuture = new TaskFuture(task, priority);
        taskQueue.offer(taskFuture);
        return taskFuture;
    }

    public TaskFuture take() throws InterruptedException {
        return resultQueue.take();
    }

    public TaskFuture poll() {
        return resultQueue.poll();
    }
}

class Task<V> implements Callable<V> {
    public String getId() {
        return id;
    }

    String id;

    public Task(String id) {
        this.id = id;
    }

    @Override
    public V call() throws InterruptedException {
        System.out.println("running task " + id);
        Thread.sleep(10);
        return null;
    }
}
