package graph.pathFinder;

import graph.Node;
import java.util.Iterator;

public class PathFinderNode extends Node
{
	private Node baseNode;

	protected Integer distanceFromSink;
	protected PathFinderNode nextNode;

	public PathFinderNode(Node node)
	{
		baseNode = node;
	}

	@Override
	public Iterator<Node> iterator()
	{
		return baseNode.iterator();
	}

	@Override
	public boolean isSink()
	{
		return baseNode.isSink();
	}

	public Integer getDistanceFromSink()
	{
		return distanceFromSink;
	}

	public void setDistanceFromSink(Integer distance)
	{
		this.distanceFromSink = distance;
	}

	public PathFinderNode getNextNode()
	{
		return nextNode;
	}

	public void setNextNode(PathFinderNode nextNode)
	{
		this.nextNode = nextNode;
	}

	public Node getUnderlyingNode()
	{
		return baseNode;
	}
}
