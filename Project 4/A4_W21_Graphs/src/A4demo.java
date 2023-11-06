
public class A4demo {

	public static void main(String[] args) {
		Graph g = new DistanceGraph();
		String from = "Ajax";
		String to = "Toronto";
		
		System.out.println(g);
		System.out.println(Graphs.shortestPath(g, from, to));
		System.out.println(Graphs.MST(g));

	}

}
