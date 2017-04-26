import java.util.Scanner;
import java.io.File;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

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
	static long dijkstraMinHeap(int src, Vertex[] vertexArray) {
		long startTime, endTime;
		Vertex currentVertex;
		LinkedList<Vertex> currentList;
		Iterator<Vertex> iter;
		int currentNode, destination;
		startTime = System.currentTimeMillis();
		while(!minHeap.isEmpty()) {
			// Removing the top of the queue
			currentVertex = minHeap.peek();
			minHeap.remove(currentVertex);
			// If the top of the queue is at INF distance, we know nothing else is reachable, thus break
			if(Float.MAX_VALUE == currentVertex.distance)
				break;
			currentNode = currentVertex.id;
			currentList = adjList.get(currentNode);
			iter = currentList.iterator();
			while(iter.hasNext()) {
				currentVertex = iter.next();
				destination = currentVertex.id;
				if(vertexArray[destination].distance > vertexArray[currentNode].distance + currentVertex.distance) {
					// Using vertexArray aka "pointers" to help with adding and removing from the heap.
					minHeap.remove(vertexArray[destination]);
					vertexArray[destination].setDistance(vertexArray[currentNode].distance + currentVertex.distance);
					minHeap.add(vertexArray[destination]);
				}
			}
		}
		endTime = System.currentTimeMillis();
		//print(vertexArray);
		return (endTime - startTime);
	}

	// Slower Dijkstra's algorithm using distanceArray
	static long dijkstraArray(int src) {
		long startTime, endTime;
		LinkedList<Vertex> currentList;
		HashSet<Integer> visited = new HashSet<Integer>();
		Iterator<Vertex> iter;
		int currentNode, destination;
		Vertex currentVertex;
		startTime = System.currentTimeMillis();
		while(distanceArray.length != visited.size()) {
			currentNode = extractMin(distanceArray, visited);
			// If the shortest distance from source is INF, nothing else is reachable, thus break
			if(Float.MAX_VALUE == distanceArray[currentNode])
				break;
			visited.add(currentNode);
			currentList = adjList.get(currentNode);
			iter = currentList.iterator();
			while(iter.hasNext()) {
				currentVertex = iter.next();
				destination = currentVertex.id;
				if(distanceArray[destination] > distanceArray[currentNode] + currentVertex.distance) {
					distanceArray[destination] = distanceArray[currentNode] + currentVertex.distance;
				}
			}
		}
		endTime = System.currentTimeMillis();
		//print(distanceArray);
		return (endTime - startTime);
	}

	// Helper method to extract the index of the vertex with the current min distance from source
	private static int extractMin(float[] distanceArray,
			HashSet<Integer> visited) {
		int minIndex = 0;
		float minDistance = Float.MAX_VALUE;
		for(int i = 0; i < distanceArray.length; i++) {
			if(distanceArray[i] <= minDistance && !visited.contains(i)) {
				minIndex = i;
				minDistance = distanceArray[i];
			}
		}
		return minIndex;
	}

	// Main testing method
	public static boolean Run(int src, String filename) {
		File file = new File(filename);
		try {
			Scanner input = new Scanner(file);
			// Reading the number of vertices and edges
			int noVert = input.nextInt();
			if(src >= noVert || src < 0) {
				System.out.println("Invalid starting node");
				input.close();
				return false;
			}
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
			System.out.printf("%d\t%d\n", dijkstraMinHeap(src, vertexArray), dijkstraArray(src));
			input.close();
			return true;
		}
		catch (IOException e) {
			System.out.println("Error opening file!");
			return false;
		}
	}

	static void generateDenseGraph(int size) {
		int start, end;
		float weight;
		Random rand = new Random();
		try {
			PrintWriter fileWriter = new PrintWriter("randomDenseGraph.txt", "UTF-8");
			fileWriter.println(size);
			fileWriter.println(size*(size-1)/2);
			for(int i = 0; i < size; i++) {
				for(int j = i + 1; j < size; j++){
					weight = rand.nextFloat();
					fileWriter.printf("%d %d %.2f\n", i, j, weight);
				}
			}
			fileWriter.close();
		}
		catch (IOException e) {
			System.out.println("Error writing to file!");
		}
	}


static void generateSparseGraph(int size) {
		int start, end;
		int adj[][] = new int[size][size];
		float weight;
		Random rand = new Random();
		int edges = rand.nextInt(size * (size - 1) / 2);
		try {
			PrintWriter fileWriter = new PrintWriter("randomSparseGraph.txt", "UTF-8");
			fileWriter.println(size);
			fileWriter.println(edges);
			for(int i = 0; i < edges; i++) {
				start = rand.nextInt(size);
				end = rand.nextInt(size);
				while(start == end) {
					end = rand.nextInt(size);
				}
				if(adj[start][end] == 1 || adj[end][start] == 1)
					continue;
				weight = rand.nextFloat();
				fileWriter.printf("%d %d %.2f\n", start, end, weight);
				adj[start][end] = 1;
				adj[end][start] = 1;
			}
			fileWriter.close();
		}
		catch (IOException e) {
			System.out.println("Error writing to file!");
		}
	}
	public static void main(String args[]) {
		// Testing functions go here
		System.out.println("Dense graphs");
		System.out.printf("MinHeap\tArray\n");
		for(int size = 2; size < Math.pow(2,10); size *= 2) {
			// Generate a very dense graph for testing purposes
			generateDenseGraph(size);
			Run(0, "randomDenseGraph.txt");
		}
		System.out.println("Sparse graphs");
		System.out.printf("MinHeap\tArray\n");
		for(int size = 2; size < Math.pow(2,10); size *= 2) {
			// Generate a very dense graph for testing purposes
			generateSparseGraph(size);
			Run(0, "randomSparseGraph.txt");
		}
	}
}
