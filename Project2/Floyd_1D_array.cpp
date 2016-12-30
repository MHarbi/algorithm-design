#include <stdio.h>		/* printf, scanf, puts, NULL */
#include <stdlib.h>		/* srand, rand */
#include <time.h>		/* time */
#include <iostream>
#include "common.h"		/* common functions*/
using namespace std;
using namespace common;



//template <std::size_t N, std::size_t M>
void floyd(int n, int* W, int* D, int* P)
{
	int matrix_len = ((n*n)-n)/2;

	int i, j, k;

	for (i = 0; i < matrix_len; i++)
	{
		P[i] = 0;
		D[i] = 0;
	}

	for (i = 0; i < matrix_len; i++)
		D[i] = W[i];

	for (k = 0; k < n; k++)
		for (i = 0; i < n; i++)
		{
			int ik = get1DIndex(i, k);
			if(D[ik] != INF) // to skip infinity value
				// Taking advantage of the undirected graph
		   		// I minimized the period of this loop 
               	// in order to optimized the solution
				for (j = 0; j < i; j++)
					if(i != j && i != k && j != k)
					{
						int kj = get1DIndex(k, j);
						int ij = get1DIndex(i, j);
						if (D[ik] + D[kj] < D[ij])
						{
							P[ij] = k + 1;
							D[ij] = D[ik] + D[kj];
						}
					}
		}

}

int main()
{
	srand(time(NULL)); /* initialize random seed: */
	printf("+---------------------------------------------------------------------------+\n");
	printf("|-----------FLOYD'S-SHORTEST-PATH-ALGORITHM-(1-DIMENTIONAL-ARRAY)-----------|\n");
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

	int matrix_len = ((n*n)-n)/2;
	int* P = new int[matrix_len];
	int* D = new int[matrix_len];

	for (int i = 0; i < matrix_len; i++)
	{
		P[i] = 0;
	}

	floyd(n, W, D, P);

	cout << "\nWeight Matrix: " << endl;
	printMatrix(W, n);
	
	cout << "\nShortest Path: " << endl;
	printPath(D, P, n);

	chrono::steady_clock::time_point end= chrono::steady_clock::now();
	printf("\nTime Performance = %f\n", (double)chrono::duration_cast<std::chrono::nanoseconds>(end - begin).count() * 1e-9);

	// puts("");
	// printf("Hit any key to continue> \n");
	// getchar();
	return 0;
}


