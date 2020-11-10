import java.util.Random;
import java.util.concurrent.*;

/**
 * 作业一：思考有多少种方式，在 main 函数启动一个新线程，运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 作业要求：
 * 1. 异步调用计算方法
 * 2. 输出异步调用返回值result
 * 3. 退出main线程
 *
 * 解法：
 * 1. Executors.submit()
 * 2. FutureTask
 * 3. CompletableFuture.supplyAsync
 */
public class Homework03 {

    private static int cyclicBarrierSum;

    private static int semaphoreSum;

    private static CyclicBarrier cyclicBarrier;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        // 方法一：Executors.submit()
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        Future<Integer> resultFuture = executorService.submit(new Callable<Integer>() {
//            @Override
//            public Integer call() throws Exception {
//                return sum();
//            }
//        });
//        int result = resultFuture.get();
//        System.out.println("异步计算结果为：" + result + "，使用时间：" + (System.currentTimeMillis() - start) + " ms");
//        executorService.shutdownNow();

        // 方法二：new FutureTask
//        FutureTask<Integer> task = new FutureTask<Integer>(new Callable<Integer>() {
//            @Override
//            public Integer call() throws Exception {
//                return sum();
//            }
//        });
//        new Thread(task).start();
//        int result = task.get();
//        System.out.println("异步计算结果为：" + result + "，使用时间：" + (System.currentTimeMillis() - start) + " ms");
//        // 自动结束

        // 方法三：CompletableFuture.supplyAsync
//        CompletableFuture<Integer> resultFuture = CompletableFuture.supplyAsync(Homework03::sum);
//        int result = resultFuture.get();
//        System.out.println("异步计算结果为：" + result + "，使用时间：" + (System.currentTimeMillis() - start) + " ms");
//        // 自动结束

        // 方法四：join等待子线程执行完成
//        SumTask sumTask = new SumTask();
//        Thread thread = new Thread(sumTask);
//        thread.start();
//        thread.join();
//        int result = sumTask.sum;
//        System.out.println("异步计算结果为：" + result + "，使用时间：" + (System.currentTimeMillis() - start) + " ms");

        // 方法四：countDownLatch等到子线程执行完成
//        CountDownLatch countDownLatch = new CountDownLatch(1);
//        CountDownLatchSumTask sumTask = new CountDownLatchSumTask(countDownLatch);
//        new Thread(sumTask).start();
//        countDownLatch.await();
//        int result = sumTask.sum;
//        System.out.println("异步计算结果为：" + result + "，使用时间：" + (System.currentTimeMillis() - start) + " ms");

        // 方法四：CyclicBarrier等待子线程执行完成
//        cyclicBarrier = new CyclicBarrier(1,new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("异步计算结果为：" + cyclicBarrierSum + "，使用时间：" + (System.currentTimeMillis() - start) + " ms");
//            }
//        });
//        new Thread(new CyclicBarrierSumTask(cyclicBarrier)).start();

//        // 方法四：Semaphore等待子线程执行完成
//        Semaphore semaphore = new Semaphore(1);
//        new SemaphoreThread(semaphore).start();
//        System.out.println("异步计算结果为：" + semaphoreSum + "，使用时间：" + (System.currentTimeMillis() - start) + " ms");

    }
    static class SemaphoreThread extends Thread{
        private Semaphore semaphore;

        public SemaphoreThread(Semaphore semaphore){
            this.semaphore = semaphore;
        }

        @Override
        public void run(){
            try {
                semaphore.acquire();
                semaphoreSum = sum();
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    static class CyclicBarrierSumTask implements Runnable {
        private CyclicBarrier cyclicBarrier;

        public CyclicBarrierSumTask(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            cyclicBarrierSum = sum();
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    static class CountDownLatchSumTask implements Runnable {
        private int sum;
        private CountDownLatch latch;

        public CountDownLatchSumTask(CountDownLatch countDownLatch) {
            this.latch = countDownLatch;
        }

        @Override
        public void run() {
            sum = sum();
            latch.countDown();
        }
    }

    static class SumTask implements Runnable {
        private int sum;

        @Override
        public void run() {
            sum = sum();
        }
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int n) {
        int first = 0;
        int second = 1;
        if (n == 0) {
            return first;
        } else if (n == 1) {
            return second;
        }
        int fibn = 0;
        for (int i = 2; i <= n; i++) {
            fibn = first + second;
            first = second;
            second = fibn;
        }
        return fibn;
// 计算结果和提供的示例代码不一致因为我实现的斐波那契数列算法是从0开始计算的
//        if ( n < 2)
//            return 1;
//        return fibo(n-1) + fibo(n-2);
    }
}
