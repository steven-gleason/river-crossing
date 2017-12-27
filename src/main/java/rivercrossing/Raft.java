package rivercrossing;

public class Raft extends Passenger
{

	public Raft()
	{
		super("raft");
	}

	public boolean isRaft()
	{
		return true;
	}

	public Raft clone()
	{
		Raft clone = new Raft();
		clone.crossed = this.crossed;
		return clone;
	}
}
