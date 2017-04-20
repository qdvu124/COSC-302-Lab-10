import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.ArrayList;

public class DijkstraLab11 {
	
	PriorityQueue<Vertex> minHeap;
	Vertex vertexArray[];
	ArrayList<LinkedList<Vertex>> adjList;
	
	// Comparator to sort the vertices in the heap
	Comparator<Vertex> comparator = new Comparator<Vertex>() {
		public int compare(Vertex a, Vertex b) {
			return a.distance- b.distance;
		}
	};
	
	// Constructor for the class
	public DijkstraLab11(int src, int size, int graph[][]) {
		vertexArray = new Vertex[size];
		adjList = new ArrayList<LinkedList<Vertex>>(size);
		minHeap = new PriorityQueue<Vertex>(size, comparator);
		Vertex currentVertex;
		// Adding vertices into the heap and the array. Starting vertex has distance 0, the rest set to INF
		for(int i = 0; i < size; i++) {
			currentVertex = new Vertex(i);
			if(i == src) {
				currentVertex.setDistance(0);
				minHeap.add(currentVertex);
				vertexArray[i] = currentVertex;
				continue;
			}
			minHeap.add(currentVertex);
			vertexArray[i] = currentVertex;
		}
		initializeAdjList(size, graph);
	}
	
	// Initialize the adjacency list
	void initializeAdjList(int size, int graph[][]) {
		Vertex currentVertex;
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				if(0 != graph[i][j]) {
					currentVertex = new Vertex(j, graph[i][j]);
					adjList.get(i).add(currentVertex);
				}
			}
		}
	}
	
	// Main testing method
	public static boolean Run(int s, String filename) {
		return true;
	}
	
	public static void main(String args[]) {
		// Testing functions go here
	}
}
