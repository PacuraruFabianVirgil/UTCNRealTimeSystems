import java.util.Scanner;

public class Supervisor extends Thread {

    boolean stop = false;
    PlaceHandler PH = new PlaceHandler();

    public Controller c;

    Supervisor_Transition_ts_1 ts_1;
    Supervisor_Transition_ts_2 ts_2;
    Supervisor_Transition_ts_3 ts_3;
    Scanner in = new Scanner(System.in);

    public void run() {

        PH.AddPlace(new C1_ps_i1("ps_i1", new Integer[]{null, null, null}));
        PH.AddPlace(new C1_ps_1("ps_1"));
        PH.AddPlace(new IntPlace("ps_i2", null));
        PH.AddPlace(new IntPlace("ps_o1", null));
        PH.AddPlace(new IntPlace("ps_o2", null));
        PH.AddPlace(new IntPlace("ps_1", 0));
        PH.AddPlace(new IntPlace("ps_2", 0));
        PH.AddPlace(new IntPlace("ps_3", 0));
        ts_1 = new Supervisor_Transition_ts_1("ts_1", PH, 0);
        ts_2 = new Supervisor_Transition_ts_2("ts_2", PH, 0);
        ts_2.ControllerPH = c.PH;// this transition has an output channel connected to the controller
        ts_3 = new Supervisor_Transition_ts_3("ts_3", PH, 0);

        System.out.println("Supervisor: Input ps_i1 value");
        this.PH.GetPlaceByName("ps_i1").Set(new Integer[]{Integer.parseInt(in.nextLine()), null, null});

        while (!stop) {
            ts_1.TransitionGuardsMappings();

            ts_2.TransitionGuardsMappings();

            ts_3.TransitionGuardsMappings();

            // For slower printing on the console
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    public void StopThread() {
        this.stop = true;
    }

}
