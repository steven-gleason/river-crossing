package rivercrossing;

import java.lang.IllegalArgumentException;
import java.lang.IllegalStateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public abstract class RiverCrossing
{
	protected State currentState;
	protected Stack<State> history;

	abstract public boolean isValidRaft(List<Passenger> loadedRaft);
	abstract public boolean banksAreValid();

	public RiverCrossing()
	{
		currentState = new State();
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

	protected List<Passenger> getPassengers()
	{
		return currentState.getPassengers();
	}

	/**
	* General use move engine.
	* Assumes 1 or 2 passengers per raft; only 1 raft.
	**/
	public void nextMove()
	{
		System.out.println("nm: " + currentState);
		Passenger raft = currentState.getRaft();
		boolean direction = raft.hasCrossed();

		if (isSolved())
		{
			endGame();
		}

		for (Passenger p1 : getPassengers())
		{
			if (p1.hasCrossed() == direction && !p1.isRaft())
			{
				List<Passenger> loadedRaft = new ArrayList(3);
				loadedRaft.add(raft);
				loadedRaft.add(p1);
				tryMove(loadedRaft);
				for (Passenger p2 : getPassengers())
				{
					if (p2.hasCrossed() == direction
							&& !p1.getName().equals(p2.getName())
							&& !p2.isRaft())
					{
						loadedRaft.add(p2);
						tryMove(loadedRaft);
						loadedRaft.remove(loadedRaft.size() -1);
					}
				}
			}
		}
	}

	private void tryMove(List<Passenger> loadedRaft)
	{
		System.out.println("try: " + State.toString(loadedRaft));
		if (isValidRaft(loadedRaft))
		{
			System.out.println("raft: valid");
			cross(loadedRaft);
			if (banksAreValid() && !hasLooped())
			{
				System.out.println("banks: valid; hasLooped: nope");
				nextMove();
			}
			System.out.println("revert");
		  revert();
			System.out.println("state: " + currentState);
		}
	}

	public void endGame()
	{
		System.out.println("Game Solved");
		System.out.println(currentState);
		while (!history.isEmpty())
		{
			revert();
			System.out.println(currentState);
		}

		System.exit(0);
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
		for (State state : history)
		{
			if (currentState.matches(state))
			{
				System.out.println("has looped");
				return true;
			}
		}
		return false;
	}

	public void cross(List<Passenger> loadedRaft)
	{
		history.push(currentState);
		currentState = currentState.clone();
		currentState.cross(loadedRaft);
	}

}
