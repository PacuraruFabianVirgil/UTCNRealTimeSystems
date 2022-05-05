
public class Controller extends Thread {

	boolean stop = false;
	public Robot r;
	public Supervisor s;
	PlaceHandler PH = new PlaceHandler();

	Controller_Transition_t_1 t_1;
	Controller_Transition_t_2 t_2;
	Controller_Transition_t_o2 t_o2;

	public void run() {

		PH.AddPlace(new IntPlace("p_i1", null));
		PH.AddPlace(new IntPlace("p_1", 0));
		PH.AddPlace(new IntPlace("p_o1", null));
		PH.AddPlace(new IntPlace("p_2", null));
		PH.AddPlace(new IntPlace("p_i2", null));

		t_1 = new Controller_Transition_t_1("t_1", PH, 0);
		t_2 = new Controller_Transition_t_2("t_2", PH, 0);
		t_2.SupervisorPH = s.PH;// this transition has an output channel connected to the supervisor
		t_o2 = new Controller_Transition_t_o2("t_o2", PH, 0);
		t_o2.RobotPH = r.PH;// this transition has an output channel connected to the robot

		while (!stop) {

			t_1.TransitionGuardsMappings();

			t_2.TransitionGuardsMappings();

			t_o2.TransitionGuardsMappings();
			
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
