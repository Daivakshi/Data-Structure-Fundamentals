
public class A4demo {

	public static void main(String[] args) {
		Graph g = new DistanceGraph2("Edges1.csv");
		g = new DistanceGraph2("Edges2.csv");
		g = new DistanceGraph2("Edges.csv");
		String from = "Markham";
		String to = "Guelph";
//		String from = "5";
//		String to = "6";
		
		//System.out.println(g);
		System.out.println("Graphs.shortestPath(" + from + ", " + to + "): \n" + Graphs.shortestPath(g, from, to));
		System.out.println(Graphs.MST(g));

	}

}
