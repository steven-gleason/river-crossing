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
	protected Stack<State> shortestPath;

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
		System.out.println("current path: " + history.size());
		Passenger raft = currentState.getRaft();
		boolean direction = raft.hasCrossed();

		if (!pathIsShorter())
		{
			System.out.println("DEBUG: exceeded shortest path");
			return;
		}

		if (isSolved())
		{
			System.out.println("DEBUG: found shorter path");
			shortestPath = (Stack<State>) history.clone();
			shortestPath.push(currentState);
			System.out.println("DEBUG: **BEGIN SOLUTION**");
			printSolution();
			System.out.println("DEBUG: **END SOLUTION **");
			return;
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
		if (shortestPath == null)
		{
			System.out.println("no solution");
		}
		else
		{
			System.out.println("Game Solved in " + shortestPath.size() + " moves");
			printSolution();
		}
	}

	public void printSolution()
	{
		for (State state : shortestPath)
		{
			System.out.println(state);
		}
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

	protected boolean pathIsShorter()
	{
		return shortestPath == null || history.size()+1 < shortestPath.size();
		// +1 because history does not contain the current state
	}

}
