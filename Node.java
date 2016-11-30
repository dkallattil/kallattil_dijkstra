package kallattil_dijkstra;

import java.util.*;

public class Node {
	
	private long idNum; 
	private String label;
	public List<Edge> edges;
	public int inCount;
	private long distance;
	private boolean handled;	

	public Node(long idNum, String label) {
		this.idNum = idNum; 
		this.label = label; 
		edges = new ArrayList<Edge>();
		inCount = 0; 
		distance = Long.MAX_VALUE;
		handled = false;
	}
	
	public long getId() { 
		return idNum;
	}
	
	public boolean getHandled() { 
		return handled;
	}
	
	public void setHandled(boolean bool) { 
		this.handled = bool;
	}
	
	public String getLabel() { 
		return label;
	}
	
	public long getDist() { 
		return distance;
	}
	
	public void setDist(long distance) { 
		this.distance = distance;
	}
	
	public boolean addEdge(long idNum, Node dest, long weight, String eLabel) {
		Edge edge = containsEdge(idNum, dest);
		if(edge != null) { 
			return false;
		}
		else { 
			edges.add(new Edge(weight, idNum, eLabel, dest));
			dest.inCount++;
			return true; 
		} 
		
	}
	
	public Edge containsEdge(Node dest) { 
		Iterator<Edge> edgeIterator = edges.iterator();
		Edge edge = null;
		while(edgeIterator.hasNext()) { 
			edge = edgeIterator.next();
			if(edge.getDest() == dest) { 
				return edge;
			}
		}
		return null;
	}
	
	public Edge containsEdge(long idNum, Node dest) { 
		Iterator<Edge> edgeIterator = edges.iterator();
		Edge edge = null;
		while(edgeIterator.hasNext()) { 
			edge = edgeIterator.next(); 
			if(edge.getId() == idNum) { 
				return edge;
			}
			else if(edge.getDest() == dest) { 
				return edge;
			}
		}
		return null;
	}
	
	public Edge containsEdge(long idNum) { 
		Iterator<Edge> edgeIterator = edges.iterator();
		Edge edge = null;
		while(edgeIterator.hasNext()) { 
			edge = edgeIterator.next(); 
			if(edge.getId() == idNum) { 
				return edge;
			}
		}
		return null;
	}
	

	public boolean delEdge(Node dest) {
		Edge edge = containsEdge(dest);
		if(edge == null) { 
			return false;
		} else { 
			edges.remove(edge);
			dest.inCount--;
			return true;
		}
	}
	
	

}
