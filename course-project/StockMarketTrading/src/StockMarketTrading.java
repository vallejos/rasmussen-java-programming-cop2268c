import java.util.*;
import java.sql.*; // needed for JDBC classes

public class StockMarketTrading
{

    /**
     * Asks for Stock data and adds the stock to the list
     * @param stocks
     */
    public static void addStock(StockList stocks)
    {
        Scanner input = new Scanner(System.in);
        String name, description;

        System.out.print("Enter the Stock ID: ");
        name = input.nextLine();

        System.out.print("Enter the Stock Description: ");
        description = input.nextLine();

        // add the new stock to the array
        stocks.add(new StockData(name, description));

        System.out.println("Stock added.");
    }

    /**
     * main function
     * @param args
     */
    public static void main(String[] args)
    {
        // define some variables
        Scanner input = new Scanner(System.in);
        String repeat;
        StockList stocks = new StockList();
        ArrayList<Client> dbClients = new ArrayList<Client>();

        
        // connect to the database (keeping this commented out for now)
/*
        // create a constant for the database URL
        final String DB_URL = "jdbc:derby:StockMarketDatabase;create=true";

        try
        {
            // create a connection to the database
            Connection conn = DriverManager.getConnection(DB_URL);

            // Statement object that uses the Connection stream to execute SQL statements
            Statement stmt = conn.createStatement();

            // read data from db
            ResultSet result = stmt.executeQuery("SELECT * FROM Clients");

            // display the results
            while(result.next())
            {
                // print the data to screen
                System.out.print(result.getString("id") + ";");
                System.out.print(result.getString("firstName") + ";");
                System.out.print(result.getString("lastName") + ";");
                System.out.println(result.getDouble("balance"));
        
                // or create objects and add them to the clients array list
                dbClients.add(new Client(Integer.parseInt(result.getString("id")), 
                                            result.getString("firstName"), 
                                            result.getString("lastName"),
                                            Double.parseDouble(result.getString("balance"))));
        
            }

            // write data to db
            stmt.execute("INSERT INTO Clients VALUES(10, 'Jon', 'Snow', 5.50)");
            stmt.execute("INSERT INTO Clients VALUES(20, 'Tyrion', 'Lannister', 99.99)");
            stmt.execute("INSERT INTO Clients VALUES(30, 'Daenerys', 'Targaryen', 10)");

            // close the connection
            conn.close();

        }
        catch(SQLException ex)
        {
            // if there is an error, print the error
            System.out.println("ERROR: " + ex.getMessage());
        }
*/

        // preload stocks
        stocks.preloadStocks();
        System.out.println("\nSome Stocks have been preloaded.");

        // ask to add more stocks or not
        System.out.print("Add more? <yes/no>: ");
        String addMore = input.nextLine();

        if (addMore.equalsIgnoreCase("yes"))
        {
            do
            {
                // ask for new stock data
                addStock(stocks);

                System.out.print("Add more? <yes/no>: ");
                repeat = input.nextLine();

            } while (repeat.equalsIgnoreCase("yes"));
        }

        // display loaded stock data
        System.out.println("\n---------------------");
        System.out.println("List of Stocks");
        System.out.println("---------------------");

        stocks.printAll();

    }

}
