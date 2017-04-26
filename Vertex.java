// Helper class to represent objects we will put into the queue
public class Vertex {
	int id;
	float distance;

	public Vertex(int id) {
		this.id = id;
		distance= Float.MAX_VALUE;
	}

	public Vertex(int id, float distance) {
		this.id = id;
		this.distance = distance;
	}

	void setDistance(float distance) {
		this.distance = distance;
	}
}
