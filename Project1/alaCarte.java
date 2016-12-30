import java.util.Scanner;
import java.lang.*;

public class alaCarte {
	private static alaCarte ac = null;

	private String muliplyBy2(String number)
	{
		int number_len = number.length();
		int product_table[] = new int[number_len];
		int i;
		String result = "";

		for(i = number_len-1; i >= 0; i--)
		{
			product_table[i] += Character.getNumericValue(number.charAt(i)) * 2;

			if(product_table[i] > 9 && i > 0)
			{
				product_table[i - 1] = product_table[i] / 10;
				product_table[i] %= 10;
			}
		}
		result = ac.int2String(product_table);
		return result;
	}

	private String sum(String num1, String num2)
	{
		String total = "";
		int i;

		// making the two numbers the same length by adding 0's to the beggining of number.
    	while (num1.length() < num2.length())  num1 = "0" + num1;
    	while (num2.length() < num1.length())  num2 = "0" + num2;
 		
 		int result[] = new int[num1.length()];

 		for(i = num1.length()-1; i >= 0; i--)
 		{
 			result[i] += Character.getNumericValue(num1.charAt(i)) + Character.getNumericValue(num2.charAt(i));
 			if(result[i] > 9 && i > 0)
			{
				result[i - 1] = result[i] / 10;
				result[i] %= 10;
			}
 		}
		
		total = int2String(result);
   		return total;
	}

	private String divideBy2(String dividend)
	{
		String quotient = "";
		int divisor = 2;
		int curr_dividend = Character.getNumericValue(dividend.charAt(0));
		int i;

		for(i = 0; i < dividend.length(); i++)
		{
			quotient = quotient + (curr_dividend / divisor);
			
			if(i < dividend.length() - 1)
				curr_dividend = Integer.parseInt(String.valueOf(curr_dividend % divisor) + Character.getNumericValue(dividend.charAt(i+1)));
		}
		return quotient;
	}

	private boolean isNumberZero(String number)
	{
		int number_len = number.length();
		int i;

		for(i = 0; i < number_len; i++)
		{
			if(Character.getNumericValue(number.charAt(i)) > 0)
				return false;
		}
		return true;
	}

	private String int2String(int number[])
	{
		String result = "";
		int i;
		for(i = 0; i < number.length; i++) result = result + number[i];
		// omit left zero(s)
		while(Character.getNumericValue(result.charAt(0)) == 0)
		{
		 result = result.substring(1);
		}
		return result;
	}

	public String alaCarteMultiplication(String x, String y)
	{
		String result = "";
		boolean is_x_neg = false;
		boolean is_y_neg = false;
		
		if(x.charAt(0) == '-')
		{
			is_x_neg = true;
			x = x.substring(1);
		}
		if(y.charAt(0) == '-')
		{
			is_y_neg = true;
			y = y.substring(1);
		}

		// product = 0, if one of the numbers is zero
      	if(ac.isNumberZero(x) || ac.isNumberZero(y))
        	return "0";

		while(!ac.isNumberZero(x))
		{
			if(Character.getNumericValue(x.charAt(x.length()-1)) % 2 != 0) // if x is odd
				result = ac.sum(result, y);

			x = ac.divideBy2(x);
			y = ac.muliplyBy2(y);
		}
		
		if((is_x_neg && !is_y_neg) || (!is_x_neg && is_y_neg))
		{
			result = "-" + result;
		}

		return result;
	}

	public static void main(String[] args)
	{
		ac = new alaCarte();
		Scanner in = new Scanner(System.in);
		System.out.println("+--------------------------------------------------------------------+");
		System.out.println("|----------------------ALA-CARTE-MULTIPLICATION----------------------|");
		System.out.println("+--------------------------------------------------------------------+");

		System.out.println("Enter the first number : ");
		String x = in.next();
		System.out.println("Enter the second number: ");
		String y = in.next();
		System.out.println();

		System.out.println("\nResult: " + ac.alaCarteMultiplication(x, y));
	}
}