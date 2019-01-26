package rivercrossing;

import graph.Node;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class State extends Node
{
	protected List<Passenger> passengerList;
	protected Class<? extends Rules> rulesClass;

	public State(Class<? extends Rules> rulesClass)
	{
		this.rulesClass = rulesClass;
	}

	public Iterator<Node> iterator()
	{
		try
		{
			Rules newStateIterator = rulesClass.newInstance();
			newStateIterator.setState(this);
			return newStateIterator;
		}
		catch (InstantiationException | IllegalAccessException e)
		{
			throw new IllegalStateException(e);
		}
	}

	public boolean isSink()
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

	public List<Passenger> getPassengers()
	{
		return passengerList;
	}

	public void setPassengers(List<Passenger> passengers)
	{
		passengerList = passengers;
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

	public State clone()
	{
		State newState = new State(rulesClass);
		List<Passenger> newPassengerList = new ArrayList(getPassengers().size());

		for (Passenger passenger : getPassengers())
		{
			newPassengerList.add(passenger.clone());
		}

		newState.setPassengers(newPassengerList);
		return newState;
	}

	public void cross(List<Passenger> loadedRaft)
	{
		for (Passenger traveler : loadedRaft)
		{
			getPassenger(traveler.getName()).cross();
		}
	}

	public String toString()
	{
		return toString(getPassengers());
	}

	public static String toString(List<Passenger> passengers)
	{
		String leftSide = "";
		String rightSide = "";
		Passenger raft = null;

		for (Passenger p : passengers)
		{
			if (p.isRaft())
			{
				// save raft for later
				raft = p;
			}
			else
			{
				// add to left or right string
				String name = p.getName();
				if (p.hasCrossed())
				{
					leftSide += spaces(name.length() + 1);
					rightSide += name + " ";
				}
				else
				{
					leftSide += name + " ";
					rightSide += spaces(name.length() + 1);
				}
			}
		}

		// add the raft to the appropriate side
		String raftName = raft.getName();
		if (raft.hasCrossed())
		{
			leftSide += spaces(raftName.length());
			rightSide = raftName + " " + rightSide;
		}
		else
		{
			leftSide += raftName;
			rightSide = spaces(raftName.length() + 1) + rightSide;
		}

		// put it all together
		String result = leftSide + "  /~~~/  " + rightSide;

		return result;
	}

	private static String spaces(int n)
	{
		String result = "";
		for (int i=0; i < n; ++i)
		{
			result += " ";
		}
		return result;
	}

	/**
	 * @throws IllegalArgumentException if previousState doesn't contain the same Passengers
	 **/
	public String diff(State previousState)
	{
		List<Passenger> passengers = diffList(previousState);
		String result = "";
		for (Passenger p : passengers)
		{
			result += p.getName() + "; ";
		}
		return result;
	}

	/**
	 * @throws IllegalArgumentException if previousState doesn't contain the same Passengers
	 **/
	public String diffWithoutRaft(State previousState)
	{
		List<Passenger> passengers = diffList(previousState);
		String result = "";
		for (Passenger p : passengers)
		{
			if (!p.isRaft())
			{
				result += p.getName() + "; ";
			}
		}
		return result;
	}

	/**
	 * @throws IllegalArgumentException if previousState doesn't contain the same Passengers
	 **/
	public List<Passenger> diffList(State previousState)
	{
		if (getPassengers().size() != previousState.getPassengers().size())
		{
			throw new IllegalArgumentException("different number of passengers");
		}

		List<Passenger> moved = new ArrayList<>(getPassengers().size());

		for (Passenger ghost : previousState.getPassengers())
		{
			Passenger present = getPassenger(ghost.getName());
			if (present.hasCrossed() != ghost.hasCrossed())
			{
				moved.add(present);
			}
		}

		return moved;
	}

	public int hashCode()
	{
		return getPassengers().hashCode();
	}

	public boolean equals(Object otherState)
	{
		if (!(otherState instanceof State))
		{
			throw new IllegalArgumentException();
		}

		return otherState != null
			&& statesMatch(getPassengers(), ((State)otherState).getPassengers());
	}

	private boolean statesMatch(List<Passenger> stateA, List<Passenger> stateB)
	{
		if (stateA.size() != stateB.size())
		{
			throw new IllegalArgumentException();
		}

		for (int i=0; i < stateA.size(); ++i)
		{
			Passenger pA = stateA.get(i);
			Passenger pB = stateB.get(i);
			if (!pA.matches(pB))
			{
				return false;
			}
		}
		return true;
	}
}
