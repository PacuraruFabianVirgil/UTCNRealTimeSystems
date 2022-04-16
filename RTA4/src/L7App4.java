import java.util.concurrent.Semaphore;

public class L7App4 {
    public static void main(String[] args) {
        Semaphore s = new Semaphore(2);
        new ExecutionThread3(s,3,4,7,2).start();
        new ExecutionThread3(s,6,5,7,2).start();
        new ExecutionThread3(s,5,3,6,2).start();
    }
}

class ExecutionThread3 extends Thread {
    Semaphore s;
    int sleep, activity_min, activity_max, permit;
    public ExecutionThread3(Semaphore s, int sleep, int activity_min, int activity_max, int permit) {
        this.s = s;
        this.sleep = sleep;
        this.activity_min = activity_min;
        this.activity_max = activity_max;
        this.permit = permit;
    }
    public void run() {
        while(true) {
            try {
                System.out.println(this.getName() + " - STATE 1");
                this.s.acquire(this.permit);
                System.out.println(this.getName() + " - STATE 2");
                int k = (int) Math.round(Math.random() * (activity_max - activity_min) + activity_min);
                for (int i = 0; i < k * 100000; i++) {
                    i++;
                    i--;
                }
                System.out.println(this.getName() + " - END OF STATE 2");
                this.s.release(this.permit);
                System.out.println(this.getName() + " - STATE 3");
                Thread.sleep(sleep * 500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(this.getName() + " - STATE 4");
        }
    }
}