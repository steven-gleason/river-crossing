package rivercrossing;

import graph.Node;
import graph.PathFinder;
import java.util.List;

public class RiverCrossing
{
	protected State initialState;
	protected List<Node> shortestPath;

	public RiverCrossing(Class<? extends Rules> rulesClass)
	{
		try
		{
			initialState = rulesClass.newInstance().getInitialState();
		}
		catch (InstantiationException | IllegalAccessException e)
		{
			throw new IllegalArgumentException(e);
		}
		shortestPath = null;
	}
 
	public void solve()
	{
		PathFinder pathFinder = new PathFinder(initialState);
		shortestPath = pathFinder.findShortestPath();
	}

	public void endGame()
	{
		if (shortestPath == null)
		{
			System.out.println("no solution");
		}
		else
		{
			System.out.println("Game Solved in " + (shortestPath.size() - 1) + " moves");
			printSolution();
		}
	}

	public void printSolution()
	{
		for (Node state : shortestPath)
		{
			System.out.println(state);
		}
	}

}
