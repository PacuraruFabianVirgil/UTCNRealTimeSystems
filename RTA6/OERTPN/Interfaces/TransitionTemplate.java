package Interfaces;

public interface TransitionTemplate {
	public void Init(String Name, PlaceHandlerTemplate PH);

	public void SetDelay(int value);

	public void SetDelayInRange(int eet, int let);

	public void TransitionDelay();

	public Boolean TransitionGuardsMappings();

	public String Print();
}
