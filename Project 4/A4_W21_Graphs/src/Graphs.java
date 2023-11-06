
public class Graphs {
	public static Path shortestPath(Graph graph, String from, String to) {
		final Path path = new Path();
		//DUMMY CODE (STUB) TODO
		for (Edge e : graph.adj(new Vertex("Guelph"))) {
			path.getList().add(e);
		}
		return path;
	}

	public static Graph MST(Graph graph) {
		//DUMMY CODE (STUB) TODO
		return graph;
	}
}
