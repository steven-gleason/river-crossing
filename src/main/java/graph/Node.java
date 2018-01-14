package graph;

import java.util.Iterator;

public abstract class Node implements Iterable<Node>
{
	protected Integer distanceFromSink;

	public abstract Iterator<Node> iterator();
	public abstract boolean isSink();

	public Integer getDistanceFromSink()
	{
		return distanceFromSink;
	}

	public void setDistanceFromSink(Integer distance)
	{
		this.distanceFromSink = distance;
	}
}
