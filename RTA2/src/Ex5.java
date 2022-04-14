import java.io.PipedOutputStream;

import java.io.*;

public class Ex5{
    public static void main(String args[]) {
        ReadThread rt = new ReadThread();
        WriteThread wt = new WriteThread();
        try{
            rt.conect(wt.getPipe());
            rt.start();wt.start();
        }catch(Exception e){e.printStackTrace();}
    }
}

class WriteThread extends Thread{
    private PipedOutputStream po;
    WriteThread(){
        po = new PipedOutputStream();
    }
    public void run(){
        try{
            while (true){
                int d = (int)(10*Math.random());
                System.out.println("Writing Thread is sent : "+d);
                po.write(d);
                sleep(400);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    PipedOutputStream getPipe(){return po;}
}

class ReadThread extends Thread{
    private PipedInputStream pi;
    ReadThread(){
        pi = new PipedInputStream();
    }
    public void run(){
        try{
            while (true){
                if (pi.available()>0) {
                    System.out.println("Read Thread is received : "+pi.read());
                }
            }
        }catch(Exception e){} }
    void conect(PipedOutputStream os)throws Exception{ pi.connect(os); }
}