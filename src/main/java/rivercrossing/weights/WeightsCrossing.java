package rivercrossing.weights;

import java.util.ArrayList;
import java.util.List;
import rivercrossing.Passenger;
import rivercrossing.Raft;
import rivercrossing.Rules;
import rivercrossing.weights.Weight;

public class WeightsCrossing extends Rules
{

	private static final String ONE = "one";
	private static final String TWO = "two";
	private static final String THREE = "three";
	private static final String FOUR = "four";

	public WeightedState getInitialState()
	{
		Weight one = new Weight(ONE, 1);
		Weight two = new Weight(TWO, 2);
		Weight three = new Weight(THREE, 3);
		Weight four = new Weight(FOUR, 4);

		List<Passenger> passengerList = new ArrayList(4);
		passengerList.add(new Raft());
		passengerList.add(one);
		passengerList.add(two);
		passengerList.add(three);
		passengerList.add(four);

		WeightedState initialState = new WeightedState();
		initialState.setPassengers(passengerList);
		initialState.setPreviousMass(0);
		return initialState;
	}

	public boolean banksAreValid()
	{
		return true;
	}

	public boolean isValidRaft(List<Passenger> loadedRaft)
	{
		int totalMass = getMassOfRaft(loadedRaft);
		return totalMass > 0
			&& (giveOrTakeOne(totalMass, getPreviousMass())
					|| getPreviousMass() == 0);
	}

	public WeightedState cross(List<Passenger> loadedRaft)
	{
		WeightedState newState = (WeightedState) super.cross(loadedRaft);
		newState.setPreviousMass(getMassOfRaft(loadedRaft));
		return newState;
	}

	private int getPreviousMass()
	{
		return ((WeightedState)currentState).getPreviousMass();
	}

	private void setPreviousMass(int previousMass)
	{
		((WeightedState)currentState).setPreviousMass(previousMass);
	}

	private int getMassOfRaft(List<Passenger> loadedRaft)
	{
		int totalMass = 0;
		for (Passenger p : loadedRaft)
		{
			if (!p.isRaft())
			{
				Weight weight = (Weight) p;
				totalMass += weight.getMass();
			}
		}

		return totalMass;
	}

	private boolean giveOrTakeOne(int a, int b)
	{
		return a == b
			|| a + 1 == b
			|| a - 1 == b;
	}

}
