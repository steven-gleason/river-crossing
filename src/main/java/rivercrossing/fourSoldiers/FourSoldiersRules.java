package rivercrossing.fourSoldiers;

import java.util.ArrayList;
import java.util.List;
import rivercrossing.Passenger;
import rivercrossing.Raft;
import rivercrossing.Rules;
import rivercrossing.State;

public class FourSoldiersRules extends Rules
{

	static final String ARROGANT = "arrogant_red";
	static final String LAZY = "lazy_green";
	static final String BRAVE_A = "brave_A_blue";
	static final String BRAVE_B = "brave_B_blue";

	public State getInitialState()
	{
		Passenger arrogant = new Passenger(ARROGANT);
		Passenger lazy = new Passenger(LAZY);
		Passenger braveA = new Passenger(BRAVE_A);
		Passenger braveB = new Passenger(BRAVE_B);

		List<Passenger> passengerList = new ArrayList(5);
		passengerList.add(new Raft());
		passengerList.add(arrogant);
		passengerList.add(lazy);
		passengerList.add(braveA);
		passengerList.add(braveB);

		State initialState = new State(FourSoldiersRules.class);
		initialState.setPassengers(passengerList);
		return initialState;
	}

	public boolean isValidRaft(List<Passenger> loadedRaft)
	{
		return !raftLazyIsAlone(loadedRaft) && !raftArrogantHasCompany(loadedRaft);
	}

	public boolean banksAreValid(State currentState)
	{
		// Lazy is not alone?
		boolean lazySide = currentState.getPassenger(LAZY).hasCrossed();

		boolean arrogantSide = currentState.getPassenger(ARROGANT).hasCrossed();
		boolean braveASide = currentState.getPassenger(BRAVE_A).hasCrossed();
		boolean braveBSide = currentState.getPassenger(BRAVE_B).hasCrossed();

		return lazySide == arrogantSide
			|| lazySide == braveASide
			|| lazySide == braveBSide;
	}

	private static boolean raftLazyIsAlone(List<Passenger> loadedRaft)
	{
		return loadedRaft.size() == 2 && containsPassengerNamed(LAZY, loadedRaft);
	}

	private static boolean raftArrogantHasCompany(List<Passenger> loadedRaft)
	{
		return loadedRaft.size() == 3 && containsPassengerNamed(ARROGANT, loadedRaft);
	}

	private static boolean containsPassenger(Passenger needle, List<Passenger> loadedRaft)
	{
		return containsPassengerNamed(needle.getName(), loadedRaft);
	}

	private static boolean containsPassengerNamed(String needle, List<Passenger> loadedRaft)
	{
		for (Passenger p : loadedRaft)
		{
			if (p.getName().equals(needle))
			{
				return true;
			}
		}
		return false;
	}

}
