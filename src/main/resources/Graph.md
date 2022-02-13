###MST
MST for a graph can be determined using Prim algorithm and Kruskal algorithm.Both are greedy algorithm. 
1. Prim algorithm similar to Djikstra algortithm for shortest path between two nodes chooses a new node every time based on minimum cost. Prim algorithm treats all the nodes in a intree as a single node and considers the non tree node with minimum distance from any of intree node. While the Djikstra algorithm chooses to minimumize distance to any non tree node from the original src node.
2. Kruskal algorithm uses union find data structure which articulates disjoint set data structure and help in optimizing union function which allows two disjoint set to collapse in single disjoint set and find operation which allows us to distinguish two nodes their respective disjoint sets. Each node starts with their own disjoint set. Then edges are sorted in order of their cost. Then at each step two distinct disjoint set of each edge's two node   collapse into one single disjoint set.

###Depth First Search
#####performance
DFS time complexity is O(V+E)??The for loop for visiting edge for each vertex have a cumulative sum of at most E iterations since it will iterate over all the node's neighbors for each node. 

####Cycle detection:
In case of cycle detection we need coloring of nodes which signifies 
1. node not touched, 
2. visited but not all descendant fully explored, 
3. fully explored means all descendant are fully visited. 

At any particular time during the algorithm grey nodes signify the active path. And last grey node being explored means all its descendant being checked to see if a any descendant is grey means that we have visited that descendant before in the active path. Hence we have a cycle. In case descendant is fully explored that means we have a cross edge that means current path is connecting to other path which is explored before.

###Breadth First Search
#####Use Case
1. detect cycle and traverse all nodes
2. Shortest path from one vertex to another in a graph where all edges have equal and positive weights

#####performance
Time Complexity: O(V+E). Here, V represents the number of vertices, and E represents the number of edges. We need to check every vertex and traverse through every edge in the graph. The time complexity is the same as it was for the DFS approach.

###Djikstra shortestpath Algorithm:
It does not work on graph containing negative edges.
####Perfomance
Time complexity O(E+VlogV)

###BellmenFord shortestpath Algorithm:
It only work on graph which have positive weight cycle. It means sum of weights in cycle is positive not negative. Thus it does not work on a graph having negative weight cycle.
It based on a assumption that in a graph of V number of vertices the path between any two vertices can not be greater than V-1 length. Thus after V-1 relaxations of all edges we will get shortest path to all vetices from source vertex. In each relaxation we consume all the edges one by one and recompute shortest distance using current edge and previous shortest distance computation.  