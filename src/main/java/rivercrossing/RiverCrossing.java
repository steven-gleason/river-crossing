package rivercrossing;

import java.lang.IllegalArgumentException;
import java.lang.IllegalStateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public abstract class RiverCrossing
{
	protected List<Passenger> currentState;
	protected Stack<List<Passenger>> history;

	abstract public boolean isValidRaft(List<Passenger> loadedRaft);
	abstract public boolean banksAreValid();

	public RiverCrossing()
	{
		currentState = new ArrayList();
		history = new Stack();
	}

	public boolean isSolved()
	{
		for (Passenger passenger : getPassengers())
		{
			if (!passenger.hasCrossed())
			{
				return false;
			}
		}
		return true;
	}

	/**
	* General use move engine.
	* Assumes 1 or 2 passengers per raft; only 1 raft.
	**/
	public void nextMove()
	{
		Passenger raft = getRaft();
		boolean direction = raft.hasCrossed();

		if (isSolved())
		{
			endGame();
		}

		for (Passenger p1 : getPassengers())
		{
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

	public void endGame()
	{
		System.out.println("Game Solved");
		System.out.println(currentStateString());
		while (!history.isEmpty())
		{
			revert();
			System.out.println(currentStateString());
		}

		System.exit(0);
	}

	public String currentStateString()
	{
		String result = "";
		for (Passenger p : getPassengers())
		{
			result += p.getName() + " " + (p.hasCrossed() ? "R" : "L") + "; ";
		}
		return result;
	}
	public List<Passenger> getPassengers()
	{
		return currentState;
	}

	public void revert()
	{
		currentState = history.pop();
	}

	public static boolean containsRaft(List<Passenger> loadedRaft)
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

		throw new IllegalStateException();
	}

	public Passenger getPassenger(String passengerName)
	{
		for (Passenger p : getPassengers())
		{
			if (p.getName().equals(passengerName))
			{
				return p;
			}
		}

		throw new IllegalArgumentException();
	}

}
