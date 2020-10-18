import java.util.*;

final class WorkUnit {
	private Vertex v;
	private int newDistance;
	private Vertex hospital;

	public WorkUnit(Vertex v, int newDistance, Vertex hospital) {
		this.v = v;
		this.newDistance = newDistance;
		this.hospital = hospital;
	}

	public Vertex getVertex() {
		return v;
	}

	public int getNewDistance() {
		return newDistance;
	}

	public Vertex getHospital() {
		return hospital;
	}
}

public class SearchAlgorithm {
	private Map<Integer, Vertex> vertexes = new HashMap<Integer, Vertex>();
	private ArrayDeque<WorkUnit> workQueue = new ArrayDeque<WorkUnit>();

	public Vertex getVertex(int id) {
		Vertex v;

		if (!vertexes.containsKey(id)) {
			v = new Vertex(id);
			vertexes.put(id, v);
		} else {
			v = vertexes.get(id);
		}

		return v;
	}
	
	public Collection<Vertex> getVertexes() {
		return vertexes.values();
	}

	public void createFromEdge(int src, int dst) {
		Vertex srcV = getVertex(src);
		Vertex dstV = getVertex(dst);

		srcV.addNeighbor(dstV);
		dstV.addNeighbor(srcV);
	}
	
	public void assignHospitalsRandomly() {
		Random random = new Random();
		random.setSeed(0);
		
		for (Vertex v : vertexes.values()) {
			if (random.nextInt(50) == 0) {
				v.setIsHospital(true);
			}
		}
	}

	public void setHospital(int id) {
		vertexes.get(id).setIsHospital(true);
	}

	public void search() {
		for (Vertex v : vertexes.values()) {
			if (!v.isHospital())
				continue;
			
			workQueue.add(new WorkUnit(v, 0, v));
		}
		
		System.out.println(String.format("Starting search with %d hospitals", workQueue.size()));
		
		while (!workQueue.isEmpty()) {
			tick(workQueue.removeFirst());
		}
	}

	private void tick(WorkUnit work) {
		Vertex v = work.getVertex();
		int newDistance = work.getNewDistance();
		Vertex hospital = work.getHospital();

		if (v.getDistance() <= newDistance)
			return;

		v.visit();
		v.setDistance(newDistance);
		v.setNearestHospital(hospital);

		newDistance++;
		for (Vertex connected : v.getNeighbors()) {
			if (connected.isHospital() || newDistance > connected.getDistance())
				continue;

			workQueue.add(new WorkUnit(connected, newDistance, hospital));
		}
	}
}
