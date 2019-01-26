package rivercrossing.wolfGoatCabbage;

import java.util.ArrayList;
import java.util.List;
import rivercrossing.Passenger;
import rivercrossing.Raft;
import rivercrossing.Rules;
import rivercrossing.State;

public class WolfGoatCabbageCrossing extends Rules
{

	private static final String WOLF = "wolf";
	private static final String GOAT = "goat";
	private static final String CABBAGE = "cabbage";
	private static final String FARMER = "farmer";

	public State getInitialState()
	{
		Passenger wolf = new Passenger(WOLF);
		Passenger goat = new Passenger(GOAT);
		Passenger cabbage = new Passenger(CABBAGE);
		Passenger farmer = new Passenger(FARMER);

		List<Passenger> passengerList = new ArrayList(5);
		passengerList.add(new Raft());
		passengerList.add(wolf);
		passengerList.add(goat);
		passengerList.add(cabbage);
		passengerList.add(farmer);

		State initialState = new State(WolfGoatCabbageCrossing.class);
		initialState.setPassengers(passengerList);
		return initialState;
	}

	public boolean isValidRaft(List<Passenger> loadedRaft)
	{
		return containsFarmer(loadedRaft);
	}

	public boolean banksAreValid(State currentState)
	{
		Passenger wolf = currentState.getPassenger(WOLF);
		Passenger goat = currentState.getPassenger(GOAT);
		Passenger cabbage = currentState.getPassenger(CABBAGE);
		Passenger farmer = currentState.getPassenger(FARMER);

		if (wolf.hasCrossed() == goat.hasCrossed()
			 && wolf.hasCrossed()	!= farmer.hasCrossed())
		{
			//System.out.println("bank invalid: farmer left wolf with goat");
			return false;
		}

		if (goat.hasCrossed() == cabbage.hasCrossed()
			 && goat.hasCrossed()	!= farmer.hasCrossed())
		{
			//System.out.println("bank invalid: farmer left goat with cabbage");
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
		//System.out.println("raft invalid: no farmer");
		return false;
	}

}
