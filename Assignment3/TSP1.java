import java.util.*;
import java.lang.*;

public class TSP1
{
    static int len;
    static int[] path;
    int[][] allPaths;
    static int count = 0;
    int minWeight = Integer.MAX_VALUE;
    static int[] w;

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
    allPaths = new int[row_len][len+1]; 
    path = new int[len+1];

    //Go through row(with given column)
    for(int i = start - 1; i < start; i++)
    {
        path[0] = path[len] = i + 1;
        findHamiltonpath(x, 0, i, 0);
    }

    w = new int[row_len];

    minWeight(orgX);
    findTSPpaths();
}

private void findHamiltonpath(int[][] M, int x, int y, int l)
{
    for(int i = x; i < len; i++){         //Go through row

        if(M[i][y] != 0){      //2 point connect

            if(duplicate(path, i + 1))// if detect a point that already in the path => duplicate 
                continue;

            l++;                //Increase path length due to 1 new point is connected 
            path[l] = i + 1;    //correspond to the array that start at 0, graph that start at point 1
            if(l == len - 1){  //Except initial point already count =>success connect all point
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
     path[l+1]=0;    //disconnect two point correspond the failure to find the..   
}


public void minWeight(int[][] x)
{
    for(int i = 0; i < allPaths.length; i++)
    {
        w[i] = 0;
        for(int j = 0; j < len; j++)
        {
            if(j == len-1)
                w[i] += x[allPaths[i][j]-1][allPaths[i][0]-1];
            else
                w[i] += x[allPaths[i][j]-1][allPaths[i][j+1]-1];
        }
    }

    for(int i = 0; i < allPaths.length; i++)
        if(w[i] <= minWeight)
            minWeight = w[i];
}

public void printHamiltonPaths()
{
    System.out.println("\nHamilton path of graph: ");
    for(int i = 0; i < allPaths.length; i++)
    {
        System.out.print((i + 1) + " : ");
        for(int v:allPaths[i])
        {
            System.out.print(v + " ");
        }
        System.out.println(" : " + w[i]);
    }

    System.out.println("\n*** The shortest tour: ***");
    for(int i = 0; i < allPaths.length; i++)
        if(w[i] == minWeight)
        {
            for(int j = 0; j <= len; j++)
                System.out.print(allPaths[i][j] + " ");

            System.out.println(" = " + w[i]);
        }
}

public void findTSPpaths()
{
    System.out.println("\n*** The shortest tour: ***");
    for(int i = 0; i < allPaths.length; i++)
        if(w[i] == minWeight)
        {
            for(int j = 0; j <= len; j++)
                System.out.print(allPaths[i][j] + " ");

            System.out.println(" = " + w[i]);
        }
}

private void savePath(int[] x)
{
    for(int i = 0; i < x.length; i++)
        allPaths[count-1][i] = x[i];
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

