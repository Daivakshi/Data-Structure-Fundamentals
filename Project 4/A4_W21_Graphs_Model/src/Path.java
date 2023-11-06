import java.util.ArrayList;
import java.util.List;

/** 
 * Describes a path between two vertices, defined as a sequence of vertices
 * connected with edges, as well as the length of such path 
 * @author Andriy
 *
 */
public class Path {
	private double length;
	private List <Edge> path;

	public Path() {
		this.path = new ArrayList<>();
	}

	public double getLength() {
		length = 0;
		for (Edge e : path)
			length += e.weight();
		return length;
	}

	public List<Edge> getList() {
		return path;
	}

	public String toString(){
		String s = "";
		for (Edge e : path)
			s += e + " ";
		s += "\nTotal: " + getLength() + " km";
		return s;
	}


}
