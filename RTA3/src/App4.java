public class App4 {
    public static void main(String[] args) throws InterruptedException {
        Integer monitor1 = 1;
        Integer monitor2 = 2;
        ExecutionThread4 thread = new ExecutionThread4(monitor1,monitor2,1,2,3);
        thread.start();
        new ExecutionThread4(thread,monitor1,2,3,5).start();
        new ExecutionThread4(thread,monitor2,3,4,6).start();

    }
}

class ExecutionThread4 extends Thread {
    ExecutionThread4 thread;
    Integer monitor1, monitor2;
    int minimum, maximum, threadIndex;
    public ExecutionThread4 (Integer monitor1, Integer monitor2, int threadIndex, int minimum, int maximum) {
        this.monitor1 = monitor1;
        this.monitor2 = monitor2;
        this.threadIndex = threadIndex;
        this.minimum = minimum;
        this.maximum = maximum;
    }
    public ExecutionThread4 (ExecutionThread4 thread, Integer monitor1, int threadIndex, int minimum, int maximum) throws InterruptedException {
        this.thread = thread;
        this.monitor1 = monitor1;
        this.threadIndex = threadIndex;
        this.minimum = minimum;
        this.maximum = maximum;
    }
    public void run() {
        if(threadIndex==1) {
            System.out.println(this.getName() + " - STATE 1");
            System.out.println(this.getName() + " - START TRANSITION 1-2");
            try {
                Thread.sleep(7 * 500);
                System.out.println(this.getName() + " - END TRANSITION 1-2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(this.getName() + " - STATE 2");
            int k = (int) Math.round(Math.random() * (maximum - minimum) + minimum);
            for (int i = 0; i < k * 100000; i++) {
                i++;
                i--;
            }
            synchronized (monitor1) {
                synchronized (monitor2) {
                    System.out.println(this.getName() + " - END OF STATE 2");
                    System.out.println(this.getName() + " - STATE 3");
                    monitor1.notify();
                    System.out.println(this.getName() + " - ACTIVATE THREAD 1 (P6)");
                    monitor2.notify();
                    System.out.println(this.getName() + " - ACTIVATE THREAD 2 (P10)");
                }
            }
        } else {
            System.out.println(this.getName() + " - STATE 1");
            synchronized (monitor1) {
                try {
                    monitor1.wait();
                    System.out.println(this.getName() + " - STATE 2");
                    int k = (int) Math.round(Math.random() * (maximum - minimum) + minimum);
                    for (int i = 0; i < k * 100000; i++) {
                        i++;
                        i--;
                    }
                    System.out.println(this.getName() + " - END OF STATE 2");
                    System.out.println(this.getName() + " - STATE 3");
                    thread.join();
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }
            }
        }
    }
}