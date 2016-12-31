import java.util.*;
// import GraphAlgorithms.Graph;

public class FindGirth
{
    // test case # 1
    static int[][] G = {{0, 0, 0, 1, 0, 1},
                        {0, 0, 0, 1, 1, 1},
                        {0, 0, 0, 1, 0, 0},
                        {1, 1, 1, 0, 0, 0},
                        {0, 1, 0, 0, 0, 0},
                        {1, 1, 0, 0, 0, 0}};

    // test case # 2
    /*static int[][] G = {{0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1},
                        {1, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 1},
                        {1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 1, 0},
                        {1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0},
                        {1, 1, 1, 1, 0, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1},
                        {0, 0, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0},
                        {1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1},
                        {1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1},
                        {1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 1},
                        {1, 1, 0, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0},
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1},
                        {1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1},
                        {0, 1, 0, 1, 1, 0, 1, 0, 0, 1, 1, 1, 0, 0, 1},
                        {1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1},
                        {1, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0}};*/
    // static int[][] G;

    public static void main(String[] args)
    {
        System.out.println("+--------------------------------------------------------------------+");
        System.out.println("|---------------------------FINDING-GIRTH----------------------------|");
        System.out.println("+--------------------------------------------------------------------+");

        FindGirth obj = new FindGirth();
        Scanner in = new Scanner(System.in);
        // System.out.println("Enter nodes number : ");
        // n = in.nextInt();
        // System.out.println("Enter edges number: ");
        // int m = in.nextInt();
        // System.out.println();

        // Graph gr = new Graph();
        // G = gr.randomGraph(n, m);

        // System.out.println("\nGraph:");
        // obj.printMatrix(G);
        // System.out.println("\n");

        long startTime = System.currentTimeMillis();
        int n = G.length;
        int girth = obj.findGirth(G, n);
        if(girth == -1)
            System.out.println("The girth is infinite since the graph is acyclic.");
        else
            System.out.println("The girth of the graph is " + girth);


        System.out.format("%n%-21s: %d ms.%n", "Solving Process Time", (System.currentTimeMillis() - startTime));
    }

    /** Find the girth of a graph (O(V*(V+E))).
     *  @param graph: Graph to find the girth for.
     *  @return The girth of a graph.
     */
    private int findGirth(int[][] graph, int n)
    {
        int[] p = new int[n]; // For calculating minimum length of circle(s), Integer array p is used to store predecessor of every vertex. 
        // Rather than using boolean values to indicate whether a vertex was visited,
        // use a Integer array to mark each visited vertex.
        // If visited[v] = -1 , vertex v has not been visited.
        // If v is root of a BFS, visited[v] = 0;
        // Other positive values represent for the shortest path length from source(root) to a particular vertex.
        int[] visited = new int[n];
        int girth = Integer.MAX_VALUE;

        for(int v = 0; v < n; v++)
        {
            // Initialize int[] visited before a BFS is going to commence.
            for(int i = 0; i < n; i++){
                visited[i] = -1;
            }
            // A standard BFS begins.
            Queue<Integer> q = new LinkedList<Integer>();
            int vgirth = Integer.MAX_VALUE;
            visited[v] = 0;
            q.add(v);              // Inserts the v node into q queue
            while(!q.isEmpty())
            {
                int u = q.poll(); // Retrieves and removes the head of this queue
                for(Integer w: getNeighbors(graph, u, n))
                {
                    if(visited[w] < 0)
                    {
                        // Each level of BFS should increase shortest path length by 1
                        visited[w] = visited[u] + 1;
                        // w's parent u was found.
                        p[w] = u;
                        q.add(w);
                    }
                    else
                    {
                        // If the visited vertex w is not u's parent,
                        // there is a cycle here.
                        if(w != p[u])
                        {
                            // Decide that whether this cycle's length is greater than
                            // the smallest one that has been discovered.
                            // If no, this cycle is the smallest one.
                            if(vgirth > visited[w] + visited[u] + 1)
                            {
                                vgirth = visited[w] + visited[u] + 1;
                            }
                        }
                    }
                }
            }

            if(vgirth < girth)
                girth = vgirth;
        }

        if(girth == Integer.MAX_VALUE)
            return -1;
        else
            return girth;
    }

    public List<Integer> getNeighbors(int[][] graph, int vertex, int n)
    {
        List<Integer> neighbors = new ArrayList<Integer>();
        for (int i = 0; i < n; i++)
        {
            if(graph[vertex][i] == 1)
                neighbors.add(i);
        }
        return neighbors;
    }

    public int getNeighborCount(int vertex) {
        int neighborCount = 0;
        for (int i = 0; i < G.length; i++){
            if(G[vertex][i] == 1) {
                neighborCount++;
            }
        }
        return neighborCount;
    }

    public int[] getNeighbors(int vertex) {
        int[] neighbors = new int[getNeighborCount(vertex)];
        int j = 0;
        for (int i = 0; i < G.length; i++){
            if(G[vertex][i] == 1) {
                neighbors[j++] = i;
            }
        }
        return neighbors;
    }

    public void printMatrix1(int[][] matrix)
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

    public void printMatrix(int[][] matrix)
    {
        for (int i = 0; i < matrix.length; i++)
        {
            for (int j = 0; j < matrix.length; j++)
            {
                System.out.format("%d", matrix[i][j]);
                if(j != matrix.length -1)
                    System.out.print(",");
            }       
            System.out.println();
        }
    }
}

