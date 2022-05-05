import Interfaces.PlaceHandlerTemplate;
import Interfaces.TransitionTemplate;

public class Supervisor_Transition_ts_2 implements TransitionTemplate {
    Integer timeUnitControl = 500;
    Integer eet;
    Integer let;
    String Name;
    PlaceHandlerTemplate PH;
    PlaceHandlerTemplate ControllerPH;

    public Supervisor_Transition_ts_2(String name, PlaceHandlerTemplate PH, Integer delay) {
        this.Init(name, PH);
        this.SetDelay(delay);
    }

    public Supervisor_Transition_ts_2(String name, PlaceHandlerTemplate PH, Integer eet, Integer let) {
        this.Init(name, PH);
        this.SetDelayInRange(eet, let);
    }

    @Override
    public void TransitionDelay() {
        try {
            if (this.let == null) {
                Thread.sleep((long)this.eet * timeUnitControl);
            } else {
                Thread.sleep(Math.round(Math.random() * (this.let - this.eet) + this.eet) * timeUnitControl);
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public Boolean TransitionGuardsMappings() {
        TransitionDelay();
        String toPrint="--------------Supervisor-------------------\n";

        C1_ps_i1 rel = (C1_ps_i1) PH.GetPlaceByName("ps_1").Get();
        if (!PH.GetPlaceByName("ps_1").IsNull() && !PH.GetPlaceByName("ps_2").IsNull() &&
                (rel.r != PH.GetPlaceByName("ps_2").Get())) {
            toPrint = toPrint.concat(Print() + "\n");
            toPrint=toPrint.concat("ControllerPH\n"+ControllerPH.PrintAllPlaces()+"\n");

            PH.GetPlaceByName("ps_o1").Set(rel.r);
            PH.GetPlaceByName("ps_3").Set(rel.e);
            ControllerPH.GetPlaceByName("p_i1").Set(PH.GetPlaceByName("ps_2").Get());
            PH.GetPlaceByName("ps_2").Set(null);

            toPrint = toPrint.concat(Print() + "\n");
            toPrint=toPrint.concat("ControllerPH\n"+ControllerPH.PrintAllPlaces()+"\n");
            toPrint = toPrint.concat("--------------------------------------\n");

            System.out.println(toPrint);
            return true;
        }
        return false;
    }

    @Override
    public void Init(String name, PlaceHandlerTemplate PH) {
        this.PH = PH;
        this.Name = name;
    }

    @Override
    public void SetDelay(int value) {
        this.eet = value;
    }

    @Override
    public void SetDelayInRange(int eet, int let) {
        this.eet = eet;
        this.let = let;
    }

    @Override
    public String Print() {
        return this.Name + "\n" + this.PH.PrintAllPlaces();
    }
}
