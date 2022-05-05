import Interfaces.PlaceTemplate;

public class IntPlace implements PlaceTemplate {
	Integer value;
	String Name;

	public IntPlace(String name, Object value) {
		this.Init(name, value);
	}

	@Override
	public Object Get() {
		return this.value;
	}

	@Override
	public void Set(Object value) {
		this.value = (Integer) value;
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
	public String Print() {
		return "[" + this.Name + "=" + this.value + "]";
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
