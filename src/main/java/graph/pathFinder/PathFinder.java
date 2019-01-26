package graph.pathFinder;

import graph.Node;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class PathFinder
{
	protected PathFinderNode startNode;
	protected Stack<PathFinderNode> currentPath;
	protected Map<Node, PathFinderNode> nodeCache;

	public PathFinder(Node startNode)
	{
		this.startNode = new PathFinderNode(startNode);
		nodeCache = new HashMap<>();
	}

	public Stack<Node> findShortestPath()
	{
		currentPath = new Stack<>();
		continueFindingShortestPath(startNode);
		return pathAsStack();
	}

	private void continueFindingShortestPath(PathFinderNode nextNode)
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

	private void exploreChildren(PathFinderNode nextNode)
	{
		currentPath.push(nextNode);
		for (Node child : nextNode)
		{
			PathFinderNode persistentNode = cacheNode(child);
			continueFindingShortestPath(persistentNode);
		}
		currentPath.pop();
	}

	private boolean hasAKnownPath(PathFinderNode nextNode)
	{
		if (nextNode.getDistanceFromSink() != null)
		{
			//System.out.println("DEBUG: known path: " + nextNode.getDistanceFromSink());
		}
		return nextNode.isSink() || nextNode.getDistanceFromSink() != null;
	}

	private void evaluatePath(PathFinderNode nextNode)
	{
		if (nextNode.isSink())
		{
			nextNode.setDistanceFromSink(0);
		}

		if (nodeIsCloser(nextNode))
		{
			currentPath.push(nextNode);
			reDistancePath();
			currentPath.pop();
		}
	}

	private void reDistancePath()
	{
		Stack<PathFinderNode> reversePath = new Stack();

		PathFinderNode node = currentPath.pop();
		reversePath.push(node);

		while (!currentPath.empty())
		{
			if (nodeIsCloser(node))
			{
				getCurrentNode().setDistanceFromSink(node.getDistanceFromSink() + 1);
				getCurrentNode().setNextNode(node);
				node = currentPath.pop();
				reversePath.push(node);
			}
			else
			{
				//System.out.println("DEBUG: reDistance already contains shorter path");
				break;
			}
		}

		while (!reversePath.isEmpty())
		{
			currentPath.push(reversePath.pop());
		}
	}

	private boolean nodeIsCloser(PathFinderNode nextNode)
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

	private PathFinderNode getCurrentNode()
	{
		return currentPath.peek();
	}

	private boolean tooDeep()
	{
		Integer shortestPathSize = startNode.getDistanceFromSink();
		return shortestPathSize != null && currentPath.size() >= shortestPathSize;
	}

	private boolean inLoop(Node nextNode)
	{
		for (Node node : currentPath)
		{
			if (node.equals(nextNode))
			{
				//System.out.println("DEBUG: looped");
				return true;
			}
		}

		return false;
	}

	private PathFinderNode cacheNode(Node keyNode)
	{
		PathFinderNode cachedNode = nodeCache.get(keyNode);
		if (cachedNode != null)
		{
			//System.out.println("TRACE: cached node " + nodeCache.get(keyNode));
		}
		else
		{
			//System.out.println("TRACE: not cached " + keyNode);
			cachedNode = new PathFinderNode(keyNode);
			nodeCache.put(keyNode, cachedNode);
		}
		return cachedNode;
	}

	private Stack<Node> pathAsStack()
	{
		Stack<Node> path = new Stack();
		buildPath(startNode, path);
		return path;
	}

	private void buildPath(PathFinderNode currentNode, Stack<Node> path)
	{
		if (currentNode != null)
		{
			path.push(currentNode.getUnderlyingNode());
			buildPath(currentNode.getNextNode(), path);
		}
	}
}
