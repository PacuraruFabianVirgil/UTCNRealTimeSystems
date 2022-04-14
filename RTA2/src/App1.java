import java.io.*;
import java.util.Date;

public class App1 {
    private static final boolean stopThreads = false;
    public static void main(String[] args){
        FileService service = new FileService("messages.txt");
        RThread reader = new RThread(service);
        WThread writer = new WThread(service);
        reader.start();
        writer.start();
    }
    public static boolean isStopThreads(){
        return stopThreads;
    }
}

class FileService {
    String fileName;
    BufferedReader in;
    PrintWriter out;
    public FileService(String fName){
        this.fileName = fName;
        try {
            out = new PrintWriter(new FileWriter(fileName, true));
            in = new BufferedReader(new FileReader(fileName));
        } catch (Exception e) { e.printStackTrace();}
    }
    public void write(String msg){
        Date date = new Date(System.currentTimeMillis());
        out.println("Date: " + date);
        out.println("Message: " + msg);
        out.flush();
    }
    public String read() throws IOException {
        String iterator, last="no message to read";
        while((iterator = in.readLine()) != null){
            last = new Date(System.currentTimeMillis()) + " - " + iterator;
        }
        return last;
    }
}

class WThread extends Thread{
    FileService service;
    public WThread(FileService service) {
        this.service = service;
    }
    public void run(){
        while(!App1.isStopThreads()){
            String msg = String.valueOf(Math.round(Math.random()*100));
            synchronized (this) {
                service.write(msg);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class RThread extends Thread{
    FileService service;
    public RThread(FileService service) {
        this.service = service;
    }
    public void run(){
        while (!App1.isStopThreads()){
            synchronized (this) {
                try {
                    String readMsg = service.read();
                    System.out.println(readMsg);
                    Thread.sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}