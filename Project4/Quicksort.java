import java.util.*;

public class Quicksort
{
	static int[] S;

	private static void quicksort(int low, int high, int pivot_method) {

		if (low < high) {

			int pivotpoint = partition(low, high, pivot_method);
			quicksort(low, pivotpoint - 1, pivot_method);
			quicksort(pivotpoint + 1, high, pivot_method);

		}
	}

	public static int partition(int low, int high, int pivotMethod)
	{
		int i;
		int pivotitem = S[low]; // first element
		int j = low;

		// Choosing pivot item
		if(pivotMethod == 1) 		// last element
		{
			pivotitem = S[high];
			swap(low, high);
		}
		else if(pivotMethod == 2)	// median-of-three elements
		{
			int med = medianOf3(low, high);
			pivotitem = S[med];
			swap(low, med);
		}
		else if(pivotMethod == 3) 	// random element
		{
			int rnd = (int) (low + (Math.random()*((high-low)+1)));
			pivotitem = S[rnd];
			swap(low, rnd);
		}
		else if(pivotMethod == 4)	// median element							 
		{
			int med = (low + high) / 2;
			pivotitem = S[med];
			swap(low, med);
		}
		

		for(i = low + 1; i <= high; i++)
			if(S[i] < pivotitem){
				j++;
				swap(i, j);
			}

		int pivotpoint = j;
		swap(low, pivotpoint);

		return pivotpoint;
	}

	public static void swap(int i, int j)
	{
		int temp = S[i];
		S[i] = S[j];
		S[j] = temp;
	}

	public static int medianOf3(int left, int right)
	{
		int mid = (left + right) / 2;

		if (S[left] > S[mid])
			swap(left, mid);

		if (S[left] > S[right])
			swap(left, right);

		if (S[mid] > S[right])
			swap(mid, right);

		swap(mid, right-1);

		return right-1;
	}

	public static void printList(int[] l)
	{
		for(int i = 0; i < l.length; i++)
		  System.out.print(l[i] + "  ");
		System.out.println();
	}

	public static void main(String[] args)
	{
		long start = System.nanoTime();
		// int[] tmp = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}; // best
		// int[] tmp = {1, 2, 3, 6, 7, 8, 5, 4, 9, 10}; // average
		int[] tmp = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1}; // worst
		S = tmp;

		// pivot method equals: 0: first element, 
		//                      1: last element, 
		//                      2: median-of-three elements, 
		//                      3: random element,
		//                      4: median element
		int pivot_method = 4;

		System.out.println("\nThe Array is ");
		printList(S);

		quicksort(0, S.length-1, pivot_method);
		// mergesort3(S);

		System.out.println("The Sorted Array is ");
		printList(S);

		long end = System.nanoTime();
		long time=end-start;
		System.out.println("\nTime Performance = " + time + " ns\n");
	}
}