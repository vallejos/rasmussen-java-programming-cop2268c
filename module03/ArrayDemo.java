/*
   this demo class is going to demonstrate Array Basics
*/

import java.util.*; // needed for ArrayLists, Scanner and other fun things

public class ArrayDemo
{
   public static void main(String[] args)
   {
      // general structure of an Array
      /*
         (data type)[] (name of the array) = new (dataType)[size of the array]
         
            example array that stores 10 integers:
               int[] numbers = new int[10];
               int numbers[] = new int[10];
             // implicit declaration - sets up 10 empty slots to hold integers
             
             int numbers[] = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
             // explicit declaration
             
             // arrays are broken into subscripts aka elements aka indexes
             start at element 0 and end at element n-1 (where n is the number of elements in the array aka the size)
             
             if you try to access an element that is greater than or equal to n, you will get an "index out of bounds error"
             
      */
      
      // create Scanner Object
      Scanner keyboard = new Scanner(System.in);
      
      // variable to hold the number of elements in the array
      int size;
      
      // ask the user for the size of the array
      System.out.print("Enter the size of the array (Must be at least 3 elements): ");
      // get input of the size of the array
      size = keyboard.nextInt();
      
      // validate the input of the size of the array
      while(size < 3)
      {
          // ask the user for another size of the array
         System.out.print("Enter the size of the array (Must be at least 3 elements): ");
         // get another input of the size of the array
         size = keyboard.nextInt();
      }
      
      // array to hold user defined doubles
      double values[] = new double[size];
      
      // each index acts as its own variable
      /*values[0] = 1.0; // updates the value of the first index to 1.0
      values[1] = 2.0; // updates the second element to 2.0
      values[2] = values[0] + values[1]; // value of the third subscript is the sum of the first two elements
      
      // print the value of an individual element
      System.out.println("The value at index 2: " + values[2]);
      */
      
      // use a for loop to populate the array
      for(int i = 0; i < values.length; i++)
      {
         // ask for the value of the element
         System.out.print("Enter the value for index " + i + ": ");
         // get the user input of the value at that index
         values[i] = keyboard.nextDouble();
      }
      
      System.out.println("-------------------------------------");
      
      // use a for loop to display the elements in the array
      for(int i = 0; i < values.length; i++)
      {
         // print out the elements on separate lines
         System.out.println("values[" + i + "]: " + values[i]);
      }
      
      // to find the total of all of the elements in the array
      double total = 0.0; // running total aka an accumulator
      
      for(int i = 0; i < values.length; i++)
      {
         total += values[i]; // on each pass, total is updated with the current value in the array
      }
      
      // display the total
      System.out.println("Total of all elements: " + total);

      // calculate the average
      System.out.println("Average: " + total / values.length);
      
      // to find the highest value in the array
      double highest = values[0];
      
       for(int i = 1; i < values.length; i++)
      {
         // compare the highest value to the current element in the array
            // if the current element is larger than the current highest number,
               // that number becomes the new highest number
               if(values[i] > highest)
               {
                  highest = values[i];
               }
      }
      
      // to find the lowest value in the array
      double lowest = values[0];
      
       for(int i = 1; i < values.length; i++)
      {
         // compare the lowest value to the current element in the array
            // if the current element is smaller than the current smallest number,
               // that number becomes the new lowest number
               if(values[i] < lowest)
               {
                  lowest = values[i];
               }
      }
      
      // display the highest value
      System.out.println("Highest value in the array: " + highest);

      // display the lowest value
      System.out.println("Lowest value in the array: " + lowest);
      
   } // end main method
   
} // end ArrayDemo class