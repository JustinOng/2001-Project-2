public class Path {
	private Vertex vertex = null;
	private Path next = null;
	private int length = 1;
	
	public Path(Vertex v) {
		this.vertex = v;
	}
	
	public Path(Vertex v, Path next) {
		this.vertex = v;
		this.next = next;
		
		setNext(next);
	}
	
	public static Path extend(Vertex v, Path p) {
		return new Path(v, p);
	}
	
	public void setNext(Path next) {		
		if (this.next == null) {
			return;
		}
		
		this.next = next;
		this.length += next.length;
	}
	
	public Vertex getVertex() {
		return vertex;
	}
	
	public Path getNext() {
		return next;
	}
	
	public int getLength() {
		return length;
	}
}
