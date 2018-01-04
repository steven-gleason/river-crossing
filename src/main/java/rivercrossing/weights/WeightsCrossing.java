package rivercrossing.weights;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import rivercrossing.Passenger;
import rivercrossing.Raft;
import rivercrossing.RiverCrossing;
import rivercrossing.weights.Weight;

public class WeightsCrossing extends RiverCrossing
{

	private static final String ONE = "one";
	private static final String TWO = "two";
	private static final String THREE = "three";
	private static final String FOUR = "four";

	private int previousMass;
	private Stack<Integer> massHistory;

	public static void main(String[] args)
	{
		WeightsCrossing wc = new WeightsCrossing();
		wc.nextMove();
		System.out.println("no solution");
	}


	public WeightsCrossing()
	{
		super();
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
		currentState.setPassengers(passengerList);

		previousMass = 0;
		massHistory = new Stack();
	}

	public boolean isValidRaft(List<Passenger> loadedRaft)
	{
		int totalMass = getMassOfRaft(loadedRaft);
		return totalMass > 0
			&& (giveOrTakeOne(totalMass, previousMass) || previousMass == 0);
	}

	public void cross(List<Passenger> loadedRaft)
	{
		super.cross(loadedRaft);
		massHistory.push(previousMass);
		previousMass = getMassOfRaft(loadedRaft);
	}

	public void revert()
	{
		super.revert();
		previousMass = massHistory.pop();
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

	public boolean banksAreValid()
	{
		return true;
	}

}
