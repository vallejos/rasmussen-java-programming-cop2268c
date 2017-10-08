import java.util.Scanner;

/**
 * This program will reverse a number entered by the user using a while loop.
 * By Leonardo Vallejos
 */
public class Module01Assignment {

   public static void main(String[] args) {
      // declare needed variables
      int num, digit, reverse = 0;
      Scanner input = new Scanner(System.in);

      // get the number to be reversed
      System.out.print("Enter a Number: ");
      num = input.nextInt();

      // repeat until all the digits from the reverse number are calculated
      while (num != 0) {
         // get the first digit on the right
         digit = num % 10;

         // append the digit to the reversed number
         reverse = reverse * 10 + digit;
         
         // prepare num for next digit
         num /= 10;
      
      } // /while
   
      // display the results
      System.out.println("Reverse: " + reverse);

   } // /main

} // /class
