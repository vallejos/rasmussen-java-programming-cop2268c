/**
 * Author: Leonardo Vallejos <leonardofabian.hernandezvallej@smail.rasmussen.edu>
 * Date: October 29, 2017
 * Purpose: Module 04 Lab Assignment for COP2268C Java Programming course at Rasmussen College.
 * Description: User inputs 10 clients with id #, first name, last name, and account balance 
 *              and outputs to a sequential file.
 *              Then read the data from that output file and print the data.
 *              The format for the output should be id#; first name; last name; account balance.
 */

import java.util.*;     // keyboard input
import java.io.*;       // file i/o

public class SequentialFileOutput
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException
    {
        // variables
        int numClients = 10; // set max number of clients
        String idInput = "", balanceInput = "", firstName = "", lastName = "";
        int id = 0;
        double balance = -1.0;
        String fileName = "dbClients.txt";
        ArrayList<Client> clients = new ArrayList<Client>();
        ArrayList<Client> dbClients = new ArrayList<Client>();

        Scanner input = new Scanner(System.in);

        // get client's data
        for(int i = 1; i <= numClients; i++)
        {
            System.out.println("Enter data for Client #" + i);

            // get client id
            while(idInput.isEmpty() || id == 0)
            {
                System.out.print("\tID#: ");
                idInput = input.nextLine();

                id = (!idInput.isEmpty()) ? Integer.parseInt(idInput) : 0;

                // validate input
                if (idInput.isEmpty() || id == 0)
                {
                    System.out.println("> ERROR: ID must be positive integer. Try again.");
                }
            } // while

           
            // get client first name
            while(firstName.isEmpty())
            {
                System.out.print("\tFirst Name: ");
                firstName = input.nextLine();

                // validate input
                if (firstName.isEmpty())
                {
                    System.out.println("> ERROR: First Name cannot be blank. Try again.");
                }
            } // while

            // get client last name
            while(lastName.isEmpty())
            {
                System.out.print("\tLast Name: ");
                lastName = input.nextLine();

                // validate input
                if (lastName.isEmpty())
                {
                    System.out.println("> ERROR: Last Name cannot be blank. Try again.");
                }
            } // while

            // get client account balance
            while(balanceInput.isEmpty() || balance < 0)
            {
                System.out.print("\tAccount Balance: ");
                balanceInput = input.nextLine();

                balance = (!balanceInput.isEmpty()) ? Double.parseDouble(balanceInput) : 0;

                // validate input
                if (balanceInput.isEmpty() || balance < 0)
                {
                    System.out.println("> ERROR: Invalid balance. Try again.");
                }
            } // while
            
            // create Client object and add the object to the clients ArrayList
            clients.add(new Client(id, firstName, lastName, balance));

            // clean up the input variables for next client
            idInput = "";
            balanceInput = "";
            firstName = "";
            lastName = "";
            
        } // end for
        
        
        // write to file
        PrintWriter outputFile = new PrintWriter(fileName);

        for(Client client : clients)
        {
            // save the content with a known format, each field separated by semicolon
            String content = client.getId() + ";" +
                                client.getFirstName() + ";" +
                                client.getLastName() + ";" + 
                                client.getBalance() + ";";
            outputFile.println(content);
        }

        outputFile.close(); // close the file

        System.out.print("\nFile Saved. Press <enter> to read the file and display its content...");
        input.nextLine();

        // read from file
        File file = new File(fileName); // opens the file
      
        Scanner inputFile = new Scanner(file); // create scanner object to read from file
      
        // use a while loop to read data from the file and store into dbClients list
        while(inputFile.hasNext())
        {
            // read in one line of the file at a time and store each field into a String array
            String[] record = inputFile.nextLine().split(";");

            // create Client object and add the object to the dbClients ArrayList
            dbClients.add(new Client(Integer.parseInt(record[0]), 
                                        record[1], 
                                        record[2],
                                        Double.parseDouble(record[3])));
        }

        inputFile.close(); // close the file when all content has been loaded
        
        // output results
        for(Client client : dbClients)
        {
            String content = client.getId() + "; " +
                                client.getFirstName() + "; " +
                                client.getLastName() + "; " + 
                                client.getBalance() + ".";
            System.out.println(content);
        }
        
    } // end main
    
} // end class
