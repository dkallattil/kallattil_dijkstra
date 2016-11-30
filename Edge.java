package kallattil_dijkstra;

public class Edge {
	private long weight; 
	private long idNum; 
	private String label; 
	private Node dest; 
	
	
	public Edge(long weight, long idNum, String label, Node destination) { 
		this.weight = weight; 
		this.idNum = idNum; 
		this.label = label; 
		this.dest = destination;
	}
	
	public long getWeight() {
		return weight; 
	}
	
	public long getId() { 
		return idNum;
	}
	
	public String getLabel() { 
		return label;
	}
	
	public Node getDest() { 
		return dest; 
	}
}
