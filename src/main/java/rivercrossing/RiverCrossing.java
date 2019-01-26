package rivercrossing;

import graph.Node;
import graph.pathFinder.PathFinder;
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

		for (Node node : shortestPath)
		{
			State state = (State) node;
			if (previousState == null)
			{
				System.out.println("Initial State:");
			}
			else
			{
				String move = state.diffWithoutRaft(previousState);
				System.out.println("Move " + moveCount++ + ": " + move);
			}
			System.out.println("* " + state);
			previousState = state;
		}
	}

	public int getPathLength()
	{
		return shortestPath.size() - 1;
	}

}
