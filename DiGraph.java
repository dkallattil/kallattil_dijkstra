package kallattil_dijkstra;

import java.util.*;

public class DiGraph implements DiGraph_Interface {
	
	List<Node> nodes = new ArrayList<Node>();
	List<Edge> edges = new ArrayList<Edge>();
	Map<String, Node> nodeMap = new HashMap<String, Node>();
	
	
	int edgeCount;
	
	public DiGraph ( ) { 
		//Default constructor for testing
	}

	@Override
	public boolean addNode(long idNum, String label) {
		Node node = containsNode(idNum, label); 
		if(node == null) { 
			nodes.add(new Node(idNum, label));
			nodeMap.put(label, new Node(idNum, label));
			return true;
		} else {
			return false;
		}
	}
	
	public Node containsNode(long idNum, String label) {
		Iterator<Node> nodeIterator = nodes.iterator();
		Node contains;	
		while(nodeIterator.hasNext()) { 
			contains = nodeIterator.next();
			if(contains.getId() == idNum) { 
				return contains;
			}
			if(contains.getLabel() == label) { 
				return contains;
			}
		}
		return null; 
	}
	
	public Node containsNode(String label) {
		Iterator<Node> nodeIterator = nodes.iterator();
		Node contains;	
		while(nodeIterator.hasNext()) { 
			contains = nodeIterator.next();
			if(contains.getLabel() == label) { 
				return contains;
			}
		}
		return null; 
	}

	@Override
	public boolean addEdge(long idNum, String sLabel, String dLabel, long weight, String eLabel) {
		Node source = containsNode(sLabel);
		Node dest = containsNode(dLabel);
		if(source == null) { 
			return false;
		}
		if(dest == null) { 
			return false;
		}
		Iterator<Node> nodeIterator = nodes.iterator();
		Node next = null;
		while(nodeIterator.hasNext()) { 
			next = nodeIterator.next();
			if(next.containsEdge(idNum) != null) { 
				return false;
			}
		}
		boolean bool = source.addEdge(idNum, dest, weight, eLabel);
		if(bool == true) { 
			edgeCount++;
		}
		return bool;
		
	}
	
	@Override
	public boolean delNode(String label) {
		Node node = containsNode(label);
		if(node != null) { 
			
			int edgeCounter = node.edges.size();
			for(int i = 0; i < edgeCounter; i++) { 
				Edge edge = node.edges.get(i);
				Node dest = edge.getDest();
				dest.inCount--;
			}
			
			edgeCount = edgeCount - node.edges.size();
			Iterator<Node> nodeIterator = nodes.iterator();
			Node next = null;
			while(nodeIterator.hasNext()) { 
				next = nodeIterator.next();
				Edge remove = next.containsEdge(node);
				if(remove != null) { 
					edgeCount--;
					next.edges.remove(remove);
				}
			}
			
			nodes.remove(node);
			nodeMap.remove(node);
			return true;
		}
		
		
		
		return false;
	}
	
	@Override
	public boolean delEdge(String sLabel, String dLabel) {
		Node source = containsNode(sLabel);
		Node dest = containsNode(dLabel);
		
		if(source == null) {
			return false;
		}
		if(dest == null) { 
			return false;
		}
		
		boolean bool = source.delEdge(dest);
		
		if(bool == true) { 
			edgeCount--;
		}
		return bool;
	}

	@Override
	public long numNodes() {
		return nodes.size();
	}
	
	@Override
	public long numEdges() {
		return edgeCount;
	}

	@Override
	public String[] topoSort() {
		Iterator<Node> nodeIterator = nodes.iterator();
		List<String> strings = new ArrayList<String>();
		int graphSize = nodes.size();
		while(nodeIterator.hasNext()) { 
			Node node = nodeIterator.next();
			if(node.inCount == 0) { 
				strings.add(node.getLabel());
				this.delNode(node.getLabel());
				nodeIterator = nodes.iterator(); 
			}
		}
		if(strings.size() < graphSize) { 
			return null;
		}
		String[] topo = new String[strings.size()];
		for (int i = 0; i < strings.size(); i++) { 
			topo[i] = strings.get(i);
		}
		
		
		return topo;
	}

	@Override
	public ShortestPathInfo[] shortestPath(String label) {
		MinBinHeap queue = new MinBinHeap();
		ShortestPathInfo[] paths = new ShortestPathInfo[nodes.size()];
		
		Node startNode = containsNode(label);
		startNode.setDist(0);
		
		EntryPair start = new EntryPair(startNode.getLabel(), (int)startNode.getDist());
		queue.insert(start);
		
		while(queue.size() > 0) 
		{ 
			EntryPair pair = queue.getMin();
			queue.delMin();
		
			Node min = containsNode(pair.getValue());

			if(min.getHandled()) { continue; } 
			
			min.setHandled(true);
			for(Edge e :  min.edges) 
			{ 
				Node dest = e.getDest();
				if(dest.getDist() > min.getDist() + e.getWeight()) 
				{ 
					dest.setDist(min.getDist() + e.getWeight());
					EntryPair pairToAdd = new EntryPair(dest.getLabel(), (int)dest.getDist());
					queue.insert(pairToAdd);
				}
			}
		
		}
		int i = 0;
		for(Node node : nodes) { 
			if(node.getDist() < Long.MAX_VALUE) { 
				ShortestPathInfo path = new ShortestPathInfo(node.getLabel(), node.getDist());
				paths[i] = path;
			} else { 
				ShortestPathInfo path = new ShortestPathInfo(node.getLabel(), -1);
				paths[i] = path;
			}
			i++;
		}	
		
		return paths;
	}

}