
public class Main {

	public static void main(String[] args) {
		Supervisor s = new Supervisor();
		Controller c = new Controller();
		Robot r = new Robot ();
		s.c = c;
		c.r = r;
		c.s = s;
		r.c = c;
		s.start();
		c.start();
		r.start();
	}
}
