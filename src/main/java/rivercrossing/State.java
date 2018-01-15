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
		for (Passenger passenger : passengerList)
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

		newState.passengerList = newPassengerList;
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
		return toString(passengerList) + "Dist: " + getDistanceFromSink() + "; ";
	}

	public static String toString(List<Passenger> passengerList)
	{
		String result = "";
		for (Passenger p : passengerList)
		{
			result += p.getName() + " " + (p.hasCrossed() ? "R" : "L") + "; ";
		}
		return result;
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
			&& statesMatch(this.passengerList, ((State)otherState).getPassengers());
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
