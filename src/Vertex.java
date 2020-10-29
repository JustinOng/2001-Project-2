import java.util.*;

public class Vertex {
	private int id;
	private List<Vertex> neighbors = new ArrayList<Vertex>();
	private boolean isHospital = false;
	
	private Path[] paths;
	private int numPaths = 0;
	
	public Vertex(int id, int maxPaths) {
		this.id = id;
		this.paths = new Path[maxPaths];
		this.numPaths = 0;
	}
	
	public int getId() {
		return id;
	}
	
	public void reset() {
		this.numPaths = 0;
	}
	
	public void addNeighbor(Vertex v) {
		neighbors.add(v);
	}
	
	public List<Vertex> getNeighbors() {
		return neighbors;
	}
	
	public boolean foundAllPaths() {
		return numPaths >= paths.length;
	}
	
	public boolean addPath(Path path) {
		for (int i = 0; i < numPaths; i++) {
			if (paths[i].getBase() == path.getBase()) return false;
		}
		
		this.paths[numPaths++] = path;
		return true;
	}
	
	public void setIsHospital(boolean isHospital) {
		this.isHospital = isHospital;
	}
	
	public boolean isHospital() {
		return isHospital;
	}
	
	public String toString() {		
		StringJoiner full_sj = new StringJoiner(", ", String.format("Vertex id=%d ", id), "");
		
		for (Path a : paths) {
			Path p = a;
			StringJoiner sj = new StringJoiner("-", "[", "]");
			
			while(p != null) {
				sj.add(Integer.toString(p.getVertex().id));
				p = p.getNext();
			}
			
			full_sj.add(sj.toString());
		}
		
		return full_sj.toString();
	}
}
