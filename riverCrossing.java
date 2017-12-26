package rivercrossing

import java.util.List;
import java.util.Stack;

public abstract class RiverCrossing
{
	protected List<Passenger> currentState;
	protected Stack<List<Passenger>> history;

	abstract public boolean isSolved();
	abstract public boolean isValidRaft(List<Passenger> loadedRaft);
	abstract public boolean banksAreValid();

	/**
	* General use move engine.
	* Assumes 1 or 2 passengers per raft; only 1 raft.
	**/
	public void nextMove()
	{
		Passenger raft = getRaft();
		boolean direction = raft.isCrossed();

		for (Passenger p1 : getPassengers())
		{
			if (isSolved())
			{
				endGame();
			}
			List<Passenger> loadedRaft = new ArrayList(3);
			loadedRaft.add(raft);
			loadedRaft.add(p1);
			tryMove(loadedRaft);
			for (Passenger p2 : getPassengers())
			{
				if (p1.getName().compareTo(p2.getName()) > 1)
				{
					loadedRaft.add(p2);
					tryMove(loadedRaft);
					loadedRaft.remove(loadedRaft.size() -1);
				}
			}
		}
	}

	private void tryMove(List<Passenger> loadedRaft)
	{
		if (isValidRaft(loadedRaft))
		{
			cross(loadedRaft);
			if (banksAreValid() && !hasLooped())
			{
				nextMove();
			}
		  revert();
		}
	}

	public List<Passenger> getPassengers()
	{
		returns currentState;
	}

	public void revert()
	{
		currentState = history.pop();
	}

	public boolean containsRaft(List<Passenger> loadedRaft)
	{
		for (Passenger passenger : loadedRaft)
		{
			if (passenger.isRaft())
			{
				return true;
			}
		}
		return false;
	}

	public boolean hasLooped()
	{
		return history.contains(getPassengers());
	}

	public void cross(List<Passenger> loadedRaft)
	{
		List<Passenger> oldState = cloneCurrentState();
		history.push(oldState);

		for (Passenger traveler : loadedRaft)
		{
			traveler.cross();
		}
	}

	private List<Passenger> cloneCurrentState()
	{
		List<Passenger> currentState = getPassengers();
		List<Passenger> newState = new ArrayList(currentState.size());

		for (Passenger passenger : currentState)
		{
			newState.add(passenger.clone());
		}

		return newState;
	}

	public Passenger getRaft()
	{
		for (Passenger p : getPassengers())
		{
			if (p.isRaft())
			{
				return p;
			}
		}
	}

}
