import java.util.*;

public class BPP {

    public static int binPacking(int[] a)
    {
        a = sort(a); // sort the items in nonincreasing order
        int size = 6;
        ArrayList<Integer> binValues = new ArrayList<Integer>();
        binValues.add(size);

        for (int i = 0; i < a.length; i++)
        {
            boolean packed = false;
            for (int j = 0; j < binValues.size(); j++)
            {
                if (binValues.get(j) - a[i] >= 0) // if the item fits in the bin
                {
                    binValues.set(j, binValues.get(j) - a[i]); // pack it in the bin
                    packed = true;
                    break;
                }
            }
            if(!packed)
            {
                binValues.add(size); // start a new bin
                int last = binValues.size()-1;
                // place the item in the new bin
                binValues.set(last, binValues.get(last) - a[i]);
            }
        }

        return binValues.size();
    }

    public static int[] sort(int[] l)
    {
        // Bubble Sort descending order
        for (int i = 0; i < l.length; i++){
            for (int j = 0; j < l.length - 1; j++){
                if (l[j] < l[j + 1])
                {
                    l[j] = l[j] + l[j + 1];
                    l[j + 1] = l[j] - l[j + 1];
                    l[j] = l[j] - l[j + 1];
                }
            }
        }
        return l;
    }

    public static void main(String[] args)
    {
        System.out.println("BIN - PACKING Algorithm 1D Objects(First Fit Decreasing)");

        int n = 9;
        int[] a = {4, 1, 2, 5, 3, 2, 3, 6, 3};
        int size = 6;
        int binCount = binPacking(a);

        System.out.println("Number of bins required using first fit decreasing algorithm is: " + binCount);
    }
}