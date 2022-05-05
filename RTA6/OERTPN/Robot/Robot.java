
public class Robot extends Thread {

	boolean stop = false;
	PlaceHandler PH = new PlaceHandler();

	public Controller c;

	Robot_Transition_t_1 t_1;
	Robot_Transition_t_2 t_2;

	public void run() {

		PH.AddPlace(new IntPlace("p_0", 0));
		PH.AddPlace(new IntPlace("p_1", null));
		PH.AddPlace(new IntPlace("p_i", null));
		PH.AddPlace(new IntPlace("p_o", null));
		t_1 = new Robot_Transition_t_1("t_1", PH, 0);
		t_2 = new Robot_Transition_t_2("t_2", PH, 5);
		t_2.ControllerPH = c.PH;// this transition has an output channel connected to the controller

		while (!stop) {
			t_1.TransitionGuardsMappings();

			t_2.TransitionGuardsMappings();
			
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
