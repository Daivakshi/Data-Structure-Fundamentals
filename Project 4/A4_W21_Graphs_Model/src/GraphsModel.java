import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * @author Andriy
 * A utility class containing two graph algorithms: path finding and a minimum spanning tree
 */
public class GraphsModel {
	/**
	 * Implements a shortest path algorithm based on Dijkstra's
	 * stops as soon as the path to the destination is computed
	 * (does NOT compute the shortest path tree)
	 * @param graph input graph
	 * @param from starting vertex
	 * @param to destination
	 * @return Path containing the list of edges to follow 
	 */
	public static Path shortestPath(Graph graph, String from, String to) {
		final Path path = new Path();
		Map <String, Double> distTo = new HashMap <>();
		Map <String, Edge> edgeTo = new HashMap <>();
		PriorityQueue <String> verticesPQ = new PriorityQueue<>(new Comparator <String>() {
			@Override
			public int compare(String v1, String v2) {
				return distTo.get(v1)>distTo.get(v2) ? 1 : -1;
			}
		});

		for (String str: graph.vertices()) {
			distTo.put(str, Double.POSITIVE_INFINITY);
		}
		distTo.put(from, 0.0);

		for (verticesPQ.add(from); !verticesPQ.isEmpty();) {
			String currentV = verticesPQ.remove();
			if (currentV.equals(to)) {
				//System.out.println("FOUND!");
				if (!from.equals(to)) //if the path is not empty
					do {
						path.getList().add(0, edgeTo.get(currentV));
						currentV = edgeTo.get(currentV).other(new Vertex(currentV)).name;
					}while (!currentV.equals(from));
				return path;
			}

			for (Edge edge : graph.adj.get(currentV)) {
				if (distTo.get(currentV) + edge.weight() < distTo.get(edge.other(new Vertex(currentV)).name)){
					distTo.put(edge.other(new Vertex(currentV)).name, distTo.get(currentV) + edge.weight());
					edgeTo.put(edge.other(new Vertex(currentV)).name, edge);
					verticesPQ.add(edge.other(new Vertex(currentV)).name);
				}
			}
		}
		return path;
	}

	/**
	 * Implements a [lazy] Prim's MST algorithm
	 * @param graph
	 * @return minimum spanning tree
	 */
	public static Graph MST(Graph graph) {
		if (graph == null) return null;
		MSTGraph mst = new MSTGraph();
		if (graph.V == 0) return mst;
		PriorityQueue <Edge> edgesPQ = new PriorityQueue<>(new Comparator <Edge>() {
			@Override
			public int compare(Edge e1, Edge e2) {
				return e1.weight()>e2.weight() ? 1 : -1;
			}
		});

		String currentV = graph.vertices().iterator().next();
		mst.addVertex(currentV); //add the "first" vertex to the result
		//add all the incident edges to the PQ
		for (Edge edge : graph.adj.get(currentV)) {
			edgesPQ.add(edge);
		}

		//remove edges from the PQ and add to the result when appropriate
		//until the graph is a spanning graph (it will be a tree, and will be a minimum too)
		while (!edgesPQ.isEmpty() && mst.V < graph.V) {
			Edge candidateEdge = edgesPQ.remove();
			String edgeFrom = candidateEdge.either().name;
			String edgeTo = candidateEdge.other(candidateEdge.either()).name;
			if (mst.adj.containsKey(edgeFrom) ^ mst.adj.containsKey(edgeTo)) {
				//vertex that wasn't in the tree, but is about to be added
				currentV = mst.adj.containsKey(edgeFrom) ? edgeTo : edgeFrom;
				//add the edge to the tree
				mst.addEdge(edgeFrom, edgeTo, candidateEdge.weight());
				//add all the edges incident to currentV to the PQ
				for (Edge edge : graph.adj.get(currentV)) {
					edgesPQ.add(edge);
				}
			}
		}
		return mst;
	}

	
	static class MSTGraph extends Graph{
		public MSTGraph() {
			this.adj = new HashMap<String, List<Edge>>();
		}
		
		public void addVertex(String vertex) {
			if (!adj.containsKey(vertex)) {
				adj.put(vertex, new ArrayList<Edge>());
				V++;
			}
		}

		public void addEdge(String vertex1, String vertex2, double distance) {
			if (!adj.containsKey(vertex1)) {adj.put(vertex1, new ArrayList<Edge>()); V++;}
			if (!adj.containsKey(vertex2)) {adj.put(vertex2, new ArrayList<Edge>()); V++;}
			adj.get(vertex1).add(new Edge(new Vertex(vertex1), new Vertex(vertex2), distance));
			adj.get(vertex2).add(new Edge(new Vertex(vertex2), new Vertex(vertex1), distance));
			E++;
		}
	}
}
