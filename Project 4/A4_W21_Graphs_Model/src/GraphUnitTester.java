import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GraphUnitTester {

	@Test (timeout = 1000)
	public void shortestPathOntario_1() {
		Graph g = new DistanceGraph2("Edges.csv");
		List <Edge> pathListAct = Graphs.shortestPath(g, "Aurora", "Guelph").getList();
		List <Edge> pathListModel = GraphsModel.shortestPath(g, "Aurora", "Guelph").getList();
		List <String> modelList = new ArrayList<>();
		List <String> actualList = new ArrayList<>();
		for (Edge edge : pathListAct) {
			String edgeFrom = edge.either().name;
			String edgeTo = edge.other(edge.either()).name;
			actualList.add(edgeFrom + "-" + edgeTo);
		}
		for (Edge edge : pathListModel) {
			String edgeFrom = edge.either().name;
			String edgeTo = edge.other(edge.either()).name;
			modelList.add(edgeFrom + "-" + edgeTo);
		}

		//System.out.println(actualList);
		//System.out.println(modelList);
		//System.out.println(Graphs.shortestPath(g, "Aurora", "Guelph").getLength());
		assertTrue("List of path edges different", actualList.equals(modelList));
		assertEquals("Path length different", Graphs.shortestPath(g, "Aurora", "Guelph").getLength(),
				GraphsModel.shortestPath(g, "Aurora", "Guelph").getLength(), 1.0);
	}

	@Test (timeout = 1000)
	public void shortestPathOntario_2() {
		Graph g = new DistanceGraph2("Edges.csv");
		List <Edge> pathListAct = Graphs.shortestPath(g, "Grimsby", "Alliston").getList();
		List <Edge> pathListModel = GraphsModel.shortestPath(g, "Grimsby", "Alliston").getList();
		List <String> modelList = new ArrayList<>();
		List <String> actualList = new ArrayList<>();
		for (Edge edge : pathListAct) {
			String edgeFrom = edge.either().name;
			String edgeTo = edge.other(edge.either()).name;
			actualList.add(edgeFrom + "-" + edgeTo);
		}
		for (Edge edge : pathListModel) {
			String edgeFrom = edge.either().name;
			String edgeTo = edge.other(edge.either()).name;
			modelList.add(edgeFrom + "-" + edgeTo);
		}

		//System.out.println(actualList);
		//System.out.println(modelList);
		assertTrue("List of path edges different", actualList.equals(modelList));
		assertEquals("Path length different", Graphs.shortestPath(g, "Aurora", "Guelph").getLength(),
				GraphsModel.shortestPath(g, "Aurora", "Guelph").getLength(), 1.0);
	}

	@Test (timeout = 1000)
	public void shortestPathSetSimple() {
		Graph g = new DistanceGraph2("Edges1.csv");
		List <Edge> pathListAct = Graphs.shortestPath(g, "0", "2").getList();
		List <Edge> pathListModel = GraphsModel.shortestPath(g, "0", "2").getList();
		Set <String> actualSet = new HashSet<>();
		Set <String> modelSet = new HashSet<>();
		for (Edge edge : pathListAct) {
			String edgeFrom = edge.either().name;
			String edgeTo = edge.other(edge.either()).name;
			if(edgeFrom.compareTo(edgeTo) < 0)
				actualSet.add(edgeFrom + "-" + edgeTo);
			else 
				actualSet.add(edgeTo + "-" + edgeFrom);
		}
		for (Edge edge : pathListModel) {
			String edgeFrom = edge.either().name;
			String edgeTo = edge.other(edge.either()).name;
			if(edgeFrom.compareTo(edgeTo) < 0)
				modelSet.add(edgeFrom + "-" + edgeTo);
			else 
				modelSet.add(edgeTo + "-" + edgeFrom);
		}
		
		//System.out.println(actualSet);
		//System.out.println(modelSet);
		assertTrue("List of path edges different", actualSet.equals(modelSet));
	}

	@Test (timeout = 1000)
	public void shortestPathListSimple() {
		Graph g = new DistanceGraph2("Edges1.csv");
		List <Edge> pathListAct = Graphs.shortestPath(g, "0", "2").getList();
		List <Edge> pathListModel = GraphsModel.shortestPath(g, "0", "2").getList();
		List <String> modelList = new ArrayList<>();
		List <String> actualList = new ArrayList<>();
		for (Edge edge : pathListAct) {
			String edgeFrom = edge.either().name;
			String edgeTo = edge.other(edge.either()).name;
			actualList.add(edgeFrom + "-" + edgeTo);
		}
		for (Edge edge : pathListModel) {
			String edgeFrom = edge.either().name;
			String edgeTo = edge.other(edge.either()).name;
			modelList.add(edgeFrom + "-" + edgeTo);
		}
		assertTrue("List of path edges different", actualList.equals(modelList));
	}

	@Test (timeout = 1000)
	public void shortestPathDistSimple() {
		Graph g = new DistanceGraph2("Edges1.csv");
		assertEquals("Path length 1 incorrect", Graphs.shortestPath(g, "0", "1").getLength(), 5.0, 0.1);
		assertEquals("Path length 2 incorrect", Graphs.shortestPath(g, "0", "2").getLength(), 14.0, 0.1);
		assertEquals("Path length 3 incorrect", Graphs.shortestPath(g, "0", "3").getLength(), 17.0, 0.1);
		assertEquals("Path length 4 incorrect", Graphs.shortestPath(g, "0", "4").getLength(), 9.0, 0.1);
		assertEquals("Path length 5 incorrect", Graphs.shortestPath(g, "0", "5").getLength(), 13.0, 0.1);
		assertEquals("Path length 6 incorrect", Graphs.shortestPath(g, "0", "6").getLength(), 25.0, 0.1);
		assertEquals("Path length 7 incorrect", Graphs.shortestPath(g, "0", "7").getLength(), 8.0, 0.1);
	}

	@Test (timeout = 1000)
	public void mstOntarioSet() {
		Graph mstExp = GraphsModel.MST(new DistanceGraph2("Edges.csv"));
		Set <String> edgeSetExp = new HashSet<>();
		for (String v : mstExp.vertices())
			for (Edge edge : mstExp.adj.get(v)) {
				String edgeFrom = edge.either().name;
				String edgeTo = edge.other(edge.either()).name;
				if(edgeFrom.compareTo(edgeTo) < 0)
					edgeSetExp.add(edgeFrom + "-" + edgeTo);
				else 
					edgeSetExp.add(edgeTo + "-" + edgeFrom);
			}

		Graph mstActual = Graphs.MST(new DistanceGraph2("Edges.csv"));
		Set <String> edgeSetActual = new HashSet<>();
		for (String v : mstActual.vertices())
			for (Edge edge : mstActual.adj.get(v)) {
				String edgeFrom = edge.either().name;
				String edgeTo = edge.other(edge.either()).name;
				if(edgeFrom.compareTo(edgeTo) < 0)
					edgeSetActual.add(edgeFrom + "-" + edgeTo);
				else 
					edgeSetActual.add(edgeTo + "-" + edgeFrom);
			}
		//System.out.println(edgeSetActual);
		//System.out.println(mstActual);
		assertTrue("Set of MST edges different", edgeSetActual.equals(edgeSetExp));
	}

	@Test (timeout = 1000)
	public void mstOntarioWeight() {
		Graph g = new DistanceGraph2("Edges.csv");
		double weight = graphWeight(Graphs.MST(g));
		//759.4 for undirected, 1518.8 for bidirectional
		//System.out.println(weight);
		if (weight > 1000) 
			assertEquals("MST weight incorrect", weight, 1518.8, 1.0);
		else
			assertEquals("MST weight incorrect", weight, 759.4, 1.0);
	}

	@Test (timeout = 1000)
	public void mstSetSimple1() {
		Graph mstExp = GraphsModel.MST(new DistanceGraph2("Edges1.csv"));
		Set <String> edgeSetExp = new HashSet<>();
		for (String v : mstExp.vertices())
			for (Edge edge : mstExp.adj.get(v)) {
				String edgeFrom = edge.either().name;
				String edgeTo = edge.other(edge.either()).name;
				if(edgeFrom.compareTo(edgeTo) < 0)
					edgeSetExp.add(edgeFrom + "-" + edgeTo);
				else 
					edgeSetExp.add(edgeTo + "-" + edgeFrom);
			}

		Graph mstActual = Graphs.MST(new DistanceGraph2("Edges1.csv"));
		Set <String> edgeSetActual = new HashSet<>();
		for (String v : mstActual.vertices())
			for (Edge edge : mstActual.adj.get(v)) {
				String edgeFrom = edge.either().name;
				String edgeTo = edge.other(edge.either()).name;
				if(edgeFrom.compareTo(edgeTo) < 0)
					edgeSetActual.add(edgeFrom + "-" + edgeTo);
				else 
					edgeSetActual.add(edgeTo + "-" + edgeFrom);
			}
		assertTrue("Set of MST edges different", edgeSetActual.equals(edgeSetExp));
	}

	@Test (timeout = 1000)
	public void mstSetSimple2() {
		Graph mstExp = GraphsModel.MST(new DistanceGraph2("Edges2.csv"));
		Set <String> edgeSetExp = new HashSet<>();
		for (String v : mstExp.vertices())
			for (Edge edge : mstExp.adj.get(v)) {
				String edgeFrom = edge.either().name;
				String edgeTo = edge.other(edge.either()).name;
				if(edgeFrom.compareTo(edgeTo) < 0)
					edgeSetExp.add(edgeFrom + "-" + edgeTo);
				else 
					edgeSetExp.add(edgeTo + "-" + edgeFrom);
			}

		Graph mstActual = Graphs.MST(new DistanceGraph2("Edges2.csv"));
		Set <String> edgeSetActual = new HashSet<>();
		for (String v : mstActual.vertices())
			for (Edge edge : mstActual.adj.get(v)) {
				String edgeFrom = edge.either().name;
				String edgeTo = edge.other(edge.either()).name;
				if(edgeFrom.compareTo(edgeTo) < 0)
					edgeSetActual.add(edgeFrom + "-" + edgeTo);
				else 
					edgeSetActual.add(edgeTo + "-" + edgeFrom);
			}
		assertTrue("Set of MST edges different", edgeSetActual.equals(edgeSetExp));
	}

	@Test (timeout = 1000)
	public void mstWeightSimple() {
		Graph g = new DistanceGraph2("Edges2.csv");
		double weight = graphWeight(Graphs.MST(g));
		//1.81 for undirected, 3.62 for bidirectional
		if (weight > 2.00) 
			assertEquals("MST weight incorrect", weight, 3.62, 0.01);
		else
			assertEquals("MST weight incorrect", weight, 1.81, 0.01);
	}

	private static double graphWeight(Graph g) {
		double weight = 0;
		for (String v : g.vertices())
			for (Edge edge : g.adj.get(v)) {
				weight += edge.weight();
			}
		return weight;
	}
}
