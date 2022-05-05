package Interfaces;

public interface PlaceTemplate {
	public String GetPlaceName();

	public void SetPlaceName(String name);

	public Object Get();

	public Boolean IsNull();
	
	public void Set(Object value);

	public String Print();

	public void Init(String name, Object value);
}
