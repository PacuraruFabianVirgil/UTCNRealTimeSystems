public class App1 {
    public static void main(String[] args) {
        new ExecutionThread1(1,null, 4, 2, 4).start();
        new ExecutionThread1(1, 2, 3, 3, 6).start();
        new ExecutionThread1(2, null, 5, 2, 5).start();
    }
}

class ExecutionThread1 extends Thread {
    Integer monitor1, monitor2;
    int sleep,  activity_min, activity_max;
    public ExecutionThread1(Integer monitor1, Integer monitor2, int sleep, int activity_min, int activity_max) {
        this.monitor1 = monitor1;
        this.monitor2 = monitor2;
        this.sleep = sleep;
        this.activity_min = activity_min;
        this.activity_max = activity_max;
    }
    private void loop() {
        System.out.println(this.getName() + " - STATE 2");
        int k = (int) Math.round(Math.random() * (activity_max - activity_min) + activity_min);
        for (int i = 0; i < k * 100000; i++) {
            i++;
            i--;
        }
        System.out.println(this.getName() + " - START TRANSITION 2-3");
        try {
            Thread.sleep(sleep * 500);
            System.out.println(this.getName() + " - END TRANSITION 2-3");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void run() {
        System.out.println(this.getName() + " - STATE 1");
        if(monitor2!=null) {
            synchronized (monitor1) {
                synchronized (monitor2) {
                    loop();
                }
            }
        } else {
            synchronized (monitor1) {
                loop();
            }
        }
        System.out.println(this.getName() + " - STATE 3");
    }
}