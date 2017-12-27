package rivercrossing.wolfGoatCabbage;

import java.util.List;
import rivercrossing.Passenger;
import rivercrossing.Raft;
import rivercrossing.RiverCrossing;

public class WolfGoatCabbageCrossing extends RiverCrossing
{

	private static final String WOLF = "wolf";
	private static final String GOAT = "goat";
	private static final String CABBAGE = "cabbage";
	private static final String FARMER = "farmer";

	public static void main(String[] args)
	{
		WolfGoatCabbageCrossing wgc = new WolfGoatCabbageCrossing();
		wgc.nextMove();
		System.out.println("no solution");
	}


	public WolfGoatCabbageCrossing()
	{
		super();
		Passenger wolf = new Passenger(WOLF);
		Passenger goat = new Passenger(GOAT);
		Passenger cabbage = new Passenger(CABBAGE);
		Passenger farmer = new Passenger(FARMER);

		currentState.add(new Raft());
		currentState.add(wolf);
		currentState.add(goat);
		currentState.add(cabbage);
		currentState.add(farmer);
	}

	public boolean isValidRaft(List<Passenger> loadedRaft)
	{
		return containsRaft(loadedRaft) && containsFarmer(loadedRaft);
	}

	public boolean banksAreValid()
	{
		Passenger wolf = getPassenger(WOLF);
		Passenger goat = getPassenger(GOAT);
		Passenger cabbage = getPassenger(CABBAGE);
		Passenger farmer = getPassenger(FARMER);

		if (wolf.hasCrossed() == goat.hasCrossed()
			 && wolf.hasCrossed()	!= farmer.hasCrossed())
		{
			return false;
		}

		if (goat.hasCrossed() == cabbage.hasCrossed()
			 && goat.hasCrossed()	!= farmer.hasCrossed())
		{
			return false;
		}

		return true;
	}

	private static boolean containsFarmer(List<Passenger> loadedRaft)
	{
		for (Passenger p : loadedRaft)
		{
			if (p.getName().equals(FARMER))
			{
				return true;
			}
		}
		return false;
	}

}
