import java.util.*;
import java.lang.*;

public class TSP
{
    class HamiltonPath
    {
        public int weight;
        public int[] path;
    }

    static class Node
    {
        int data; 
        boolean visited; 
        Node(int data) { this.data = data; } 
    }

    public static void main(String[] args)
    {
        long startTime = System.currentTimeMillis();

        TSP obj = new TSP();

        int[][] d = {{ 0, 36, 32, 54, 20, 40}, 
                    {36,  0, 22, 58, 54, 67}, 
                    {32, 22,  0, 36, 42, 71}, 
                    {54, 58, 36,  0, 50, 92}, 
                    {20, 54, 42, 50,  0, 45}, 
                    {40, 67, 71, 92, 45,  0}};

        System.out.println("Traveling Salesman Problem (TSP) using Brute Force Algorithm");
        System.out.println("The matrix of the route length is expressed as follows:\n");

        for(int i=0; i < d.length; i++)
        {  
            for(int j = 0; j < d.length; j++)
                System.out.format("%5d", d[i][j]);
            System.out.println(); 
        }

        // obj.allHamiltonPath(d);   //list all Hamiltonian paths of graph
        obj.HamiltonPath(d, 1);  //list all Hamiltonian paths start at point 1

        System.out.format("%n%-32s: %d", "No. of calculated routes", obj.count);
        System.out.format("%n%-32s: %d", "Minimum length", obj.minWeight);
        System.out.format("%n%-32s: %d ms.%n", "Brute Force Solving Process Time", (System.currentTimeMillis() - startTime));
    }

    static int len;
    static int[] path;
    HamiltonPath[] hPath;
    int count = 0;
    int minWeight = Integer.MAX_VALUE;  
    static ArrayList<Node> nodes = new ArrayList<Node>();

    //List all possible Hamilton path with fixed starting point
    public void HamiltonPath(int[][] x, int start)
    {
        len = x.length;
        int[][] orgX = new int[len][len];
        for(int i = 0; i < len; i++)
            for(int j = 0; j < len; j++)
                orgX[i][j] = x[i][j];

        int row_len = 1; // = (N-1)!
        for (int i = 1; i < len; i++ ) row_len *= i;

            hPath = new HamiltonPath[row_len];
        for(int i = 0; i < row_len; i++)
        {
            hPath[i] = new HamiltonPath();
            hPath[i].path = new int[len+1];
        }
        path = new int[len+1];

        //Go through row(with given column)
        for(int i = start - 1; i < start; i++)
        {
            path[0] = path[len] = i + 1;
            findHamiltonpath(x, 0, i, 0);
        }
        
        minWeight(orgX);
        printHamiltonPaths();

        // dfsUsingStack(orgX, new Node(1));
    }

    private void findHamiltonpath(int[][] M, int x, int y, int l)
    {
        for(int i = x; i < len; i++){         //Go through row

            if(M[i][y] != 0)       //2 point connect
            {
                if(duplicate(path, i + 1))// if detect a point that already in the path => duplicate 
                    continue;

                l++;                //Increase path length due to 1 new point is connected 
                path[l] = i + 1;    //correspond to the array that start at 0, graph that start at point 1
                if(l == len - 1)    //Except initial point already count =>success connect all point
                {
                    count++;   
                    savePath(path);
                    l--;
                    continue;
                }

                M[i][y] = M[y][i] = 0;  //remove the path that has been get and
                findHamiltonpath(M, 0, i, l); //recursively start to find new path at new end point
                l--;                // reduce path length due to the failure to find new path         
                M[i][y] = M[y][i] = 1; //and tranform back to the inital form of adjacent matrix(graph)
            }
        }
         path[l+1] = 0;    //disconnect two point correspond the failure to find the..   
    }                      //possible hamilton path at new point(ignore newest point try another one)         

    public  void dfsUsingStack(int adjacency_matrix[][], Node node)
    {
        Stack<Node> stack = new Stack<Node>();
        stack.add(node);
        node.visited = true;
        while (!stack.isEmpty())
        {
            Node element = stack.pop();
            System.out.print(element.data + "\t");

            ArrayList<Node> neighbours = findNeighbours(adjacency_matrix, element);
            for (int i = 0; i < neighbours.size(); i++) {
                Node n = neighbours.get(i);
                if(n != null && !n.visited)
                {
                    stack.add(n);
                    n.visited = true;

                }
            }
        }
    }

    // find neighbors of node using adjacency matrix 
    // if adjacency_matrix[i][j]==1, then nodes at index i and index j are connected 
    public ArrayList<Node> findNeighbours(int adjacency_matrix[][], Node x) 
    {
        int nodeIndex = -1;
        ArrayList<Node> neighbours = new ArrayList<Node>();

        for (int i = 0; i < nodes.size(); i++)
        {
            if(nodes.get(i).equals(x))
            { 
                nodeIndex=i; break; 
            } 
        } 
        if(nodeIndex!=-1)
        { 
            for (int j = 0; j < adjacency_matrix[nodeIndex].length; j++)
            {
                if(adjacency_matrix[nodeIndex][j]==1) 
                { 
                    neighbours.add(nodes.get(j)); 
                }
            }
        } 
        return neighbours;
    }

    
    public void minWeight(int[][] x)
    {
        for(int i = 0; i < hPath.length; i++)
        {
            hPath[i].weight = 0;
            for(int j = 0; j < len; j++)
            {
                if(j == len-1)
                    hPath[i].weight += x[hPath[i].path[j]-1][hPath[i].path[0]-1];
                else
                    hPath[i].weight += x[hPath[i].path[j]-1][hPath[i].path[j+1]-1];
            }
        }

        for(int i = 0; i < hPath.length; i++)
            if(hPath[i].weight <= minWeight)
                minWeight = hPath[i].weight;
    }

    public void printHamiltonPaths()
    {
        System.out.println("\nHamilton path of graph: ");
        for(int i = 0; i < hPath.length; i++)
        {
            System.out.print((i + 1) + " : ");
            for(int v:hPath[i].path)
            {
                System.out.print(v + " ");
            }
            System.out.println(" : " + hPath[i].weight);
        }

        System.out.println("\n*** The shortest tour: ***");
        for(int i = 0; i < hPath.length; i++)
            if(hPath[i].weight == minWeight)
            {
                for(int j = 0; j <= len; j++)
                    System.out.print(hPath[i].path[j] + " ");

                System.out.println(" = " + hPath[i].weight);
            }
    }

    private void savePath(int[] x)
    {
        for(int i = 0; i < x.length; i++)
            hPath[count-1].path[i] = x[i];
    }

    //Detect duplicate point in Halmilton path
    private boolean duplicate(int[] x, int target)
    { 
        boolean t = false;                        
        for(int i:x){
            if(i == target){
                t = true;
                break;
            }
        }
        return t;
    }
}

