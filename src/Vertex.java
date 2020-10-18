import java.util.*;

public class Vertex {
	private int id;
	private List<Vertex> neighbors = new ArrayList<Vertex>();
	private boolean isHospital = false;
	
	private Vertex nearestHospital; 
	private int distance = Integer.MAX_VALUE;
	
	public Vertex(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void addNeighbor(Vertex v) {
		neighbors.add(v);
	}
	
	public List<Vertex> getNeighbors() {
		return neighbors;
	}
	
	public int getDistance() {
		return distance;
	}
	
	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	public Vertex getNearestHospital() {
		return nearestHospital;
	}
	
	public void setNearestHospital(Vertex v) {
		nearestHospital = v;
	}
	
	public void setIsHospital(boolean isHospital) {
		this.isHospital = isHospital;
	}
	
	public boolean isHospital() {
		return isHospital;
	}
}
