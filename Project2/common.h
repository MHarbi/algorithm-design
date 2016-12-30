#include <stdio.h>		/* printf, scanf, puts, NULL */
#include <stdlib.h>		/* srand, rand */
#include <time.h>		/* time */
#include <iostream>
#include "LLGraph.h"	/* utility lib. for representing the graph as linked list */
using namespace std;

// header
namespace common
{
	const int INF = 999999999;

	static int** complete2D(int n);
	static int* complete1D(int n);
	static LLGraph* completeLL(int n);

	static int** sparse2D(int n);
	static int* sparse1D(int n);
	static LLGraph* sparseLL(int n);

	static void weight(int** adj_matrix, int n);
	static void weight(int* adj_matrix, int n);
	static void weight(LLGraph* graph, int n);

	static void printMatrix(int** matrix, int n);
	static int minDistance(int* length, bool* F, int n);
	static int get1DIndex(int v, int u);

	static void printPath(int* touch, int j);							// used in Dijkstra
	static void printPath(int** P, int v, int u); 						// used in Floyd 2D
	static void printPath(int** D, int** P, int n); 					// used in Floyd 2D
	static void printPath(int* P, int v, int u); 						// used in Floyd 1D
	static void printPath(int* D, int** P, int n); 						// used in Floyd 1D
	static void printPath(LLGraph* P, int v, int u); 					// used in Floyd LL
	static void printPath(LLGraph* D, int** P, int n); 					// used in Floyd LL
	static void printSolution(int* length, int* touch, int src, int n); // used in Dijkstra

	static int** initialize_2d_matrix_case1(int n);
	static int* initialize_1d_matrix_case1(int n);
	static int** initialize_2d_matrix_case2(int n);
	static int* initialize_1d_matrix_case2(int n);
	static LLGraph* initialize_ll_case1(int n);
	static LLGraph* initialize_ll_case2(int n);
}

// source
namespace common
{
	/*
	 * generate random number between 1 and n
	 */
	int random(int n)
	{
		return rand() % n + 1;
	}

	int** complete2D(int n)
	{
		int **adj_matrix;
		adj_matrix = new int*[n];

		for (int i = 0; i < n; i++)
		{
			adj_matrix[i] = new int[n];
			for (int j = 0; j < n; j++)
			{
	            // fill in some initial values
	            // (filling diagnal elements in zeros)
	            if(i == j)
	            	adj_matrix[i][j] = 0;
	            else
					adj_matrix[i][j] = 1;
			}
		}

		return adj_matrix;
	}

	int* complete1D(int n)
	{
		int matrix_len = ((n*n)-n)/2;
		int* adj_matrix = new int[matrix_len];

		for (int i = 0; i < matrix_len; i++)
			adj_matrix[i] = 1;

		return adj_matrix;
	}

	LLGraph* completeLL(int n)
	{
		
		LLGraph* graph = new LLGraph(n);

		for (int i = n-1; i >= 0; i--)
			for (int j = n-1; j > i; j--)
				graph->addUnDEdge(i, j);

		return graph;
	}

	int** sparse2D(int n)
	{
		int **adj_matrix;
		adj_matrix = new int*[n];

		for (int i = 0; i < n; i++)
		{
			adj_matrix[i] = new int[n];
			for (int j = 0; j < n; j++)
	            adj_matrix[i][j] = 0;
		}
		// number of edges must be n - 1
		for(int i = 1; i < n; )
		{
			int j = random(i) - 1;
			if(j != i)
			{
				adj_matrix[i][j] = 1;
				adj_matrix[j][i] = 1;
				i++;
			}
		}

		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
			{
				// fill in some initial values
				// keeping diagnal elements in zeros
				// filling out the other zero elements with INF
				if(i != j && adj_matrix[i][j] == 0)
					adj_matrix[i][j] = INF;
			}

		return adj_matrix;
	}

	int* sparse1D(int n)
	{
		int matrix_len = ((n*n)-n)/2;
		int *adj_matrix = new int[matrix_len];

		for (int i = 0; i < matrix_len; i++)
			adj_matrix[i] = 0;

		// number of edges must be n - 1
		for(int i = 1; i < n; )
		{
			int j = random(i) - 1;
			if(j != i)
			{
				adj_matrix[get1DIndex(i, j)] = 1;
				i++;
			}
		}

		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
			{
				// fill in some initial values
				// keeping diagnal elements in zeros
				// filling out the other zero elements with INF
				if(i != j && adj_matrix[get1DIndex(i, j)] == 0)
					adj_matrix[get1DIndex(i, j)] = INF;
			}

		return adj_matrix;
	}

	LLGraph* sparseLL(int n)
	{
		LLGraph* graph = new LLGraph(n);

		// number of edges must be n - 1
		for(int i = 1; i < n; )
		{
			int j = random(i) - 1;
			if(j != i)
			{
				graph->addUnDEdge(i, j);
				i++;
			}
		}

		return graph;
	}

	void weight(int** adj_matrix, int n)
	{
		for (int i = 0; i < n; i++)
			for(int j = i+1; j < n; j++)
				if(adj_matrix[i][j] == 1)
				{
					adj_matrix[i][j] = random(n);
					adj_matrix[j][i] = adj_matrix[i][j];
				}
	}

	void weight(int* adj_matrix, int n)
	{
		int matrix_len = ((n*n)-n)/2;
		for (int i = 0; i < matrix_len; i++)
			if(adj_matrix[i] == 1)
				adj_matrix[i] = random(n);
	}

	void weight(LLGraph* graph, int n)
	{
		for (int i = 0; i < n; ++i)
			for(struct Edge* e = graph->getLastEdges()[i].head; e != NULL; e = e->next)
				if(e->attr == 1)
					e->attr = random(n); // set weight
	}

	void printMatrix(int** matrix, int n)
	{
		// cout << "\t";
		for (int i = 0; i < n; i++)
			printf("\t[%5.d]\t", i+1);

		cout << endl;
		for (int i = 0; i < n; i++)
		{
			printf("[%5.d]", i+1);
			for (int j = 0; j < n; j++)
			{
				if(matrix[i][j] == INF)
					printf("\t%8s\t", "∞");
				else if(matrix[i][j] == 0)
					printf("\t%6s\t", "0");
				else
					printf("\t%6.d\t", matrix[i][j]);
			}
			cout << endl;
		}
	}

	void printMatrix(int* matrix, int n)
	{
		// cout << "\t";
		for (int i = 0; i < n; i++)
			printf("\t[%5.d]\t", i+1);

		cout << endl;
		for (int i = 0; i < n; i++)
		{
			printf("[%5.d]", i+1);
			for (int j = 0; j < n; j++)
			{
				int k = get1DIndex(i, j);
				if(i == j || matrix[k] == 0)
					printf("\t%6s\t", "0");
				else if(matrix[k] == INF)
					printf("\t%8s\t", "∞");
				else
					printf("\t%6.d\t", matrix[k]);
			}		
			cout << endl;
		}
	}

	// A utility function to find the vertex with minimum distance value, from
	// the set of vertices not yet included in shortest path tree
	int minDistance(int* length, bool* F, int n)
	{
		int min = INF;
		int vnear;

		for (int i = 0; i < n; i++)
			if (F[i] == false && length[i] <= min){
				min = length[i];
				vnear = i;
			}

			return vnear;
	}

	int get1DIndex(int v, int u)
	{
		if (v < u) {
			int temp = v;
			v = u;
			u = temp;
		}
		// return ((v-1) * (v-2)) / 2 + u; // intexed 1
		return (v * (v-1)) / 2 + u;
	}

	// print shortest path from source to j
	// used in Dijkstra
	void printPath(int* touch, int j)
	{
		// Base Case : If j is source
		if (touch[j] == -1) return;
		printPath(touch, touch[j]);
		cout << "-> " << j + 1 << " ";
	}

	// used in Floyd 2D
	void printPath(int** P, int v, int u)
	{
		if(v != u && P[v][u] != 0){
			printPath(P, v, P[v][u]-1);
			cout << "-> " << P[v][u] << " ";
			printPath(P, P[v][u]-1, u);
		}
	}
	// used in Floyd 2D
	void printPath(int** D, int** P, int n)
	{
		for(int src = 0; src < n; src++)
		{
			printf("Source = %d\n", src + 1);
			printf("+---------+------------+-------------------------\n");
			printf("| Vertex  |  Distance  | Shprtest Path\n");
			printf("+---------+------------+-------------------------");
			for (int i = 0; i < n; i++)
			{
				if(D[src][i] == INF)
					printf("\n %d -> %d \t\t ∞\t\t ", src + 1, i + 1);
				else if(D[src][i] == 0)
					printf("\n %d -> %d \t\t %d\t\t%d ", src + 1, i + 1, D[src][i], src + 1);
				else
				{
					printf("\n %d -> %d \t\t %d\t\t%d ", src + 1, i + 1, D[src][i], src + 1);
		        	printPath(P, src, i);
		        	printf("-> %d", i + 1);
		        }
			}
			printf("\n+---------+------------+-------------------------\n");
		}
	}

	// used in Floyd 1D
	void printPath(int* P, int v, int u)
	{
		int index = get1DIndex(v, u);
		if(v != u && P[index] != 0)
		{
			printPath(P, v, P[index]-1);
			cout << "-> " << P[index] << " ";
			printPath(P, P[index]-1, u);
		}
	}
	// used in Floyd 1D
	void printPath(int* D, int* P, int n)
	{
		for(int src = 0; src < n; src++)
		{
			printf("Source = %d\n", src + 1);
			printf("+---------+------------+-------------------------\n");
			printf("| Vertex  |  Distance  | Shprtest Path\n");
			printf("+---------+------------+-------------------------");
			for (int i = 0; i < n; i++)
			{
				if(src == i)
					printf("\n %d -> %d \t\t %d\t\t%d ", src + 1, i + 1, 0, src + 1);
				else if(D[get1DIndex(src, i)] == INF)
					printf("\n %d -> %d \t\t ∞\t\t ", src + 1, i + 1);
				else if(D[get1DIndex(src, i)] == 0)
					printf("\n %d -> %d \t\t %d\t\t%d ", src + 1, i + 1, D[get1DIndex(src, i)], src + 1);
				else
				{
					printf("\n %d -> %d \t\t %d\t\t%d ", src + 1, i + 1, D[get1DIndex(src, i)], src + 1);
		        	printPath(P, src, i);
		        	printf("-> %d", i + 1);
		        }
			}
			printf("\n+---------+------------+-------------------------\n");
		}
	}

	// used in Floyd LL
	void printPath(LLGraph* P, int v, int u)
	{
		int weight = P->getAttr(v, u);
		if(v != u && weight != 0)
		{
			printPath(P, v, weight-1);
			cout << "-> " << weight << " ";
			printPath(P, weight-1, u);
		}
	}
	// used in Floyd LL
	void printPath(LLGraph* D, LLGraph* P, int n)
	{
		for(int src = 0; src < n; src++)
		{
			printf("Source = %d\n", src + 1);
			printf("+---------+------------+-------------------------\n");
			printf("| Vertex  |  Distance  | Shprtest Path\n");
			printf("+---------+------------+-------------------------");
			for (int i = 0; i < n; i++)
			{
				int weight = D->getAttr(src, i);
				if(src == i)
					printf("\n %d -> %d \t\t %d\t\t%d ", src + 1, i + 1, 0, src + 1);
				else if(weight == INF)
					printf("\n %d -> %d \t\t ∞\t\t ", src + 1, i + 1);
				else if(weight == 0)
					printf("\n %d -> %d \t\t %d\t\t%d ", src + 1, i + 1, weight, src + 1);
				else
				{
					printf("\n %d -> %d \t\t %d\t\t%d ", src + 1, i + 1, weight, src + 1);
		        	printPath(P, src, i);
		        	printf("-> %d", i + 1);
		        }
			}
			printf("\n+---------+------------+-------------------------\n");
		}
	}

	void printSolution(int* length, int* touch, int src, int n)
	{
		printf("+---------+------------+-------------------------\n");
		printf("| Vertex  |  Distance  | Shortest Path\n");
		printf("+---------+------------+-------------------------");
		for (int i = 0; i < n; i++)
		{
			if(length[i] == INF)
				printf("\n %d -> %d \t\t ∞\t\t ", src + 1, i + 1);
			else 
				printf("\n %d -> %d \t\t %d\t\t%d ", src + 1, i + 1, length[i], src + 1);
	        printPath(touch, i);
		}
		printf("\n+---------+------------+-------------------------\n");
	}

	int** initialize_2d_matrix_case1(int n)
	{
		int** m;
		m = new int*[n];

		for (int i = 0; i < n; i++)
		{
			m[i] = new int[n];
		}
		
		m[0][0] = 0, 	m[0][1] = 7, 	m[0][2] = INF,	m[0][3] = 18,	m[0][4] = 15;
		m[1][0] = 7, 	m[1][1] = 0, 	m[1][2] = 11, 	m[1][3] = 7,	m[1][4] = 5;
		m[2][0] = INF,	m[2][1] = 11, 	m[2][2] = 0, 	m[2][3] = 29, 	m[2][4] = INF;
		m[3][0] = 18,	m[3][1] = 7,	m[3][2] = 29, 	m[3][3] = 0, 	m[3][4] = 18;
		m[4][0] = 15,	m[4][1] = 5,	m[4][2] = INF,	m[4][3] = 18,	m[4][4] = 0;
		return m;
	}

	int* initialize_1d_matrix_case1(int n)
	{
		int *m;
		m = new int[((n*n)-n)/2];
		
		m[0] = 7;
		m[1] = INF; 	m[2] = 11;
		m[3] = 18;		m[4] = 7;		m[5] = 29;
		m[6] = 15;		m[7] = 5;		m[8] = INF;	m[9] = 18;
		return m;
	}

	int** initialize_2d_matrix_case2(int n)
	{
		int** m;
		m = new int*[n];

		for (int i = 0; i < n; i++)
		{
			m[i] = new int[n];
		}
		
		m[0][0] = 0; 	m[0][1] = INF; 	m[0][2] = INF; 	m[0][3] = INF; 	m[0][4] = INF; 	m[0][5] = INF; 	m[0][6] = INF; 	m[0][7] = INF; 	m[0][8] = INF; 	m[0][9] = 8; 	m[0][10] = INF; 	m[0][11] = INF;
		m[1][0] = INF; 	m[1][1] = 0; 	m[1][2] = 17; 	m[1][3] = 13; 	m[1][4] = INF; 	m[1][5] = INF; 	m[1][6] = INF; 	m[1][7] = 17; 	m[1][8] = 12; 	m[1][9] = INF; 	m[1][10] = INF; 	m[1][11] = INF;
		m[2][0] = INF; 	m[2][1] = 17; 	m[2][2] = 0; 	m[2][3] = INF; 	m[2][4] = INF; 	m[2][5] = INF; 	m[2][6] = INF; 	m[2][7] = INF; 	m[2][8] = INF; 	m[2][9] = 7; 	m[2][10] = INF; 	m[2][11] = 9;
		m[3][0] = INF; 	m[3][1] = 13; 	m[3][2] = INF; 	m[3][3] = 0; 	m[3][4] = INF; 	m[3][5] = INF; 	m[3][6] = 15; 	m[3][7] = INF; 	m[3][8] = INF; 	m[3][9] = 13; 	m[3][10] = 5; 		m[3][11] = 13;
		m[4][0] = INF; 	m[4][1] = INF; 	m[4][2] = INF; 	m[4][3] = INF; 	m[4][4] = 0; 	m[4][5] = 18; 	m[4][6] = INF; 	m[4][7] = INF; 	m[4][8] = 5; 	m[4][9] = INF; 	m[4][10] = INF; 	m[4][11] = INF;
		m[5][0] = INF; 	m[5][1] = INF; 	m[5][2] = INF; 	m[5][3] = INF; 	m[5][4] = 18; 	m[5][5] = 0; 	m[5][6] = INF; 	m[5][7] = 17; 	m[5][8] = INF; 	m[5][9] = INF; 	m[5][10] = INF; 	m[5][11] = INF;
		m[6][0] = INF; 	m[6][1] = INF; 	m[6][2] = INF; 	m[6][3] = 15; 	m[6][4] = INF; 	m[6][5] = INF; 	m[6][6] = 0; 	m[6][7] = 17; 	m[6][8] = INF; 	m[6][9] = INF; 	m[6][10] = 8; 		m[6][11] = 11;
		m[7][0] = INF; 	m[7][1] = 17; 	m[7][2] = INF; 	m[7][3] = INF; 	m[7][4] = INF; 	m[7][5] = 17; 	m[7][6] = 17; 	m[7][7] = 0; 	m[7][8] = INF; 	m[7][9] = INF; 	m[7][10] = INF; 	m[7][11] = INF;
		m[8][0] = INF; 	m[8][1] = 12; 	m[8][2] = INF; 	m[8][3] = INF; 	m[8][4] = 5; 	m[8][5] = INF; 	m[8][6] = INF; 	m[8][7] = INF; 	m[8][8] = 0; 	m[8][9] = INF; 	m[8][10] = 15; 		m[8][11] = INF;
		m[9][0] = 8; 	m[9][1] = INF; 	m[9][2] = 7; 	m[9][3] = 13; 	m[9][4] = INF; 	m[9][5] = INF; 	m[9][6] = INF; 	m[9][7] = INF; 	m[9][8] = INF; 	m[9][9] = 0; 	m[9][10] = INF; 	m[9][11] = 14;
		m[10][0] = INF; m[10][1] = INF; m[10][2] = INF; m[10][3] = 5; 	m[10][4] = INF; m[10][5] = INF; m[10][6] = 8; 	m[10][7] = INF; m[10][8] = 15; 	m[10][9] = INF; m[10][10] = 0; 		m[10][11] = INF;
		m[11][0] = INF; m[11][1] = INF; m[11][2] = 9; 	m[11][3] = 13; 	m[11][4] = INF; m[11][5] = INF; m[11][6] = 11; 	m[11][7] = INF; m[11][8] = INF; m[11][9] = 14; 	m[11][10] = INF; 	m[11][11] = 0;
		return m;
	}

	int* initialize_1d_matrix_case2(int n)
	{
		int* m;
		m = new int[((n*n)-n)/2];
		
		m[0] = INF;
		m[1] = INF; 	m[2] = 17;
		m[3] = INF; 	m[4] = 13; 		m[5] = INF;
		m[6] = INF; 	m[7] = INF; 	m[8] = INF; 	m[9] = INF;
		m[10] = INF; 	m[11] = INF; 	m[12] = INF; 	m[13] = INF; 	m[14] = 18;
		m[15] = INF; 	m[16] = INF; 	m[17] = INF; 	m[18] = 15; 	m[19] = INF; 	m[20] = INF;
		m[21] = INF; 	m[22] = 17; 	m[23] = INF; 	m[24] = INF; 	m[25] = INF; 	m[26] = 17; 	m[27] = 17;
		m[28] = INF; 	m[29] = 12; 	m[30] = INF; 	m[31] = INF; 	m[32] = 5; 		m[33] = INF; 	m[34] = INF; 	m[35] = INF;
		m[36] = 8; 		m[37] = INF; 	m[38] = 7; 		m[39] = 13; 	m[40] = INF; 	m[41] = INF; 	m[42] = INF; 	m[43] = INF; 	m[44] = INF;
		m[45] = INF; 	m[46] = INF; 	m[47] = INF; 	m[48] = 5; 		m[49] = INF; 	m[50] = INF; 	m[51] = 8; 		m[52] = INF; 	m[53] = 15; 	m[54] = INF;
		m[55] = INF; 	m[56] = INF; 	m[57] = 9; 		m[58] = 13; 	m[59] = INF; 	m[60] = INF; 	m[61] = 11; 	m[62] = INF; 	m[63] = INF; 	m[64] = 14; 	m[65] = INF;
		return m;
	}

	LLGraph* initialize_ll_case1(int n)
	{
		LLGraph* graph = new LLGraph(n);	

		graph->addUnDEdge(0, 1, 7);
		graph->addUnDEdge(0, 3, 18);
		graph->addUnDEdge(0, 4, 15);
		graph->addUnDEdge(1, 2, 11);
		graph->addUnDEdge(1, 3, 7);
		graph->addUnDEdge(1, 4, 5);
		graph->addUnDEdge(2, 3, 29);
		graph->addUnDEdge(3, 4, 18);
		return graph;
	}

	LLGraph* initialize_ll_case2(int n)
	{
		LLGraph* graph = new LLGraph(n);

		graph->addUnDEdge(0, 9,  8);
		graph->addUnDEdge(1, 2,  17);
		graph->addUnDEdge(1, 3,  13);
		graph->addUnDEdge(1, 7,  17);
		graph->addUnDEdge(1, 8,  12);
		graph->addUnDEdge(2, 9,  7);
		graph->addUnDEdge(2, 11, 9);
		graph->addUnDEdge(3, 6,  15);
		graph->addUnDEdge(3, 9,  13);
		graph->addUnDEdge(3, 10, 5);
		graph->addUnDEdge(3, 11, 13);
		graph->addUnDEdge(4, 5,  18);
		graph->addUnDEdge(4, 8,  5);
		graph->addUnDEdge(5, 7,  17);
		graph->addUnDEdge(6, 7,  17);
		graph->addUnDEdge(6, 10, 8);
		graph->addUnDEdge(6, 11, 11);
		graph->addUnDEdge(8, 10, 15);
		graph->addUnDEdge(9, 11, 14);
		return graph;
	}
}
