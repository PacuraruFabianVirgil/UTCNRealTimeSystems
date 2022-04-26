import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class App1_Semaphore {
    public static void main(String[] args) {
        CyclicBarrier bar = new CyclicBarrier(3);
        CountDownLatch latch = new CountDownLatch(3);
        while(true) {
            Semaphore s1 = new Semaphore(1);
            Semaphore s2 = new Semaphore(1);
            new ExecutionThread3(bar, latch, s1, s2, 4, 2, 4, 4, 6, 1).start();
            new ExecutionThread3(bar, latch, s2, s1, 5, 11, 11, 5, 7, 1).start();
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

class ExecutionThread3 extends Thread {
    CyclicBarrier bar;
    CountDownLatch latch;
    Semaphore s1,s2;
    int sleep, activity_min1, activity_max1, activity_min2, activity_max2, permit;

    public ExecutionThread3(CyclicBarrier bar, CountDownLatch latch, Semaphore s1, Semaphore s2, int sleep,
                            int activity_min1, int activity_max1, int activity_min2, int activity_max2, int permit) {
        this.bar =  bar;
        this.latch = latch;
        this.s1 = s1;
        this.s2 = s2;
        this.sleep = sleep;
        this.activity_min1 = activity_min1;
        this.activity_max1 = activity_max1;
        this.activity_min2 = activity_min2;
        this.activity_max2 = activity_max2;
        this.permit = permit;
    }

    public void run() {
        try {
            System.out.println(this.getName() + " - STATE 1");
            int k = (int) Math.round(Math.random() * (activity_max1 - activity_min1) + activity_min1);
            Thread.sleep(k * 100L);
            System.out.println(this.getName() + " - END OF STATE 1");
            this.s1.acquire(this.permit);
            System.out.println(this.getName() + " - STATE 2");
            k = (int) Math.round(Math.random() * (activity_max2 - activity_min2) + activity_min2);
            Thread.sleep(k * 100L);
            System.out.println(this.getName() + " - END OF STATE 2");
            this.s2.acquire(this.permit);
            System.out.println(this.getName() + " - STATE 3");
            System.out.println(this.getName() + " - START TRANSITION 3-4");
            Thread.sleep(sleep * 500L);
            System.out.println(this.getName() + " - END TRANSITION 3-4");
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
        this.s2.release(this.permit);
        this.s1.release(this.permit);
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