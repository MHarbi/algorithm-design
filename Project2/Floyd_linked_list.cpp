#include <stdio.h>		/* printf, scanf, puts, NULL */
#include <stdlib.h>		/* srand, rand */
#include <time.h>		/* time */
#include <iostream>
#include "common.h"		/* common functions*/
using namespace std;
using namespace common;



void floyd(int n, LLGraph* W, LLGraph* D, LLGraph* P)
{
	int i, j, k;

	for (i = n-1; i >= 0; i--)
		for (j = n-1; j > i; j--)
			P->addUnDEdge(i, j, 0);

	*D = *W->clone();

	for (k = 0; k < n; k++)
		for (i = 0; i < n; i++)
		{
			int dik = D->getAttr(i, k);
			if(dik != INF) // to skip infinity value
				// Taking advantage of the undirected graph
		   		// I minimized the period of this loop 
               	// in order to optimized the solution
				for (j = 0; j < i; j++)
				{
					int dkj = D->getAttr(k, j);
					if(dkj != INF)
					{
						int dij = D->getAttr(i, j);
						if (dik + dkj < dij)
						{
							P->setAttr(i, j, k + 1);
							D->setAttr(i, j, dik + dkj);
						}
					}
				}
		}
}

int main()
{
	srand(time(NULL)); /* initialize random seed: */
	printf("+---------------------------------------------------------------------------+\n");
	printf("|---------------FLOYD'S-SHORTEST-PATH-ALGORITHM-(LINKED-LIST)---------------|\n");
	printf("+---------------------------------------------------------------------------+\n");

	int n, t;
	LLGraph *W;

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
		W = completeLL(n);
		weight(W, n);
	}
	else if(t == 2){
		W = sparseLL(n);
		weight(W, n);
	}
	else if(t == 3){
		n = 5;
		W = initialize_ll_case1(n);
	}
	else if(t == 4){
		n = 12;  
		W = initialize_ll_case2(n);
	}
	else
	{
		printf("Error: wrong choice.\n");
		return 0;
	}

	LLGraph D(n);
	LLGraph P(n);

	floyd(n, W, &D, &P);

	// print the linked list representation of the graph
	cout << "\n\nWeight: " << endl;
	W->print();

	cout << "\n\nShortest Path: " << endl;
	printPath(&D, &P, n);

	chrono::steady_clock::time_point end= chrono::steady_clock::now();
	printf("\nTime Performance = %f\n", (double)chrono::duration_cast<std::chrono::nanoseconds>(end - begin).count() * 1e-9);

	// puts("");
	// printf("Hit any key to continue> \n");
	// getchar();
	return 0;
}


