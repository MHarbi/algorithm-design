#include <stdio.h>		/* printf, scanf, puts, NULL */
#include <stdlib.h>		/* srand, rand */
#include <time.h>		/* time */
#include <iostream>
#include "common.h"		/* common functions*/
#include "MinIndexedPQ.h"
using namespace std;
using namespace common;



void dijkstra(int* W, int src, int n)
{
	int vnear, v = n - 1;
	int* length = new int[n];	// length[i] will hold the shortest distance from src to i

	int* touch = new int[n];	// keep track shortest path
	MinIndexedPQ pq(n);

	// Initializations
	for (int i = 0; i < n; i++)
	{
		touch[i] = -1;
		length[i] = INF; 		// Unknown distance function from source to v
		pq.insert(i, length[i]);
	}

	length[src] = 0; // Distance from source to source
	// update distance of source in min heap
	pq.decreaseKey(src, length[src]);

	// In the followin loop, min heap contains all nodes
    // whose shortest distance is not yet finalized.
	while(!pq.isEmpty())
    {
    	// Extract the vertex with minimum distance value
        vnear = pq.deleteMin();

        // Traverse through all adjacent vertices of u (the extracted
        // vertex) and update their distance values
        for (int i = 0; i < n; i++)
        {
            int newlen = length[vnear] + W[get1DIndex(vnear, i)];
            // If shortest distance to v is not finalized yet, and distance to v
            // through u is less than its previously calculated distance
            if(pq.contains(i) && 
				length[vnear] != INF &&
            	newlen < length[i])
            {
                length[i] = newlen;
                touch[i] = vnear;
                // update distance value in min heap also
                pq.decreaseKey(i, newlen);
            }
        }
    }

	// print the constructed distance array and its shortest path
	printSolution(length, touch, src, n);
}

int main()
{
	srand(time(NULL)); /* initialize random seed: */
	printf("+---------------------------------------------------------------------------+\n");
	printf("|-----------DIJKSTRA-SHORTEST-PATH-ALGORITHM-(1-DIMENTIONAL-ARRAY)----------|\n");
	printf("+---------------------------------------------------------------------------+\n");

	int n, t;
	int* W;

	printf("Please, chose the graph type: \n");
	printf("\t 1 Complete Graph. \n");
	printf("\t 2 Sparse Graph. \n");
	printf("\t 3 Test Case #1. \n");
	printf("\t 4 Test Case #2. \n");
	printf("Your choice: ");
	scanf("%d", &t);
	
	if(t == 1 || t == 2)
	{
		printf("Enter the number of vetices: ");
		scanf("%d", &n);
	}

	chrono::steady_clock::time_point begin = chrono::steady_clock::now();

	if(t == 1){
		W = complete1D(n);
		weight(W, n);
	}
	else if(t == 2){
		W = sparse1D(n);
		weight(W, n);
	}
	else if(t == 3){
		n = 5;  
		W = initialize_1d_matrix_case1(n);
	}
	else if(t == 4){
		n = 12;  
		W = initialize_1d_matrix_case2(n);
	}
	else
	{
		printf("Error: wrong choice.\n");
		return 0;
	}
	
	// print the linked list representation of the graph
	cout << "\n\nWeight: " << endl;
	printMatrix(W, n);
	cout << endl;

	for(int i = 0; i < n; i++)
	{
		printf("Source = %d\n", i + 1);
		dijkstra(W, i, n);
	}

	chrono::steady_clock::time_point end= chrono::steady_clock::now();
	printf("\nTime Performance = %f\n", (double)chrono::duration_cast<std::chrono::nanoseconds>(end - begin).count() * 1e-9);

	// puts("");
	// printf("Hit any key to continue> \n");
	// getchar();
	return 0;
}


