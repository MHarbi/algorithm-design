import java.util.*;

public class FindKthSmallest
{
  static int[] S;
  static int pivotpoint = 0;
  public static int selection(int low, int high, int k)
  {
    if(low == high)
      return S[low];
    else {
      pivotpoint = partition(low, high, pivotpoint);
      if(k == pivotpoint){
        return S[pivotpoint];
      }
      else if(k<pivotpoint){
        return selection(low, pivotpoint-1, k);
      }
      else{
        return selection(pivotpoint+1, high, k);
      }
    }
  }

  public static int partition(int low,int high,int pivot){
    pivot = low;
    int pivotitem = S[low];
    int j=low;
    for(int i=low+1;i<=high;i++){
      if(S[i]<pivotitem){
        j++;
        int temp=S[i];
        S[i]=S[j];
        S[j]=temp;
      }
    }
    pivot=j;
    int temp=S[low];
    S[low]=S[pivot];
    S[pivot]=temp;
    return pivot;
  }

  public static int select2(int n, int[] S, int k){
    return selection2(S, 0, n, k);
  }

  public static int selection2(int[] S, int low, int high, int k){
    if(low == high){
      // System.out.println("k: "+low+", p: "+high);
      return S[low];
    }
    else {
      int pivotpoint = partition2(S, low, high);
      if(k == pivotpoint){
        // System.out.println("k: "+k+", p: "+pivotpoint);
        return S[pivotpoint];
      }
      else if(k < pivotpoint){
        return selection2(S, low, pivotpoint-1, k);
      }
      else{
        return selection2(S, pivotpoint+1, high, k);
      }
    }
  }

  public static int partition2(int[] S, int low, int high){
    int arraysize = high - low + 1;
    int r = (int)Math.ceil(arraysize/5);
    int i, j, mark, first, last;
    int pivotitem;

    mark = 0;
    int [] T = new int[r+1];

    for(i = 1; i <= r; i++){
      first = low + 5 * i - 5;
      last = Math.min(low + 5 * i - 1, arraysize);
      // System.out.println("Low: " + low + ", High: " + high);
      // System.out.println("1st: " + first + ", Last: " + last);
      T[i-1] = median(S, first, last); 
      // T[i-1] = findMedian(S, first, last);
    }

    pivotitem = select2(r, T, (r+1)/2);
    j = low;
    // System.out.println("P: " + pivotitem);

    for(i = low; i < high; i++){
      if(S[i] == pivotitem){
        S = swap(S, i, j);
        mark = j;
        j++;
      }
      else if(S[i] < pivotitem){
        S = swap(S, i, j);
        j++;
      }
    }
    int pivotpoint = j;
    S = swap(S, mark, pivotpoint);

    return pivotpoint;
  }

  /*public static int findMedian(int[] S, int first, int last){
    Arrays.sort(S);
    int length = last-first+1;
    int middle = length/2+first;
    int medianValue;
    if (length%2 == 1)
      medianValue = S[middle];
    else
      medianValue = (S[middle-1] + S[middle]) / 2;

    return medianValue;
  }*/

  public static int median(int[] list, int start, int last)
  {
      int[] copy = list.clone ();
      Arrays.sort (copy);
      return copy[(start + last) / 2];
  }

  public static int selection3(int low, int high, int k){

    if(low == high){
      return S[low];
    }
    else {
      pivotpoint = partition3(low, high);
      if(k == pivotpoint){
        return S[pivotpoint];
      }
      else if(k < pivotpoint){
        return selection3(low, pivotpoint-1, k);
      }
      else{
        return selection3(pivotpoint+1, high, k);
      }
    }
  }

  public static int partition3(int low,int high){
    int i,j;
    int pivotitem, pivotpoint;
    int randspot = (int) (low + (Math.random()*((high-low)+1)));
    pivotitem = S[randspot];
    S = swap(S, low, randspot);
    j = low;

    for(i = low+1; i <= high; i++){
      if(S[i] < pivotitem){
        j++;
        S = swap(S, i, j);
      }
    }
    pivotpoint = j;
    
    S = swap(S, low, pivotpoint);

    return pivotpoint;
  }

  public static int[] swap(int[] l, int i, int j)
  {
    int temp = l[i];
    l[i] = l[j];
    l[j] = temp;
    return l;
  }

  public static void main(String[] args) 
  {
    long start = System.nanoTime();
    // int[] tmp = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}; // best
    // int[] tmp = {1, 2, 3, 6, 7, 8, 5, 4, 9, 10}; // average
    int[] tmp = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1}; // worst
    S = tmp;
    int k = 3;

    System.out.println("\nThe Array is ");
    for(int i = 0; i < S.length; i++)
      System.out.print(S[i] + "  ");
    System.out.println();

    // int b = selection(0, tmp.length-1, k-1); // Algorithm 8.5: Selection
    // int b = select2(tmp.length, S, k);    // Algorithm 8.6: Selection Using the Median
    int b = selection3(0, tmp.length-1, k-1); // Algorithm 8.7: ProbabiIistic Selection

    System.out.println("\nThe " + k +"th smallest key is " + b);
    long end = System.nanoTime();
    long time=end-start;
    System.out.println("\nTime Performance = " + time + " ns\n");
  }
}
