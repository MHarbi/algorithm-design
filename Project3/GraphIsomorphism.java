import java.util.*;
import java.lang.*;

public class GraphIsomorphism
{
    // Test case # 1
    /*static int[][] G1 = {{0, 1, 0, 0, 1, 1, 0},
                         {1, 0, 1, 0, 0, 0, 1},
                         {0, 1, 0, 1, 0, 0, 0},
                         {0, 0, 1, 0, 0, 1, 1},
                         {1, 0, 0, 0, 0, 0, 1},
                         {1, 0, 0, 1, 0, 0, 1},
                         {0, 1, 0, 1, 1, 1, 0}};

    static int[][] G2 = {{0, 1, 0, 1, 1, 0, 0},
                         {1, 0, 0, 0, 0, 1, 1},
                         {0, 0, 0, 1, 1, 1, 0},
                         {1, 0, 1, 0, 0, 0, 0},
                         {1, 0, 1, 0, 0, 1, 1},
                         {0, 1, 1, 0, 1, 0, 0},
                         {0, 1, 0, 0, 1, 0, 0}};*/

    // Test case # 2
    /*static int[][] G1 = {{0, 1, 1, 1, 1, 0},
                         {1, 0, 0, 0, 1, 1},
                         {1, 0, 0, 0, 1, 1},
                         {1, 0, 0, 0, 0, 1},
                         {1, 1, 1, 0, 0, 1},
                         {0, 1, 1, 1, 1, 0}};

    static int[][] G2 = {{0, 0, 0, 0, 0, 1},
                         {0, 0, 0, 1, 1, 0},
                         {0, 0, 0, 1, 1, 0},
                         {0, 1, 1, 0, 1, 1},
                         {0, 1, 1, 1, 0, 0},
                         {1, 0, 0, 1, 0, 0}};*/

    // Test case # 3
    static int[][] G1 = {{0, 0, 1, 1, 1, 1, 0},
                         {0, 0, 0, 0, 1, 0, 0},
                         {1, 0, 0, 0, 1, 1, 0},
                         {1, 0, 0, 0, 1, 1, 0},
                         {1, 1, 1, 1, 0, 0, 1},
                         {1, 0, 1, 1, 0, 0, 1},
                         {0, 0, 0, 0, 1, 1, 0}};

    static int[][] G2 = {{0, 0, 0, 0, 0, 0, 1},
                         {0, 0, 1, 1, 0, 1, 1},
                         {0, 1, 0, 0, 0, 1, 1},
                         {0, 1, 0, 0, 0, 1, 1},
                         {0, 0, 0, 0, 0, 1, 1},
                         {0, 1, 1, 1, 1, 0, 0},
                         {1, 1, 1, 1, 1, 0, 0}};

    static int n;
    static int order[];
    static int order_count = 0;

    public static void main(String[] args)
    {
        System.out.println("+--------------------------------------------------------------------+");
        System.out.println("|-------------------------GRAPH-ISOMORPHISM--------------------------|");
        System.out.println("+--------------------------------------------------------------------+");

        long startTime = System.currentTimeMillis();
        GraphIsomorphism obj = new GraphIsomorphism();

        // System.out.println("Graph 1:");
        // obj.printMatrix(G1);

        // System.out.println("\nGraph 2:");
        // obj.printMatrix(G2);

        // System.out.println("\n");
        
        obj.isomorphic(0);


        System.out.format("%n%-32s: %d ms.%n", "Backtracking Solving Process Time", (System.currentTimeMillis() - startTime));
    }


    public void isomorphic(int i)
    {
        // Before starting backtrack solution, check for graphs validity. 
        if(i == 0)
        {
            // if the nodes number of both graphs are unequal, the graphs are NOT isomorphic.
            if(G1.length != G2.length)
            {
                System.out.println("The graphs are NOT isomorphic.");
                return;
            }
            else{
                n = G1.length;
                order = new int[n];
                // initialize the order with -1
                for(int k = 0; k < n; k++)
                    order[k] = -1;
            }
        }

        for (int j = 0; j < n; j++)
        {
            if (promising(i, j))
            {
                order[i] = j;

                if (i == n - 1)
                {
                    if(order_count == 0)
                    {
                        System.out.println("The graphs are isomorphic.");
                        System.out.println("The orderings of Graph 2 vertices: ");
                    }
                    order_count++;
                    printOrder(order);
                }
                else
                    isomorphic(i + 1);
            }
        }

        if(i == 0 && order_count == 0)
        {
            System.out.println("The graphs are NOT isomorphic.");
        }
    }

    /** Check whether j is promising
     * @param i: the current order to replace with j.
     * @param j: the suggested order for i.
     * @return false, if j is chosen before and 
     *                   replacing i with j does not make the adjacency matrices of G1 and G2 equal.
     *          true, otherwise.
     */
    public boolean promising(int i, int j)
    {
        // check if j is chosen before.
        for(int k = 0; k < i; k++)
        {
            if(order[k] == j)
                return false;
        }

        // check if replacing i with j does not make the adjacency matrices of G1 and G2 equal
        for (int x = 0; x < i; x++)
            if(G1[x][i] != G2[order[x]][j])
                return false;

        return true;
    }

    public void printOrder(int[] x)
    {
        for (int i = 0; i < n; i++) {
            System.out.print((x[i] + 1));
            if(i != n-1)
                System.out.print(", ");
        }
        System.out.println();
    }

    public void printMatrix(int[][] matrix)
    {
        System.out.print("\t");
        for (int i = 0; i < matrix.length; i++)
            System.out.format("\t[%5d]\t", i+1);

        System.out.println();
        for (int i = 0; i < matrix.length; i++)
        {
            System.out.format("[%5d]", i+1);
            for (int j = 0; j < matrix.length; j++)
            {
                System.out.format("\t%6d\t", matrix[i][j]);
            }       
            System.out.println();
        }
    }

}

