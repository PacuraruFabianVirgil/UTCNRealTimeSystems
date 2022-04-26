import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;

public class App2_Semaphore {
    public static void main(String[] args) {
        Semaphore s1 = new Semaphore(1);
        Semaphore s2 = new Semaphore(1);
        CyclicBarrier bar = new CyclicBarrier(2);
        CountDownLatch latch = new CountDownLatch(2);
        while(true) {
            new ExecutionThread5(bar, latch, s1, null, 4, 2, 4, 1).start();
            new ExecutionThread5(bar, latch, s1, s2, 3, 3, 6, 1).start();
            new ExecutionThread5(bar, latch, s2, null, 5, 2, 5, 1).start();
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

class ExecutionThread5 extends Thread {
    CyclicBarrier bar;
    CountDownLatch latch;
    Semaphore s1,s2;
    int sleep, activity_min, activity_max, permits;

    public ExecutionThread5 (CyclicBarrier bar, CountDownLatch latch, Semaphore s1, Semaphore s2, int sleep,
                             int activity_min, int activity_max, int permits) {
        this.bar =  bar;
        this.latch = latch;
        this.s1 = s1;
        this.s2 = s2;
        this.sleep = sleep;
        this.activity_min = activity_min;
        this.activity_max = activity_max;
        this.permits = permits;
    }

    public void run() {
        try {
            System.out.println(this.getName() + " - STATE 1");
            s1.acquire(this.permits);
            if(s2!=null) s2.acquire(this.permits);
            System.out.println(this.getName() + " - STATE 2");
            int k = (int) Math.round(Math.random() * (activity_max - activity_min) + activity_min);
            for (int i = 0; i < k * 100000; i++) {
                i++;
                i--;
            }
            System.out.println(this.getName() + " - END OF STATE 2");
            System.out.println(this.getName() + " - START TRANSITION 2-3");
            Thread.sleep(sleep * 500L);
            System.out.println(this.getName() + " - END TRANSITION 2-3");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(s2!=null) s2.release(this.permits);
        s1.release(this.permits);
        System.out.println(this.getName() + " - STATE 3");
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