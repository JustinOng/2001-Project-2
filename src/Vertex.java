import java.util.*;

public class Vertex {
	private int id;
	private List<Vertex> neighbors = new ArrayList<Vertex>();
	private boolean isHospital = false;
	
	private Path path;
	
	private int visits = 0; 
	
	public Vertex(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void visit() {
		visits++;
	}
	
	public int getVisits() {
		return visits;
	}
	
	public void addNeighbor(Vertex v) {
		neighbors.add(v);
	}
	
	public List<Vertex> getNeighbors() {
		return neighbors;
	}
	
	public Path getPath() {
		return path;
	}
	
	public void setPath(Path path) {
		this.path = path;
	}
	
	public void setIsHospital(boolean isHospital) {
		this.isHospital = isHospital;
	}
	
	public boolean isHospital() {
		return isHospital;
	}
	
	public String toString() {		
		StringJoiner sj = new StringJoiner("-", String.format("Vertex id=%d ", id), "");
		
		Path p = path;
		
		while(p != null) {
			sj.add(Integer.toString(p.getVertex().id));
			p = p.getNext();
		}
		
		return sj.toString();
	}
}
