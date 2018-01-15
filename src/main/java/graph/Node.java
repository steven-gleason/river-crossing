package graph;

import java.util.Iterator;

public abstract class Node implements Iterable<Node>
{
	protected Integer distanceFromSink;
	protected Node nextNode;

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

	public Node getNextNode()
	{
		return nextNode;
	}

	public void setNextNode(Node nextNode)
	{
		this.nextNode = nextNode;
	}
}
