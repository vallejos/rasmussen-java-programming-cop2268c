import java.util.*;  // allows for use of the Scanner Class
import java.io.*;    // needed for file I/O

public class ClientFileWriteDemo
{
   public static void main(String[] args) throws FileNotFoundException
   {
       // variables
      String filename, id, first, last;
      double balance;
      
      // create ArrayList
      ArrayList<Client> clients = new ArrayList<Client>();
      
      // Scanner object that will be used to get user input
      Scanner keyboard = new Scanner(System.in);
      
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
      
       // use a loop to get the data
      for(int i = 1; i <= 3; i++)
      {
          // ask the user to enter the ClientID
         System.out.print("Enter Client ID#: ");
         id = keyboard.nextLine();
         
          // validate the input of the id
         while(id.isEmpty())
         {
             // ask the user to enter the ClientID
            System.out.print("ERROR: ID cannot be blank. Enter Client ID#: ");
            id = keyboard.nextLine();
         }
         
         // ask the user to enter the First name
         System.out.print("Enter First Name: ");
         first = keyboard.nextLine();
         
          // validate the input of the first name
         while(first.isEmpty())
         {
             // ask the user to enter the first name
            System.out.print("ERROR: First Name cannot be blank. Enter First Name: ");
            first = keyboard.nextLine();
         }
         
          // ask the user to enter the Last name
         System.out.print("Enter Last Name: ");
         last = keyboard.nextLine();
         
          // validate the input of the Last name
         while(last.isEmpty())
         {
             // ask the user to enter the last name
            System.out.print("ERROR: Last Name cannot be blank. Enter Last Name: ");
            last = keyboard.nextLine();
         }
         
         // ask for the account balance
         System.out.print("What is " + first + " " + last + "'s account balance? ");
         balance = keyboard.nextDouble(); 
      
         // validate the account balance
         while(balance < 0)
         {
             // ask for the account balance
            System.out.print("ERROR: Invalid balance. What is " + first + " " + last + "'s account balance? ");
            balance = keyboard.nextDouble(); 
          }
          
          // create Client object and add the object to the clients ArrayList
          clients.add(new Client(id, first, last, balance));
          
           // use a nextLine to consume the extra newLine character
           keyboard.nextLine();

      } // end for loop
      
      // use the ArrayList to write the information to the output file
      for(Client c : clients)
      {
         outputFile.println(c.toString());
      }
      
      // close the file
      outputFile.close();
      System.out.println("Data written to the file successfully.");

      // use a File object to open a file for reading
      File file = new File(filename); // opens the file that is in parenthesis
      
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

} // end ClientFileWriteDemo class