package graph;

import java.util.Iterator;

public abstract class Node implements Iterable<Node>
{
	public abstract Iterator<Node> iterator();
	public abstract boolean isSink();
}
