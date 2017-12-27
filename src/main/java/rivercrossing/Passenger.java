package rivercrossing;

public abstract class Passenger
{
	protected boolean crossed;
	protected String name;

	public abstract boolean isRaft();
	public abstract Passenger clone();

	public boolean hasCrossed()
	{
		return crossed;
	}

	public void cross()
	{
		crossed = !crossed;
	}

	public String getName()
	{
		return name;
	}

}
