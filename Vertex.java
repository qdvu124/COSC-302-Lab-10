// Helper class to represent objects we will put into the queue
public class Vertex {
	int id;
	int distance;
	
	public Vertex(int id) {
		this.id = id;
		distance= Integer.MAX_VALUE;
	}
	
	public Vertex(int id, int distance) {
		this.id = id;
		this.distance = distance;
	}
	
	void setDistance(int distance) {
		this.distance = distance;
	}
}
