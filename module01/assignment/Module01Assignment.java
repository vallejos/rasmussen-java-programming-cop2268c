import java.util.Scanner;

public class Module01Assignment {

   public static void main(String[] args) {

      int num, temp, remainder, reverse = 0;
      Scanner input = new Scanner(System.in);

      System.out.print("Enter a Number: ");
      num = input.nextInt();
      
      temp = num;

      while (num > 0) {
      
         remainder = num % 10;
         
         reverse = reverse * 10 + remainder;
         
         num /= 10;
      
      } // /while
   
      System.out.println("Number: " + temp);
      System.out.println("Reverse: " + reverse);
   } // /main

} // /class
