package rivercrossing

public abstract class Passenger
{
	protected boolean crossed;

	public abstract boolean isRaft();

	public boolean hasCrossed()
	{
		return crossed;
	}

	public void cross()
	{
		crossed = !crossed;
	}
}
