package rivercrossing.weights;

import java.util.ArrayList;
import java.util.List;
import rivercrossing.State;

public class WeightedState extends State
{
	protected int previousMass;

	public WeightedState()
	{
		super(WeightsCrossing.class);
	}

	public int getPreviousMass()
	{
		return previousMass;
	}

	public void setPreviousMass(int previousMass)
	{
		this.previousMass = previousMass;
	}

	public WeightedState clone()
	{
		WeightedState newState = new WeightedState();
		newState.passengerList = super.clone().getPassengers();
		newState.previousMass = previousMass;
		return newState;
	}

	public String toString()
	{
		return super.toString() + " ; PreviousMass " + previousMass;
	}

	public boolean equals(Object otherState)
	{
		if (!(otherState instanceof WeightedState))
		{
			throw new IllegalArgumentException();
		}

		return super.equals(otherState) && previousMass == ((WeightedState)otherState).previousMass;
	}

	public int hashCode()
	{
		return super.hashCode() + previousMass;
	}

}
