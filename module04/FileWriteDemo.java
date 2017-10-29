import java.util.*;  // allows for use of the Scanner Class
import java.io.*;    // needed for file I/O

public class FileWriteDemo
{
   // main method
   public static void main(String[] args) throws IOException
   {
      // variables
      String filename;
      String name;
      int numNames;
      
      // Scanner object that will be used to get user input
      Scanner keyboard = new Scanner(System.in);
      
      // ask for the number of entries in the file
      System.out.print("How many names are on this list? ");
      numNames = keyboard.nextInt(); 
      
      // validate the number of names
      while(numNames < 0)
      {
          // ask for the number of entries in the file
         System.out.print("ERROR: Invalid input. How many names are on this list? ");
         numNames = keyboard.nextInt(); 
      }
      
      // use a nextLine to consume the extra newLine character
      keyboard.nextLine();
      
      // enter the name of the file 
      System.out.print("Enter the name of the file: ");
      filename = keyboard.nextLine();
      
      // validate the name of the file entered
      while(!filename.endsWith(".txt"))
      {
         System.out.print("ERROR: Invalid input. Enter the name of the file: ");
         filename = keyboard.nextLine();
      }
      
      // open the file using PrintWriter
         // file is created in the same directory as the java source code
      PrintWriter outputFile = new PrintWriter(filename);
      
      // use a loop to get the data and write the data to a file
      for(int i = 1; i <= numNames; i++)
      {
         // ask the user to enter the name on the list
         System.out.print("Enter name #" + i + ": ");
         name = keyboard.nextLine();
         
         // validate the input of the name
         while(name.isEmpty())
         {
            System.out.print("Error: Name cannot be empty. Enter name #" + i + ": ");
            name = keyboard.nextLine();
         } // end while loop
         
         // write the name to the file
         outputFile.println(name);
         
      } // end for loop
      
      // close the file
      outputFile.close(); // saves the information 
      System.out.println("Data Written to the file. ");
      
   } // end main method

} // end FileWriteDemo class