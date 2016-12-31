import java.util.Scanner;

public class decimalChecker {

	public static Float checkeDecimal(String input)
	{
		float number = 0;
		int input_length = input.length();
		int i;
		boolean isNegative = false;
		boolean isPositive = true;
		boolean isZero = true;
		boolean period = false;
		String num1 = "";
		String num2 = "";

		for(i = 0; i < input_length; i++)
		{
			if((input.charAt(i) >= '0' && input.charAt(i) <= '9') || 
				input.charAt(i) == '-' || input.charAt(i) == '+' || input.charAt(i) == '.')
			{
				if(i == 0)
				{
					if(input.charAt(i) == '-'){
						isNegative = true;
						isPositive = false;
					}
					else if(input.charAt(i) == '+')
						isNegative = false;
					else if(input.charAt(i) == '.')
						period = true;
				}
				else if((period && input.charAt(i) == '.') || 
						(isPositive && input.charAt(i) == '+') ||
						(isNegative && input.charAt(i) == '-'))
				{
					System.out.print("Not a decimal number\n");
					return null;
				}

				if(!period && input.charAt(i) == '.')
					period = true;

				if(input.charAt(i) != '.' && input.charAt(i) != '-' && input.charAt(i) != '+')
				{
					if(input.charAt(i) != '0')
						isZero = false;

					if(period){
						num2 += String.valueOf(input.charAt(i));
						//System.out.println("Num2: " + input.charAt(i));
					}
					
					else{
						num1 += String.valueOf(input.charAt(i));
						//System.out.println("Num1: " + input.charAt(i));
					}
				}
			}
			else
			{
				System.out.print("Not a decimal number\n");
				return null;
			}
			
		}

		// System.out.println();
		// System.out.println("Num1: " + num1);
		// System.out.println("Num2: " + num2);

		// print out the result
		if(isZero)
			System.out.print("Zero");
		else if(isNegative)
			System.out.print("Negative");
		else
			System.out.print("Positive");
		System.out.print(" decimal number\n");

		System.out.print(num1.length() + " digits before decimal: ");
		for(i = 0; i < num1.length(); i++)
		{
			System.out.print(String.valueOf(num1.charAt(i)));
			if(i != num1.length()-1)
				System.out.print(", ");
		}
		System.out.print("\n");

		System.out.print(num2.length() + " digits after decimal: ");
		for(i = 0; i < num2.length(); i++)
		{
			System.out.print(String.valueOf(num2.charAt(i)));
			if(i != num2.length()-1)
				System.out.print(", ");
		}
		System.out.print("\n");

		// Convert the number that before the decimal point.
		for(i = 0;i < num1.length(); i++)
			number += (num1.charAt(i) - '0') * Math.pow(10, num1.length() - i - 1);
		
		// Convert the number that after the decimal point.
		for(i = num2.length()-1;i >= 0 ; i--)
			number += (num2.charAt(i) - '0') * Math.pow(0.1, i + 1);
		
		// add the negative sign, if required.
		if(isNegative) number *= -1;

		return number;
	}

	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);

		System.out.println("+---------------------------------------------------------------------+");
		System.out.println("|---------------------------DECIMAL-CHECKER---------------------------|");
		System.out.println("+---------------------------------------------------------------------+");

		System.out.println("Enter any string : ");
		String input = "567a9db.23"; // in.next();
		System.out.println(input);
		System.out.println();

		System.out.println(decimalChecker.checkeDecimal(input));
	}
}
