public class App2 {
    public static void main(String[] args) {
        new ExecutionThread2(1,2, 4, 2, 4,4,6).start();
        new ExecutionThread2(2, 1, 5, 3, 5,5,7).start();
    }
}

class ExecutionThread2 extends Thread {
    Integer monitor1, monitor2;
    int sleep,  activity_min_1, activity_max_1, activity_min_2, activity_max_2;
    public ExecutionThread2(Integer monitor1, Integer monitor2, int sleep, int activity_min_1, int activity_max_1,
    int activity_min_2, int activity_max_2) {
        this.monitor1 = monitor1;
        this.monitor2 = monitor2;
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
        if(monitor1==1) {
            try {
                Thread.sleep(7*500);  //solves the blocking problem
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        synchronized (monitor1) {
            System.out.println(this.getName() + " - STATE 2");
            k = (int) Math.round(Math.random() * (activity_max_2 - activity_min_2) + activity_min_2);
            for (int i = 0; i < k * 100000; i++) {
                i++;
                i--;
            }
            synchronized (monitor2) {
                System.out.println(this.getName() + " - STATE 3");
                System.out.println(this.getName() + " - START TRANSITION 2-3");
                try {
                    Thread.sleep(sleep * 500);
                    System.out.println(this.getName() + " - END TRANSITION 2-3");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(this.getName() + " - STATE 4");
    }
}