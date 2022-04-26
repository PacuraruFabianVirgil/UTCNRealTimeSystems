import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class App1_ReentrantLock {
    public static void main(String[] args) {
        Lock l1 = new ReentrantLock();
        Lock l2 = new ReentrantLock();
        CyclicBarrier bar = new CyclicBarrier(3);
        CountDownLatch latch = new CountDownLatch(3);
        while(true) {
            new ExecutionThread2(bar, latch, l1, l2, 4, 2, 4, 4, 6).start();
            new ExecutionThread2(bar, latch, l2, l1, 5, 11, 11, 5, 7).start();
            try {
                bar.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            bar.reset();
            /*try {
                latch.countDown();
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
    }
}

class ExecutionThread2 extends Thread {
    CyclicBarrier bar;
    CountDownLatch latch;
    Lock l1,l2;
    int sleep, activity_min1, activity_max1, activity_min2, activity_max2;

    public ExecutionThread2 (CyclicBarrier bar, CountDownLatch latch, Lock l1, Lock l2, int sleep,
                             int activity_min1, int activity_max1, int activity_min2, int activity_max2) {
        this.bar =  bar;
        this.latch = latch;
        this.l1 = l1;
        this.l2 = l2;
        this.sleep = sleep;
        this.activity_min1 = activity_min1;
        this.activity_max1 = activity_max1;
        this.activity_min2 = activity_min2;
        this.activity_max2 = activity_max2;
    }

    public void run() {
        try {
            System.out.println(this.getName() + " - STATE 1");
            int k = (int) Math.round(Math.random() * (activity_max1 - activity_min1) + activity_min1);
            Thread.sleep(k * 100L);
            System.out.println(this.getName() + " - END OF STATE 1");
            l1.lock();
            System.out.println(this.getName() + " - STATE 2");
            k = (int) Math.round(Math.random() * (activity_max2 - activity_min2) + activity_min2);
            Thread.sleep(k * 100L);
            System.out.println(this.getName() + " - END OF STATE 2");
            l2.lock();
            System.out.println(this.getName() + " - STATE 3");
            System.out.println(this.getName() + " - START TRANSITION 3-4");
            Thread.sleep(sleep * 500L);
            System.out.println(this.getName() + " - END TRANSITION 3-4");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        l2.unlock();
        l1.unlock();
        System.out.println(this.getName() + " - STATE 4");
        try {
            bar.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        /*try {
            latch.countDown();
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }
}
