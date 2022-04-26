import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class App2_ReentrantLock {
    public static void main(String[] args) {
        Lock l1 = new ReentrantLock();
        Lock l2 = new ReentrantLock();
        CyclicBarrier bar = new CyclicBarrier(3);
        CountDownLatch latch = new CountDownLatch(3);
        while(true) {
            new ExecutionThread4(bar, latch, l1, null, 4, 2, 4).start();
            new ExecutionThread4(bar, latch, l1, l2, 3, 3, 6).start();
            new ExecutionThread4(bar, latch, l2, null, 5, 2, 5).start();
            try {
                bar.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            bar.reset();
            /*try {
                latch.contDown();
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
    }
}

class ExecutionThread4 extends Thread {
    CyclicBarrier bar;
    CountDownLatch latch;
    Lock l1,l2;
    int sleep, activity_min, activity_max;

    public ExecutionThread4 (CyclicBarrier bar, CountDownLatch latch, Lock l1, Lock l2, int sleep,
                             int activity_min, int activity_max) {
        this.bar =  bar;
        this.latch = latch;
        this.l1 = l1;
        this.l2 = l2;
        this.sleep = sleep;
        this.activity_min = activity_min;
        this.activity_max = activity_max;
    }

    public void run() {
        try {
            System.out.println(this.getName() + " - STATE 1");
            l1.lock();
            if(l2!=null) l2.lock();
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
        if(l2!=null) l2.unlock();
        l1.unlock();
        System.out.println(this.getName() + " - STATE 3");
        try {
            bar.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        /*try {
            latch.contDown();
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }
}