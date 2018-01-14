package rivercrossing;

import graph.Node;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public abstract class Rules implements Iterator<Node>
{
	protected State currentState;
	protected Iterator<Passenger> p1Iterator;
	protected Iterator<Passenger> p2Iterator;
	protected Passenger p1;
	protected State next;

	abstract public State getInitialState();
	abstract protected boolean isValidRaft(List<Passenger> loadedRaft);
	abstract protected boolean banksAreValid(State newState);

	public void setState(State currentState)
	{
		this.currentState = currentState;
		p1Iterator = getPassengers().iterator();
		p2Iterator = null;
		next = null;
	}

	public boolean hasNext()
	{
		if (next == null)
		{
			findNext();
		}
		return next != null;
	}

	public State next()
	{
		if (!hasNext())
		{
			throw new NoSuchElementException();
		}

		State returnMe = next;
		next = null;
		return returnMe;
	}

	public void remove()
	{
		throw new UnsupportedOperationException();
	}

	protected List<Passenger> getPassengers()
	{
		return currentState.getPassengers();
	}

	/**
	* General use move engine.
	* Assumes 1 or 2 passengers per raft; only 1 raft.
	**/
	protected void findNext()
	{
		System.out.println("nm: " + currentState);

		// finish using p1
		while (p2Iterator != null && p2Iterator.hasNext())
		{
			State newMove = tryMove(p1, p2Iterator.next());
			if (newMove != null)
			{
				next = newMove;
				return;
			}
		}

		// try next p1 options
		while (p1Iterator.hasNext())
		{
			p1 = p1Iterator.next();
			State newMove = tryMove(p1, null);
			if (newMove != null)
			{
				next = newMove;
				return;
			}
			p2Iterator = getPassengers().iterator();
			while (p2Iterator.hasNext())
			{
				Passenger p2 = p2Iterator.next();
				newMove = tryMove(p1, p2);
				if (newMove != null)
				{
					next = newMove;
					return;
				}
			}
		}
	}

	private State tryMove(Passenger p1, Passenger p2)
	{
		Passenger raft = currentState.getRaft();
		State newMove = null;

		if (isSensiblePassengerList(p1, p2, raft))
		{
			List<Passenger> loadedRaft = new ArrayList(3);
			loadedRaft.add(raft);
			loadedRaft.add(p1);
			if (p2 != null)
			{
				loadedRaft.add(p2);
			}
			newMove = tryMove(loadedRaft);
		}
		return newMove;
	}

	private boolean isSensiblePassengerList(Passenger p1, Passenger p2, Passenger raft)
	{
		boolean direction = raft.hasCrossed();
		return isSensiblePassenger(p1, direction)
			&& (p2 == null || (isSensiblePassenger(p2, direction)
					&& !p1.getName().equals(p2.getName())));
	}

	private boolean isSensiblePassenger(Passenger passenger, boolean direction)
	{
		return passenger.hasCrossed() == direction && !passenger.isRaft();
	}

	private State tryMove(List<Passenger> loadedRaft)
	{
		System.out.println("try: " + State.toString(loadedRaft));
		State newState = null;
		if (isValidRaft(loadedRaft))
		{
			System.out.println("raft: valid");
			newState = cross(loadedRaft);
			if (banksAreValid(newState))
			{
				System.out.println("banks: valid");
			}
			else
			{
				System.out.println("invalid move");
				System.out.println("state: " + currentState);
				newState = null;
			}
		}
		return newState;
	}

	protected State cross(List<Passenger> loadedRaft)
	{
		State newState = currentState.clone();
		newState.cross(loadedRaft);
		return newState;
	}

}
