import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

public class App4 {
    public static void main(String[]args) {
        MyWindow2 win = new MyWindow2();
        Thread[] t = new Thread[3];
        Movement2[] mov = new Movement2[3];
        for(int i =0; i<3; i++){
            mov[i] = new Movement2(i,win);
            mov[i].addObserver(win);
            t[i] = new Thread(mov[i],"mov"+i);
            t[i].start();
        }
    }
}

class MyWindow2 extends JFrame implements Observer {
    int[] coordinates = new int[]{0, 0, 0};
    Panel2 panel = new Panel2(coordinates);
    @Override
    public void update(Observable mov, Object y){
        YCord2 cord = (YCord2) y;
        int newC = cord.getCord();
        int myId = cord.getId();
        switch (myId) {
            case 0 -> coordinates[0] = newC;
            case 1 -> coordinates[1] = newC;
            case 2 -> coordinates[2] = newC;
        }
        this.panel = new Panel2(coordinates);
        this.setContentPane(panel);
        this.setVisible(true);
    }
    public MyWindow2() {
        setLayout(null);
        setSize(350,400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setContentPane(panel);
    }
}

class Panel2 extends JPanel {
    int square1, square2, square3;
    public Panel2(int[] square) {
        this.square1 = square[0];
        this.square2 = square[1];
        this.square3 = square[2];
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(40, square1, 60, 60);
        g.setColor(Color.RED);
        g.fillRect(140, square2, 60, 60);
        g.setColor(Color.BLUE);
        g.fillRect(240, square3, 60, 60);
    }
}

class Movement2 extends Observable implements Runnable {
    int id, size;
    MyWindow2 win;
    public Movement2(int id, MyWindow2 win) {
        this.id = id;
        this.win = win;
        size = win.getHeight();
    }
    public void run() {
        Random rand = new Random();
        int c = 0, n = rand.nextInt(10);
        while (c < 5*size) {
            for(int i=0;i<(n+1)*5000;i++) {
                System.out.println();
            }
            c = c + 10;
            setChanged();
            YCord2 y = new YCord2(this.id, c);
            notifyObservers(y);
            stop(c>=size);
        }
    }
    public void stop(boolean exit) {
        if(exit) {
            for (Thread t : Thread.getAllStackTraces().keySet()) {
                if (t.getName().equals("mov"+id)) {
                    t.stop();
                }
            }
        }
    }
}

class YCord2 {
    int id, square;
    public YCord2(int id, int square) {
        this.id = id;
        this.square = square;
    }
    public int getId() {
        return(id);
    }
    public int getCord() {
        return(square);
    }
}