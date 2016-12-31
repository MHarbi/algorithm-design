/**********************************************************************
************ Non-recursive version of Mergesort using a stack *********
***********************************************************************/

import java.util.*;

public class nonRecursiveMergesort
{	
	private static class Record
	{
		public boolean sorted; //Indicates if the first and second half of the array have already been sorted 
		public int first;
		public int last;
		
		public Record(boolean s, int f, int l){
			sorted = s;
			first = f;
			last = l;
		}

		public void print(){ 
			System.out.println(sorted + " " + first + " " + last); 
		}
	}

	public static void mergesort(int[] S)
	{
		Stack<Record> stack = new Stack<Record>();
		Record m = new Record(false, 0, S.length-1);
		
		stack.push(m);
		
		while (!stack.empty())
		{
			/* Debugging code */
			// System.out.println("Stack size is: " + stack.size());
			// System.out.print("Top of stack is: ");
			// stack.peek().print();
					
			m = stack.pop();
			// m.print(); //for debugging purposes
			if (m.sorted){	//If first and second halves of array have been sorted, merge them
				merge(S, m.first, m.last);
			}
			else
			{
				if (m.first < m.last){
					int mid = (m.first + m.last)/2;
					stack.push(new Record(true, m.first, m.last));
					stack.push(new Record(false, m.first, mid));
					stack.push(new Record(false, mid+1, m.last));
				}
			}
			//for debugging purposes	
			// System.out.print("Current array is: ");
			// printArray(S); 
			// System.out.println();
		}
	}

	public static void merge(int[] S, int first, int last)
	{
		int[] temp = new int[last-first+1];
		if (first < last)
		{
			int f1 = first;
			int mid = (first + last)/2;
			int f2 = mid + 1;
						
			for(int i = 0; i< temp.length; i++){
				if (f1 > mid) 			temp[i] = S[f2++];				
				else if (f2 > last)		temp[i] = S[f1++];
				else if (S[f1] < S[f2])	temp[i] = S[f1++];
				else					temp[i] = S[f2++];
			
			//for debugging purposes
			// System.out.println(i+" "+temp[i]); 
			}
			for(int i = 0; i < temp.length; i++)
				S[first + i] = temp[i];
		}
	}
	
	/*public static int [] generateRandomArray(int n, int max){
		Random generator = new Random();
		int []randArray = new int[n];
		for(int i=0;i<n;i++)
			randArray[i] = generator.nextInt(max);
		return randArray;
	}*/
	
	public static void printArray(int [] S)
	{
		for(int i = 0; i < S.length; i++)
			System.out.print(S[i] + "  ");
		System.out.println();
	}

	public static void main(String[] args)
	{
		long start = System.nanoTime();
		
		// int[] S = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}; // best
		// int[] S = {1, 2, 3, 6, 7, 8, 5, 4, 9, 10}; // average
		int[] S = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1}; // worst

		//int [] S = generateRandomArray(50,100);

		System.out.println("The Array is ");
		printArray(S);

		mergesort(S);
		
		System.out.println("The Sorted Array is ");
		printArray(S);
		
		long end = System.nanoTime();
		System.out.println("\nTime Performance = " + (end-start) + " ns");
	}
}
