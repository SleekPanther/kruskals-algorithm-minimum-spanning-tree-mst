# Kruskal's Algorithm Minimum Spanning Tree (Graph MST)

Java Implementation of Kruskal's Algorithm using **disjoing sets**

##PseudoCode
![kruskal-pseudocode](https://cloud.githubusercontent.com/assets/15304528/23335535/450deca2-fb85-11e6-9fd6-ce146ddb3471.png)

##Detailed Implementation
![kruskal-detailed-implementation](https://cloud.githubusercontent.com/assets/15304528/23335531/3ef5b4da-fb85-11e6-9d9d-01318c793a3c.png)

##Code Notes
Current code runs on this sample graph
![graph](https://cloud.githubusercontent.com/assets/15304528/23335398/0971bd4c-fb83-11e6-9390-3c3d10d524c3.png)
It produces this Minimum Spanning Tree
![mst](https://cloud.githubusercontent.com/assets/15304528/23335524/267f049c-fb85-11e6-8e50-b89029bcb464.png)

- Requires **distinct nodes named with consecutive integers**. If they really **do** have the same "name", convert them to integer ID's to use this algorithm  
Example graph has 8 nodes numbered 1-8 (array ignores the 0th index)
- **You must hardcode the graph structure in constructor** as a **list of edges**
- **Make sure `nodeCount` is accurate**. There's no error checking between `nodeCount` & the actual edge list
- My implementation has **early termination**. If a graph has **N nodes** the MST has **(N-1) edges**  
Hence `&& mstEdges.size()<(nodeCount-1)` in my loop

####Sources
- [Disjoint Sets by Mark Allen Weiss](http://users.cis.fiu.edu/~weiss/dsaajava3/code/DisjSets.java) Author of *Data Structures and Algorithm Analysis in Java (3rd Edition), 2011*  
He also has other helpful [Java Data Structures implementations](http://users.cis.fiu.edu/~weiss/dsaajava3/code/)
