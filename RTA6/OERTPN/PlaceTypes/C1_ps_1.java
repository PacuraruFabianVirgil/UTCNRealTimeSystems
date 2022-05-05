import Interfaces.PlaceTemplate;

import java.util.ArrayList;

public class C1_ps_1 implements PlaceTemplate {
    ArrayList<C1_ps_i1> ps = new ArrayList();
    String Name;

    public C1_ps_1(String name){
        this.Init(name, null);
    }

    @Override
    public String GetPlaceName() {
        return this.Name;
    }

    @Override
    public void SetPlaceName(String name) {
        this.Name = name;
    }

    @Override
    public Object Get() {
        if(ps.size()>0) return ps.get(ps.size()-1);
        else return null;
    }

    @Override
    public Boolean IsNull() {
        return this.Get() == null;
    }

    @Override
    public void Set(Object value) {
        ps.add((C1_ps_i1) value);
    }

    @Override
    public String Print() {
        return this.Name;
    }

    @Override
    public void Init(String name, Object value) {
        this.SetPlaceName(name);
    }
}
