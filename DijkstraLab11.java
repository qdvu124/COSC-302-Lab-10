import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.ArrayList;

public class DijkstraLab11 {

	PriorityQueue<Vertex> minHeap;
	float vertexArray[];
	// We need two adj
	ArrayList<LinkedList<Vertex>> adjList;

	// Comparator to sort the vertices in the heap
	Comparator<Vertex> comparator = new Comparator<Vertex>() {
		public int compare(Vertex a, Vertex b) {
			return a.distance- b.distance;
		}
	};

	// Helper method to initialize minHeap and vertexArray
	public static initialize(int src, int size) {
		Vertex currentVertex;
		for(int i = 0; i < noVert; i++) {
			if (i == src) {
				currentVertex = new Vertex(i, 0);
				minHeap.add(currentVertex);
				vertexArray[i] = 0;
			}
			else {
				currentVertex = new Vertex(i);
				minHeap.add(currentVertex);
				vertexArray[i] = FLOAT.MAX_VALUE;
			}
		}
	}

	// Dijkstra's algorithm using minHeap

	static void dijkstraMinHeap(int src) {
		long startTime, endTime;
		HashSet<Integer> visited = new HashSet<Integer>();
		Vertex currentVertex;
		LinkedList<Vertex> currentList;
		Iterator<Vertex> iter;
		int currentNode;
		startTime = System.currentTimeMillis();
		while(!minHeap.isEmpty()) {
			currentVertex = minHeap.peek();
			minHeap.remove(currentVertex);
			if(visited.contains(currentVertex.id))
				continue;
			currentNode = currentVertex.id;
			currentList = adjList.get(currentNode);
			iter = currentList.iterator();
			while(iter.hasNext()) {
				currentVe
			}
		}
		endTime = System.currentTimeMillis();
	}

	// Slower Dijkstra's algorithm using vertexArray
	static void dijkstraArray(int src) {

	}
	// Main testing method
	public static boolean Run(int src, String filename) {
		File file = new File(filename);
		Scanner input = new Scanner(file);
		// Reading the number of vertices and edges
		int noVert = input.nextInt();
		int noEdges = input.nextInt();
		int start, end;
		Vertex currentVertex;
		float weight;
		adjList = new ArrayList<LinkedList<Vertex>>(noVert);
		minHeap = new PriorityQueue<Vertex>(noVert, comparator);
		vertexArray = new Vertex[noVert];
		initialize(src, noVert);
		for(int i = 0; i < noEdges; i++) {
			start = input.nextInt();
			end = input.nextInt();
			weight = input.nextFloat();
			currentVertex = new Vertex(end, weight);
			adjList.get(start).add(currentVertex);
		}
		dijkstraMinHeap(src);
		dijkstraArray(src);
		return true;
	}

	public static void main(String args[]) {
		// Testing functions go here
	}
}
