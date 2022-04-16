import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class L4App2 {
    public static void main(String[] args) {
        Lock l1 = new ReentrantLock();
        Lock l2 = new ReentrantLock();
        new ExecutionThread2(l1, l2, 4, 2, 4,4,6).start();
        new ExecutionThread2(l2, l1, 5, 3, 5,5,7).start();
    }
}

class ExecutionThread2 extends Thread {
    Lock l1, l2;
    int sleep,  activity_min_1, activity_max_1, activity_min_2, activity_max_2;
    public ExecutionThread2(Lock l1, Lock l2, int sleep, int activity_min_1, int activity_max_1,
                            int activity_min_2, int activity_max_2) {
        this.l1 = l1;
        this.l2 = l2;
        this.sleep = sleep;
        this.activity_min_1 = activity_min_1;
        this.activity_max_1 = activity_max_1;
        this.activity_min_2 = activity_min_2;
        this.activity_max_2 = activity_max_2;
    }
    public void run() {
        System.out.println(this.getName() + " - STATE 1");
        int k = (int) Math.round(Math.random() * (activity_max_1 - activity_min_1) + activity_min_1);
        for (int i = 0; i < k * 100000; i++) {
            i++;
            i--;
        }
        try{
        if(this.l1.tryLock())
            try {
                System.out.println(this.getName() + " - STATE 2");
                k = (int) Math.round(Math.random() * (activity_max_2 - activity_min_2) + activity_min_2);
                for (int i = 0; i < k * 100000; i++) {
                    i++;
                    i--;
                }
                if(this.l2.tryLock())
                    try {
                        System.out.println(this.getName() + " - STATE 3");
                        System.out.println(this.getName() + " - START TRANSITION 3-4");
                        Thread.sleep(sleep * 500L);
                        System.out.println(this.getName() + " - END TRANSITION 3-4");
                    } finally {
                        this.l2.unlock();
                    }
            } finally {
                this.l1.unlock();
            }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        System.out.println(this.getName() + " - STATE 4");
    }
}