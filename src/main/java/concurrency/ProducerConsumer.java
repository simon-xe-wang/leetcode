package concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumer {
    BlockingQueue<Integer> numQueue = new LinkedBlockingQueue<>();
    Lock readLock = new ReentrantLock();

    public void run() throws Exception {
        startProducer();
        startConsumers(3);
    }

    class NumConsumer implements Runnable {

        private List<Integer> nums;

        public NumConsumer(List<Integer> nums) {
            this.nums = nums;
        }

        @Override
        public void run() {
            for (int n : nums) {
                System.out.println("consumer " + Thread.currentThread().getId() +
                        " consuming number " + n);
            }
            System.out.println("consumer done " + Thread.currentThread().getId());
        }
    }

    private void startConsumers(int nThreads) {
        ExecutorService consumerPool = Executors.newFixedThreadPool(nThreads);

        CompletableFuture.runAsync( () -> {  // dispatcher
            while (true) {
                List<Integer> nums = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    try {
                        nums.add(numQueue.take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
                CompletableFuture.runAsync(new NumConsumer(nums), consumerPool);
            }
        }, Executors.newSingleThreadExecutor());
    }

    private void startProducer() {
        CompletableFuture.runAsync(
                () -> {
                    int i = 0;
                    while (true) {
                        numQueue.offer(i++);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            System.out.println("interrupted");
                            break;
                        }

                        if (i > 10000) {
                            break;
                        }
                    }
                    System.out.println("producing done");
                },
                Executors.newSingleThreadExecutor()
        );
    }

    public static void main(String[] args) throws Exception {
        ProducerConsumer producerConsumer = new ProducerConsumer();
        producerConsumer.run();
    }
}



