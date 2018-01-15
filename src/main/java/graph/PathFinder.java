package graph;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class PathFinder
{
	protected Node startNode;
	protected Stack<Node> currentPath;
	protected Stack<Node> shortestPath;
	protected Map<Node, Node> nodeCache;

	public PathFinder(Node startNode)
	{
		this.startNode = startNode;
		nodeCache = new HashMap();
	}

	public Stack<Node> findShortestPath()
	{
		currentPath = new Stack();
		shortestPath = null;
		continueFindingShortestPath(startNode);
		return shortestPath;
	}

	private void continueFindingShortestPath(Node nextNode)
	{
		if (hasAKnownPath(nextNode))
		{
			evaluatePath(nextNode);
		}
		else if (!tooDeep() && !inLoop(nextNode))
		{
			exploreChildren(nextNode);
		}
	}

	private void exploreChildren(Node nextNode)
	{
		currentPath.push(nextNode);
		for (Node child : nextNode)
		{
			Node persistentNode = cacheNode(child);
			continueFindingShortestPath(persistentNode);
		}
		currentPath.pop();
	}

	private boolean hasAKnownPath(Node nextNode)
	{
		if (nextNode.getDistanceFromSink() != null)
		{
			System.out.println("DEBUG: known path: " + nextNode.getDistanceFromSink());
		}
		return nextNode.isSink() || nextNode.getDistanceFromSink() != null;
	}

	private void evaluatePath(Node nextNode)
	{
		if (nextNode.isSink())
		{
			nextNode.setDistanceFromSink(0);
		}

		if (nodeIsCloser(nextNode))
		{
			currentPath.push(nextNode);
			reDistancePath();
			if (currentPathIsShortestKnown())
			{
				shortestPath = (Stack<Node>) currentPath.clone();
			}
			currentPath.pop();
		}
	}

	private void reDistancePath()
	{
		int currentNodeDistance = getCurrentNode().getDistanceFromSink();
		int distance = currentPath.size() + currentNodeDistance - 1;
		for (Node node : currentPath)
		{
			if (node.getDistanceFromSink() == null
					|| node.getDistanceFromSink() > distance)
			{
				node.setDistanceFromSink(distance--);
			}
			else
			{
				return;
			}
		}
	}

	private boolean nodeIsCloser(Node nextNode)
	{
		Integer nextDistance = nextNode.getDistanceFromSink();
		Integer currentDistance;
		try
		{
			currentDistance = getCurrentNode().getDistanceFromSink();
		}
		catch (EmptyStackException e)
		{
			currentDistance = null;
		}

		return nextDistance != null
			&& (currentDistance == null
					|| currentDistance > nextDistance + 1);
	}

	private Node getCurrentNode()
	{
		return currentPath.peek();
	}

	private boolean tooDeep()
	{
		return shortestPath != null && currentPath.size() >= shortestPath.size();
	}

	private boolean currentPathIsShortestKnown()
	{
		return shortestPath == null
			|| startNode.getDistanceFromSink() < shortestPath.size() -1;
	}

	private boolean inLoop(Node nextNode)
	{
		for (Node node : currentPath)
		{
			if (node.equals(nextNode))
			{
				System.out.println("DEBUG: looped");
				return true;
			}
		}

		return false;
	}

	private Node cacheNode(Node newNode)
	{
		if (nodeCache.containsKey(newNode))
		{
			System.out.println("TRACE: cached node " + nodeCache.get(newNode));
			return nodeCache.get(newNode);
		}
		else
		{
			System.out.println("TRACE: not cached " + newNode);
			nodeCache.put(newNode, newNode);
			return newNode;
		}
	}
}
