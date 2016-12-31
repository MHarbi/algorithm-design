/***************************************************************************************
************ Defines Objects to be used by the non-recursive Mergesort routine *********
************ Programmed by Olac Fuentes                                        *********
************ Last modified September 12, 2011                                  ********* 
****************************************************************************************/

import java.util.*;

public class MergesortRecord
{	
	public boolean sorted; //Indicates if the first and second half of the array have already been sorted 
	public int first;
	public int last;
	
	public MergesortRecord(boolean s, int f, int l){
		sorted = s;
		first = f;
		last = l;
	}

	/*public  int getfirst()   {
			return first;
	}
	
	public boolean getsorted()   {
			return sorted;
	}
	
	public  int getlast()   {
			return last;
	}*/
	
	public void print(){
		System.out.println(sorted + " " + first + " " + last);
	}
}
