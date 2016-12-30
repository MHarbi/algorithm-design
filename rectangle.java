import java.util.Scanner;

public class rectangle {
   private static rectangle r = null;

   public static int[] splitNumber(String num)
   {
      int count = num.length();          

      int[] digits = new int[count];

   	for(int i=count-1; i>=0; i--)
   	{
         digits[i] = Character.getNumericValue(num.charAt(i));
   	}
   	return digits;
   }

   private String sum(String num1, String num2)
   {
      int i;

      // making the two numbers the same length by adding 0's to the beggining of number.
      while (num1.length() < num2.length())  num1 = "0" + num1;
      while (num2.length() < num1.length())  num2 = "0" + num2;
      
      int sum[] = new int[num1.length()];

      for(i = num1.length()-1; i >= 0; i--)
      {
         sum[i] += Character.getNumericValue(num1.charAt(i)) + Character.getNumericValue(num2.charAt(i));
         if(sum[i] > 9 && i > 0)
         {
            sum[i - 1] = sum[i] / 10;
            sum[i] %= 10;
         }
      }
      
      return r.int2String(sum);
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
      for(i = 0; i < number.length; i++) 
         result = result + number[i];
      // omit left zero(s)
      while(Character.getNumericValue(result.charAt(0)) == 0)
      {
         result = result.substring(1);
      }
      return result;
   }
   
   public String rectangleMultiplication(String x, String y)
   {
      String result = "";
      boolean is_x_neg = false;
	   boolean is_y_neg = false;
      
      // get rid of the negative sign, and change the corresponding flag to add them later.
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
      if(r.isNumberZero(x) || r.isNumberZero(y))
         return "0";
      
      int i, j;
      int[] x_digits = rectangle.splitNumber(x);
      int[] y_digits = rectangle.splitNumber(y);
      int x_len = x_digits.length;
      int y_len = y_digits.length;
      int[][] table_mult = new int[y_len][x_len * 2];
      
      // multiplying each two-corresponding digits and storing the result in a table
      for(i = 0; i < y_len; i++)
         for(j = 0; j < x_len; j++) {
            int tmp_mult = y_digits[i] * x_digits[j];
            table_mult[i][(j*2)] = tmp_mult / 10;
            table_mult[i][(j*2)+1] = tmp_mult % 10;
         }
         
      // START: print out the table multiplication result
      /*for(j = 0; j < x_len; j++)
         System.out.print(" " + x_digits[j] + "  | ");

      System.out.println();
      
      for(i = 0; i < y_len; i++) {
         for(j = 0; j < x_len; j++) {
            System.out.print(table_mult[i][(j*2)] + "/" + table_mult[i][(j*2)+1] + " | ");
         }
         System.out.println(y_digits[i]);
      }
      System.out.println();*/
      // END: print out

      // START: summing up and store the result in array.
      int[] result_arr = new int[x_len + y_len];
      int sum;
      int row;
      int col;
      boolean iteration;
      
      // first loop (zigzag traversal) in the sum operation
      for(i = y_len-1; i >= 0; i--) {
      
         sum = 0;
         row = i;
         col = (x_len * 2) - 1;
         iteration = false;
         
         while(row < y_len && col >= 0) {
            sum += table_mult[row][col];
            //System.out.print(table_mult[row][col] + " ");
            if(!iteration)
               row++;
            col--;
            iteration = !iteration;
         }
         //System.out.println();
         result_arr[(x_len + i)] = sum;
      }
      
      // second loop (zigzag traversal) in the sum operation
      for(j = x_len-1; j >= 0; j--) {
      
         sum = 0;
         col = (j * 2);
         row = 0;
         iteration = true;

         while(row < y_len && col >= 0) {
            sum += table_mult[row][col];
            //System.out.print(table_mult[row][col] + " ");
            if(!iteration)
               row++;
            col--;
            iteration = !iteration;
         }
         //System.out.println();
         result_arr[j] = sum;
      }
      
      //System.out.println();
      
      // sum the carry out / leftover with the next number.
      for(i = result_arr.length - 1; i >= 0; i--)
      {
         if(result_arr[i] > 10)
         {
            result_arr[i-1] = result_arr[i-1] + (result_arr[i] / 10);
            result_arr[i] = result_arr[i] % 10;
         }
      }

      result = r.int2String(result_arr);
      
      // make sure the negative sign exist if needed
      if((is_x_neg && !is_y_neg) || (!is_x_neg && is_y_neg))
      {
         result = "-" + result;
      }
      
      return result;
   }

	public static void main(String[] args)
   {
      r = new rectangle();
      Scanner in = new Scanner(System.in);

      System.out.println("+--------------------------------------------------------------------+");
      System.out.println("|----------------------RECTANGLE-MULTIPLICATION----------------------|");
      System.out.println("+--------------------------------------------------------------------+");

      System.out.println("Enter the first number : ");
      String x = in.next();
      System.out.println("Enter the second number: ");
      String y = in.next();
      System.out.println();

      System.out.println("\nResult: " + r.rectangleMultiplication(x, y));
	}
}