package rivercrossing;

import java.lang.Cloneable;

public class Passenger implements Cloneable
{
	protected boolean crossed;
	protected String name;

	public Passenger(String name)
	{
		this.crossed = false;
		this.name = name;
	}

	public boolean isRaft()
	{
		return false;
	}

	public Passenger clone()
	{
		Passenger clone = new Passenger(name);
		clone.crossed = this.crossed;
		return clone;
	}

	public boolean hasCrossed()
	{
		return crossed;
	}

	public void setCrossed(boolean crossed)
	{
		this.crossed = crossed;
	}

	public void cross()
	{
		crossed = !crossed;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

}
