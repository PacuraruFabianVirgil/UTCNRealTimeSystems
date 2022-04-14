import javax.swing.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

class Main {
    private static final int noOfThreads=6;
    private static final int processorLoad=1000;
    public static void main(String args[]){
        Window win=new Window(noOfThreads);
        Thread[] t = new Thread[noOfThreads];
        Fir[] fir = new Fir[noOfThreads];
        for(int i =0; i<noOfThreads; i++){
            fir[i] = new Fir(i,win,processorLoad);
            fir[i].addObserver(win);
            t[i] = new Thread(fir[i],"fir"+i);
            t[i].setPriority(i+2);
            t[i].start();
        }
    }
}

class Window extends JFrame implements Observer {
    ArrayList<JProgressBar> bars=new ArrayList<JProgressBar>();
    @Override
    public void update(Observable fir, Object value){
        Value val = (Value) value;
        bars.get(val.getId()).setValue(val.getC());
    }
    public Window(int nrThreads) {
        setLayout(null);
        setSize(450,400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        init(nrThreads);
        this.setVisible(true);
    }
    private void init(int n){
        for(int i=0 ;i<n; i++){
            JProgressBar pb=new JProgressBar();
            pb.setMaximum(1000);
            pb.setBounds(50,(i+1)*30,350,20);
            this.add(pb);
            this.bars.add(pb);
        }
    }
}

class Fir extends Observable implements Runnable{
    int id;
    Window win;
    int processorLoad;
    Fir(int id, Window win, int processorLoad){
        this.id=id;
        this.win=win;
        this.processorLoad=processorLoad;
    }
    public void run(){
        int c=0;
        while(c<1000){
            for(int j=0;j<this.processorLoad;j++){
                System.out.println();
            }
            c++;
            setChanged();
            Value value = new Value(this.id,c);
            notifyObservers(value);
        }
    }
}

class Value {
    int id;
    int c;
    public Value(int id, int c) {
        this.id = id;
        this.c = c;
    }
    public int getId(){
        return(id);
    }
    public int getC(){
        return(c);
    }
}