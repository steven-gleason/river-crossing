package rivercrossing.weights;

import java.lang.Cloneable;
import rivercrossing.Passenger;

public class Weight extends Passenger
{

	protected int mass;

	public Weight(String name, int mass)
	{
		super(name);
		this.mass = mass;
	}

	public Weight clone()
	{
		Weight clone = new Weight(name, mass);
		clone.crossed = this.crossed;
		return clone;
	}

	public int getMass()
	{
		return mass;
	}

}
