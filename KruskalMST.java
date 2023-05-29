package assignment7;

import java.util.*;

//Class to represent an edge
class Edge {
 int src, dest, weight;

 Edge(int src, int dest, int weight) {
     this.src = src;
     this.dest = dest;
     this.weight = weight;
 }
}

//Class to represent a subset element
class Subset {
 int parent, rank;
}

class KruskalMST {
 int V; // Number of vertices
 List<Edge> edges; // List of edges

 KruskalMST(int V) {
     this.V = V;
     edges = new ArrayList<>();
 }

 // Main function to find MST using Kruskal's algorithm
 void kruskals() {
     List<Edge> result = new ArrayList<>(); // Stores the resultant MST

     // Step 1: Sort the edges in non-decreasing order of their weight
     Collections.sort(edges, Comparator.comparingInt(e -> e.weight));

     // Create V subsets with single elements
     Subset[] subsets = new Subset[V];
     for (int i = 0; i < V; i++) {
         subsets[i] = new Subset();
         subsets[i].parent = i;
         subsets[i].rank = 0;
     }

     int edgeIndex = 0; // Index used to iterate through the sorted edges

     // Step 2: Iterate through all edges in the sorted order
     while (result.size() < V - 1) {
         Edge nextEdge = edges.get(edgeIndex++);

         int x = findRoot(subsets, nextEdge.src);
         int y = findRoot(subsets, nextEdge.dest);

         // If including this edge does not form a cycle, include it in the result
         if (x != y) {
             result.add(nextEdge);
             union(subsets, x, y);
         }
     }

     // Print the MST
     System.out.println("Minimum Spanning Tree:");
     for (Edge edge : result) {
         System.out.println(edge.src + " -- " + edge.dest + "   Weight: " + edge.weight);
     }
 }

 // Function to find the root of the set that element i belongs to
 int findRoot(Subset[] subsets, int i) {
     if (subsets[i].parent != i) {
         // Path compression: Make the parent of i as the root
         subsets[i].parent = findRoot(subsets, subsets[i].parent);
     }
     return subsets[i].parent;
 }

 // Function to unite two disjoint sets
 void union(Subset[] subsets, int x, int y) {
     int xRoot = findRoot(subsets, x);
     int yRoot = findRoot(subsets, y);

     // Union by rank: Attach the smaller rank tree under the root of the higher rank tree
     if (subsets[xRoot].rank < subsets[yRoot].rank) {
         subsets[xRoot].parent = yRoot;
     } else if (subsets[xRoot].rank > subsets[yRoot].rank) {
         subsets[yRoot].parent = xRoot;
     } else {
         subsets[yRoot].parent = xRoot;
         subsets[xRoot].rank++;
     }
 }

 public static void main(String[] args) {
     int V = 4; // Number of vertices
     KruskalMST graph = new KruskalMST(V);

     // Add edges
     graph.edges.add(new Edge(0, 1, 10));
     graph.edges.add(new Edge(0, 2, 6));
     graph.edges.add(new Edge(0, 3, 5));
     graph.edges.add(new Edge(1, 3, 15));
     graph.edges.add(new Edge(2, 3, 4));

     // Find the minimum spanning tree
     graph.kruskals();
 }
}

