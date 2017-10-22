import java.util.Scanner;

/**
 * This program will find the lowest and highest values in an array.
 * By Leonardo Vallejos
 */
public class Module03Assignment {

   public static void main(String[] args) {
      // declare needed variables
      double[] data = new double[10];
      double lowest, highest;
      Scanner input = new Scanner(System.in);

      // get the numbers
      for (int i=0; i<10; i++) {
         System.out.print("Enter a Number: ");
         data[i] = input.nextDouble();
      }

      // set initial values for lowest and highest
      lowest = data[0];
      highest = data[0];
      
      // loop through the array to get the lowest and highest values
      for (int i=1; i<10; i++) {
         lowest = (data[i] < lowest) ? data[i] : lowest;
         highest = (data[i] > highest) ? data[i] : highest;
      }
   
      // display the results
      System.out.println("Largest number is: " + highest);
      System.out.println("Smallest number is: " + lowest);

   } // /main

} // /class
