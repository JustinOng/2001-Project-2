import java.util.*;

final class WorkUnit {
	private Vertex v;
	private Path path;

	public WorkUnit(Vertex v, Path path) {
		this.v = v;
		this.path = path;
	}

	public Vertex getVertex() {
		return v;
	}

	public Path getPath() {
		return path;
	}
}

public class SearchAlgorithm {
	private Map<Integer, Vertex> vertexes = new HashMap<Integer, Vertex>();
	private ArrayDeque<WorkUnit> workQueue = new ArrayDeque<WorkUnit>();
	private int numPaths = 0;
	
	public SearchAlgorithm(int numPaths) {
		this.numPaths = numPaths;
	}

	public Vertex getVertex(int id) {
		Vertex v;

		if (!vertexes.containsKey(id)) {
			v = new Vertex(id, numPaths);
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

	public void setHospital(int id) throws Exception {
		Vertex v = vertexes.get(id);
		if (v == null) {
			throw new Exception(String.format("Cannot set %d as hospital: does not exist!", id));
		}
		
		v.setIsHospital(true);
	}

	public void search() {
		for (Vertex v : vertexes.values()) {
			if (!v.isHospital())
				continue;
			
			workQueue.add(new WorkUnit(v, null));
		}
		
		System.out.println(String.format("Starting search with %d hospitals", workQueue.size()));
		
		while (!workQueue.isEmpty()) {
			tick(workQueue.removeFirst());
		}
	}

	private void tick(WorkUnit work) {
		Vertex v = work.getVertex();
		
		if (v.foundAllPaths()) return;
		
		Path path = Path.extend(v, work.getPath());

		if (!v.addPath(path)) return;

		for (Vertex connected : v.getNeighbors()) {
			workQueue.add(new WorkUnit(connected, path));
		}
	}
}
