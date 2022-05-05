import Interfaces.PlaceTemplate;

public class C1_ps_i1 implements PlaceTemplate {
    Integer r,e,l;
    String Name;

    public C1_ps_i1(String name, Object value) {
        this.Init(name, value);
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
        return new Integer[]{r, e, l};
    }

    @Override
    public void Set(Object value) {
        Integer[] rel = (Integer[]) value;
        this.r = rel[0];
        this.e = rel[1];
        this.l = rel[2];
    }

    @Override
    public String Print() {
        return "[" + this.Name + "= [" + this.r + ", " + this.e + ", " + this.l + "]]";
    }

    @Override
    public void Init(String name, Object value) {
        this.SetPlaceName(name);
        this.Set(value);
    }

    @Override
    public Boolean IsNull() {
        return this.Get() == null;
    }
}
