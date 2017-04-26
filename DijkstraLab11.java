import java.util.Scanner;
import java.io.File;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.FileNotFoundException;

public class DijkstraLab11 {

	static PriorityQueue<Vertex> minHeap;
	static float distanceArray[];
	static ArrayList<LinkedList<Vertex>> adjList;

	// Comparator to sort the vertices in the heap
	static Comparator<Vertex> comparator = new Comparator<Vertex>() {
		public int compare(Vertex a, Vertex b) {
			return (int) a.distance - (int) b.distance;
		}
	};

	// Helper method to initialize minHeap and vertexArray
	static void initialize(int src, int noVert, Vertex[] vertexArray) {
		Vertex currentVertex;
		for(int i = 0; i < noVert; i++) {
			if (i == src) {
				currentVertex = new Vertex(i, 0);
				minHeap.add(currentVertex);
				distanceArray[i] = 0;
			}
			else {
				currentVertex = new Vertex(i);
				minHeap.add(currentVertex);
				distanceArray[i] = Float.MAX_VALUE;
			}
			vertexArray[i] = currentVertex; adjList.add(i, new LinkedList<Vertex>());
		}
	}
	
	// Helper method to display the results
	static void print(Vertex vertexArray[]) {
		for(int i = 0; i < vertexArray.length; i++)
			System.out.printf("%d %.2f\n", vertexArray[i].id, vertexArray[i].distance);
	}
	
	//
	static void print(float distanceArray[]) {
		for(int i = 0; i < distanceArray.length; i++)
			System.out.printf("%d %.2f\n", i, distanceArray[i]);
	}


	// Dijkstra's algorithm using minHeap
	static void dijkstraMinHeap(int src, Vertex[] vertexArray) {
		long startTime, endTime;
		Vertex currentVertex;
		LinkedList<Vertex> currentList;
		Iterator<Vertex> iter;
		int currentNode, destination;
		startTime = System.currentTimeMillis();
		while(!minHeap.isEmpty()) {
			currentVertex = minHeap.peek();
			minHeap.remove(currentVertex);
			if(Float.MAX_VALUE == currentVertex.distance)
				break;
			currentNode = currentVertex.id;
			currentList = adjList.get(currentNode);
			iter = currentList.iterator();
			while(iter.hasNext()) {
				currentVertex = iter.next();
				destination = currentVertex.id;
				if(vertexArray[destination].distance > vertexArray[currentNode].distance + currentVertex.distance) {
					minHeap.remove(vertexArray[destination]);
					vertexArray[destination].setDistance(vertexArray[currentNode].distance + currentVertex.distance);
					minHeap.add(vertexArray[destination]);
				}
			}
		}
		endTime = System.currentTimeMillis();
		print(vertexArray);
		System.out.printf("Time taken: %d ms\n", endTime - startTime);
	}

	// Slower Dijkstra's algorithm using distanceArray
	static void dijkstraArray(int src) {
		long startTime, endTime;
		HashSet<Integer> visited = new HashSet<Integer>();
		startTime = System.currentTimeMillis();
		visited.add(src);
		endTime = System.currentTimeMillis();
		print(distanceArray);
		System.out.printf("Time taken: %d ms\n", endTime - startTime);

	}
	// Main testing method
	public static boolean Run(int src, String filename) {
		File file = new File(filename);
		try {
			Scanner input = new Scanner(file);
			// Reading the number of vertices and edges
			int noVert = input.nextInt();
			int noEdges = input.nextInt();
			int start, end;
			Vertex currentVertex;
			float weight;
			adjList = new ArrayList<LinkedList<Vertex>>(noVert);
			minHeap = new PriorityQueue<Vertex>(noVert, comparator);
			distanceArray = new float[noVert];
			// Helper array with "pointers" to node objects, since otherwise we would not be able to manipulate heap
			Vertex[] vertexArray = new Vertex[noVert];
			initialize(src, noVert, vertexArray);
			for(int i = 0; i < noEdges; i++) {
				start = input.nextInt();
				end = input.nextInt();
				weight = input.nextFloat();
				currentVertex = new Vertex(end, weight);
				adjList.get(start).add(currentVertex);
				// Goes both ways
				currentVertex = new Vertex(start, weight);
				adjList.get(end).add(currentVertex);

			}
			dijkstraMinHeap(src, vertexArray);
			dijkstraArray(src);
			input.close();
			return true;
		}
		catch (FileNotFoundException e) {
			System.out.println("Error opening file");
			return false;
		}
	}

	public static void main(String args[]) {
		// Testing functions go here
		Run(0, "src/classGraph.txt");
	}
}
