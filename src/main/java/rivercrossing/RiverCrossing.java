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
			System.out.println("Game Solved in " + getPathLength() + " moves");
			printSolution();
		}
	}

	public void printSolution()
	{
		State previousState = null;
		int moveCount = 1;

		for (Node state : shortestPath)
		{
			if (previousState == null)
			{
				System.out.println("Initial State: " + state);
			}
			else
			{
				String move = ((State) state).diffWithoutRaft(previousState);
				System.out.println("Move " + moveCount++ + ": " + move);
				System.out.println(" State: " + state);
			}
			previousState = (State) state;
		}
	}

	public int getPathLength()
	{
		return shortestPath.size() - 1;
	}

}
