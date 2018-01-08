import java.util.*;
import java.io.*;

public class Kruskal {
	public static void main(String[] args) {
		ArrayList<Edge> graphEdges = new ArrayList<Edge>();		//edge list, not adjacency list
		graphEdges.add(new Edge(3, 5, 2));
		graphEdges.add(new Edge(6, 7, 5));
		graphEdges.add(new Edge(3, 4, 6));
		graphEdges.add(new Edge(4, 8, 7));
		graphEdges.add(new Edge(1, 2, 9));
		graphEdges.add(new Edge(4, 5, 11));
		graphEdges.add(new Edge(1, 6, 14));
		graphEdges.add(new Edge(1, 7, 15));
		graphEdges.add(new Edge(5, 8, 16));
		graphEdges.add(new Edge(3, 6, 18));
		graphEdges.add(new Edge(3, 8, 19));
		graphEdges.add(new Edge(7, 5, 20));
		graphEdges.add(new Edge(2, 3, 24));
		graphEdges.add(new Edge(7, 8, 44));		//Edges created in almost sorted order, only the last 2 are switched but this is unnecessary as edges are sorted in the algorithm
		graphEdges.add(new Edge(6, 5, 30));

		int nodeCount = 8;		//how many nodes. NODE COUNT MUST BE ENTERED MANUALLY. No error handling between nodeCount and graphEdges

		Kruskal graph = new Kruskal();	//CAREFUL: nodeCount must be correct. No error checking between nodeCount & graphEdges to see how many nodes actually exist
		graph.kruskalMST(graphEdges, nodeCount);
	}

	public void kruskalMST(ArrayList<Edge> graphEdges, int nodeCount){
		String outputMessage="";

		Collections.sort(graphEdges);		//sort edges with smallest weight 1st
		ArrayList<Edge> mstEdges = new ArrayList<Edge>();	//list of edges included in the Minimum spanning tree (initially empty)

		DisjointSet nodeSet = new DisjointSet(nodeCount+1);		//Initialize singleton sets for each node in graph. (nodeCount +1) to account for arrays indexing from 0

		for(int i=0; i<graphEdges.size() && mstEdges.size()<(nodeCount-1); i++){		//loop over all edges. Start @ 1 (ignore 0th as placeholder). Also early termination when number of edges=(number of nodes-1)
			Edge currentEdge = graphEdges.get(i);
			int root1 = nodeSet.find(currentEdge.getVertex1());		//Find root of 1 vertex of the edge
			int root2 = nodeSet.find(currentEdge.getVertex2());
			outputMessage+="find("+currentEdge.getVertex1()+") returns "+root1+", find("+currentEdge.getVertex2()+") returns "+root2;		//just print, keep on same line for union message
			String unionMessage=",\tNo union performed\n";		//assume no union is to be performed, changed later if a union DOES happen
			if(root1 != root2){			//if roots are in different sets the DO NOT create a cycle
				mstEdges.add(currentEdge);		//add the edge to the graph
				nodeSet.union(root1, root2);	//union the sets
				unionMessage=",\tUnion("+root1+", "+root2+") done\n";		//change what's printed if a union IS performed
			}
			outputMessage+=unionMessage;
		}

		outputMessage+="\nFinal Minimum Spanning Tree ("+mstEdges.size()+" edges)\n";
		int mstTotalEdgeWeight=0;
		for(Edge edge: mstEdges){
			outputMessage+=edge +"\n";		//print each edge
			mstTotalEdgeWeight += edge.getWeight();
		}
		outputMessage+="\nTotal weight of all edges in MST = "+mstTotalEdgeWeight;

		System.out.println(outputMessage);

		try (PrintWriter outputFile = new PrintWriter( new File("06outputMST.txt") ); ){
			outputFile.println(outputMessage);
			System.out.println("\nOpen \"06outputMST.txt\" for backup copy of answers");
		} catch (FileNotFoundException e) {
			System.out.println("Error! Couldn't create file");
		}
	}
}


class Edge implements Comparable<Edge>{
	private int vertex1;	//an edge has 2 vertices & a weight
	private int vertex2;
	private int weight;

	public Edge(int vertex1, int vertex2, int weight){
		this.vertex1=vertex1;
		this.vertex2=vertex2;
		this.weight=weight;
	}

	public int getVertex1(){
		return vertex1;
	}

	public int getVertex2(){
		return vertex2;
	}

	public int getWeight(){
		return weight;
	}

	@Override
	public int compareTo(Edge otherEdge) {				//Compare based on edge weight (for sorting)
		return this.getWeight() - otherEdge.getWeight();
	}

	@Override
	public String toString() {
		return "Edge ("+getVertex1()+", "+getVertex2()+") weight="+getWeight();
	}
}


// DisjointSet class
//
// CONSTRUCTION: with int representing initial number of sets
//
// ******************PUBLIC OPERATIONS*********************
// void union(root1, root2) --> Merge two sets
// int find(x)              --> Return set containing x
// ******************ERRORS********************************
// No error checking is performed
// http://users.cis.fiu.edu/~weiss/dsaajava3/code/DisjSets.java

/**
 * Disjoint set class, using union by rank and path compression
 * Elements in the set are numbered starting at 0
 * @author Mark Allen Weiss
 */
class DisjointSet{
	private int[] set;		//the disjoint set as an array

	public int[] getSet(){		//mostly debugging method to print array
		return set;
	}

	/**
	 * Construct the disjoint sets object.
	 * @param numElements the initial number of disjoint sets.
	 */
	public DisjointSet(int numElements) {		//constructor creates singleton sets
		set = new int [numElements];
		for(int i = 0; i < set.length; i++){		//initialize to -1 so the trees have nothing in them
			set[i] = -1;
		}
	}

	/**
	 * Union two disjoint sets using the height heuristic.
	 * For simplicity, we assume root1 and root2 are distinct
	 * and represent set names.
	 * @param root1 the root of set 1.
	 * @param root2 the root of set 2.
	 */
	public void union(int root1, int root2) {
		if(set[root2] < set[root1]){		// root2 is deeper
			set[root1] = root2;		// Make root2 new root
		}
		else {
			if(set[root1] == set[root2]){
				set[root1]--;			// Update height if same
			}
			set[root2] = root1;		// Make root1 new root
		}
	}

	/**
	 * Perform a find with path compression.
	 * Error checks omitted again for simplicity.
	 * @param x the element being searched for.
	 * @return the set containing x.
	 */
	public int find(int x) {
		if(set[x] < 0){		//If tree is a root, return its index
			return x;
		}
		int next = x;		
		while(set[next] > 0){		//Loop until we find a root
			next=set[next];
		}
		return next;
	}
	
}