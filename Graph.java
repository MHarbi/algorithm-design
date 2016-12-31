// package GraphAlgorithms;

import java.util.Random;

public class Graph
{
	public int[][] randomGraph(int n, int m)
	{
		long seed = 1; 
		boolean weighted=false; 
		int minweight = 1; 
		int maxweight = 1; 
		int nodei[] = new int[m+1]; 
		int nodej[] = new int[m+1]; 
		int weight[] = new int[m+1]; 
		int[][] graph = new int[n][n];

		int k = randomConnectedGraph(n, m, seed, weighted, minweight, maxweight, nodei, nodej, weight); 
		
		if (k != 0) 
			System.out.println("Invalid input data, error code = " + k); 
		else 
		{ 
			for (k = 1; k <= m; k++)
			{
				graph[nodei[k]-1][nodej[k]-1] = 1;
				graph[nodej[k]-1][nodei[k]-1] = 1;
			}
		}
		return graph;
	}

	/**
     * n: number of objects to be permuted. 
     * ran: a pseudorandom number generator of type java.util.Random that has already been initialized by a “seed” of type long. 
     * perm: perm[i] is the random permutation at i, i = 1,2,…,n
     */
    public void randomPermutation(int n, Random ran, int perm[])
    { 
        int i,j,k; 
        for (i=1; i<=n; i++) 
            perm[i] = i; 
        for (i=1; i<=n; i++) 
        { 
            j = (int)(i + ran.nextDouble() * (n + 1 - i)); 
            k = perm[i]; 
            perm[i] = perm[j]; 
            perm[j] = k; 
        } 
    }

    /**
     * randomConnectedGraph: the method returns the following error code: 
     *                  0: solution found with normal execution
     *					1: value of m is too small, should be at least n-1
     *                  2: value of m is too large, should be at most n*(n-1)/2. 
     * n: number of nodes of the graph. Nodes of the graph are labeled from 1 to n. 
     * m: number of edges of the graph. 
     *					If m is less than n-1 then m will be set to n-1. 
     *					If m is greater than the maximum number of edges in a graph then a complete graph is generated.
     * seed: seed for initializing the random number generator.
     * weighted: weighted = true if the graph is weighted, false otherwise. 
     * minweight: minimum weight of the edges; if weighted = false then this value is ignored. 
     * maxweight: maximum weight of the edges; if weighted = false then this value is ignored. 
     * nodei, nodej: the i-th edge is from node nodei[i] to node nodej[i], for i = 1,2,…,m. 
     * weight: weight[i] is the weight of the i-th edge, for i = 1,2,…,m; 
     *					if weighted = false then this array is ignored.
     */
    public int randomConnectedGraph(int n, int m, long seed, boolean weighted, int minweight, int maxweight, int nodei[], int nodej[], int weight[]) 
    { 
    	int maxedges,nodea,nodeb,numedges,temp; 
    	int permute[] = new int[n + 1]; 
    	boolean adj[][] = new boolean[n+1][n+1];
    	Random ran = new Random(seed); 
    	// initialize the adjacency matrix 
    	for (nodea=1; nodea<=n; nodea++) 
    		for (nodeb=1; nodeb<=n; nodeb++) 
    			adj[nodea][nodeb] = false; 
    	numedges = 0; 
    	// check for valid input data 
    	if (m < (n - 1)) 
    		return 1; 
    	maxedges = (n * (n - 1)) / 2; 
    	if (m > maxedges) 
    		return 2; 
    	// generate a random spanning tree by the greedy method 
    	randomPermutation(n,ran,permute); 
    	for (nodea=2; nodea<=n; nodea++) 
    	{ 
    		nodeb = ran.nextInt(nodea - 1) + 1; 
    		numedges++; nodei[numedges] = permute[nodea]; 
    		nodej[numedges] = permute[nodeb]; 
    		adj[permute[nodea]][permute[nodeb]] = true; 
    		adj[permute[nodeb]][permute[nodea]] = true; 
    		if (weighted) 
    			weight[numedges] = (int)(minweight + ran.nextDouble() * (maxweight + 1 - minweight)); 
    	}

    	// add the remaining edges randomly
    	while (numedges < m)
    	{ 
    		nodea = ran.nextInt(n) + 1; 
    		nodeb = ran.nextInt(n) + 1; 
    		if (nodea == nodeb) continue; 
    		if (nodea > nodeb) 
    		{ 
    			temp = nodea; 
    			nodea = nodeb; 
    			nodeb = temp; 
    		} 
    		if (!adj[nodea][nodeb])
    		{
    			numedges++; 
    			nodei[numedges] = nodea; 
    			nodej[numedges] = nodeb; 
    			adj[nodea][nodeb] = true; 
    			if (weighted) 
    				weight[numedges] = (int)(minweight + ran.nextDouble() * (maxweight + 1 - minweight)); 
    		} 
    	} 
    	return 0; 
    }
}