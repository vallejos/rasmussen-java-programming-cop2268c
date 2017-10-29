import java.util.*;
import java.io.*;

public class FileReadDemo
{
   public static void main(String[] args) throws FileNotFoundException
   {
      // use a File object to open a file for reading
      File file = new File("FriendList.txt"); // opens the file that is in parenthesis
      
      // create the Scanner object used to read data from the file
      Scanner inputFile = new Scanner(file);
      
      // use a while loop to read data from the file and display it to the console
      while(inputFile.hasNext())
      {
         // read in one line of the file at a time and store it into a String variable
         String a = inputFile.nextLine();
         
         // display the line of data into the console
         System.out.println(a);
      }
      
      // close the file after its processed
      inputFile.close();
      
   } // end main method
} // end FileReadDemo class