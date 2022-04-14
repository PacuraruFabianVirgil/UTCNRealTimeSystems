public class Ex3 {
    static int result = 0;
    public static void main(String[] args){
        JoinTestThread w1 = new JoinTestThread("Thread 1",null, 50011);
        JoinTestThread w2 = new JoinTestThread("Thread 2",w1, 20009);
        w1.start();
        w2.start();
    }
}

class JoinTestThread extends Thread{
    Thread t;
    int number;
    int sum =0;
    JoinTestThread(String n, Thread t, int number){
        this.setName(n);
        this.t=t;
        this.number = number;
    }
    public void run() {
        try {
            if (t != null) t.join();
        }
        catch(Exception e){e.printStackTrace();}
        for(int i=1;i<=number;i++){
            if(number%i==0){
                System.out.print(i+" ");
                sum+=i;
            }
        }
        Ex3.result+=sum;
        System.out.print("\n The result is: "+Ex3.result+"\n");
    }
}