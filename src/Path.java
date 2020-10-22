/*
 * Stores path to `base` as a linked list, ie Path(Vertex1) > Path(Vertex2) > Path(Vertex3)
 */
public class Path {
	// Vertex that this Path object refers to
	private Vertex vertex = null;
	// Vertex of the last Path object in this linked list
	// should always be a hospital
	private Vertex base = null;
	// next item in the linked list
	private Path next = null;
	// length of this linked list
	private int length = 1;
	
	public Path(Vertex v) {
		this.vertex = v;
	}
	
	public Path(Vertex v, Path next) {
		this.vertex = v;
		this.next = next;
		
		setNext(next);
	}
	
	// create a new Path(v) pointing to p
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
