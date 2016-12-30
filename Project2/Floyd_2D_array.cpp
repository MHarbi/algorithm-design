#include <stdio.h>		/* printf, scanf, puts, NULL */
#include <stdlib.h>		/* srand, rand */
#include <time.h>		/* time */
#include <iostream>
#include "common.h"		/* common functions*/
using namespace std;
using namespace common;



void floyd(int n, int** W, int** D, int** P)
{
	int i, j, k;

	for (i = 0; i < n; i++)
		for (j = 0; j < n; j++)
			P[i][j] = 0;

	for (i = 0; i < n; i++)
		for (j = 0; j < n; j++)
			D[i][j] = W[i][j];

	for (k = 0; k < n; k++)
		for (i = 0; i < n; i++)
			if(D[k][i] != INF) // to skip infinity value
				// Taking advantage of the undirected graph
		   		// I minimized the period of this loop 
               	// in order to optimized the solution
				for (j = 0; j < i; j++)
					if (D[i][k] + D[k][j] < D[i][j])
					{
						P[i][j] = P[j][i] = k + 1;
						D[i][j] = D[j][i] = D[i][k] + D[k][j];
					}

	

}

int main()
{
	srand(time(NULL)); /* initialize random seed: */
	printf("+---------------------------------------------------------------------------+\n");
	printf("|-----------FLOYD'S-SHORTEST-PATH-ALGORITHM-(2-DIMENTIONAL-ARRAY)-----------|\n");
	printf("+---------------------------------------------------------------------------+\n");

	int n, t;
	int** W;

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
		W = complete2D(n);
		weight(W, n);
	}
	else if(t == 2){
		W = sparse2D(n);
		weight(W, n);
	}
	else if(t == 3){
		n = 5;  
		W = initialize_2d_matrix_case1(n);
	}
	else if(t == 4){
		n = 12;  
		W = initialize_2d_matrix_case2(n);
	}
	else
	{
		printf("Error: wrong choice.\n");
		return 0;
	}


	int** P = new int*[n];
	int** D = new int*[n];

	for (int i = 0; i < n; i++)
	{
		P[i] = new int[n];
		D[i] = new int[n];
	}

	// perform floyd
	floyd(n, W, D, P);

	// print weight array
	cout << "\nWeight Matrix: " << endl;
	printMatrix(W, n);
	
	// print shortest path
	cout << "\nShortest Path: " << endl;
	printPath(D, P, n);

	chrono::steady_clock::time_point end= chrono::steady_clock::now();
	printf("\nTime Performance = %f\n", (double)chrono::duration_cast<std::chrono::nanoseconds>(end - begin).count() * 1e-9);

	// puts("");
	// printf("Hit any key to continue> \n");
	// getchar();
	return 0;
}


