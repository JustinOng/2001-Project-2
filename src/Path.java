public class Path {
	private Vertex vertex = null;
	private Vertex base = null;
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
			this.base = this.vertex;
			return;
		}
		
		this.next = next;
		this.base = this.next.base;
		this.length += next.length;
	}
	
	public Vertex getVertex() {
		return vertex;
	}
	
	public Vertex getBase() {
		return base;
	}
	
	public Path getNext() {
		return next;
	}
	
	public int getLength() {
		return length;
	}
}
