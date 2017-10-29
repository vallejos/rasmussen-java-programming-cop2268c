import java.sql.*; // needed for JDBC classes

public class ClientDBDemo
{
   // main method
   public static void main(String[] args)
   {
      // create a constant for the database URL
      final String DB_URL = "jdbc:derby:ClientDatabase;create=true";
      
      try
      {
         // 1. create a connection to the database
         Connection conn = DriverManager.getConnection(DB_URL);
         
         // 2. check to see if the database already exists, if so, drop all of the tables
         dropTable(conn);
         
         // 3. build (or rebuild) the clients table and add rows
         buildClientTable(conn);
         
         // 4. display all of the info in the table
         displayAllClientsQuery(conn);
         
         // 5. close the connection
         conn.close();
         
      }
      catch(SQLException ex)
      {
         // if there is an error, print the error
         System.out.println("ERROR: " + ex.getMessage());
      }
   } // end main
   
   // method to build the client table
   public static void buildClientTable(Connection conn)
   {
      // build the table, then add rows to the table
      
      // message to let user know the process has started
      System.out.println("Building Client Table. ");
      
      // try catch
      try
      {
         // Statement object that uses the Connection stream to execute SQL statements
         Statement stmt = conn.createStatement();
         
         // 1. create the table
            /*
               SQL Sytax to create a table:
                  CREATE TABLE 'tableName' (fieldName dataType, fieldName dataType, etc)
            */
              stmt.execute("CREATE TABLE Clients (idNumber VARCHAR(10) NOT NULL PRIMARY KEY, " +
                                    "firstName VARCHAR(30), "+
                                    "lastName VARCHAR(30), " +
                                    "accountBalance REAL )");
         
         // 2. insert each row into the table
            /*
               SQL Syntax for inserting rows into a table:
                  INSERT INTO 'tableName' VALUES (fieldValue, fieldValue, etc.)
            */
            
            // for text based data, enclose the data in single quotes within the parenthesis
            stmt.execute("INSERT INTO Clients VALUES('123', 'OneTwo', 'ThreeFour', 1234)");
            stmt.execute("INSERT INTO Clients VALUES('321', 'ThreeTwo', 'OneZero', 3210)");
            stmt.execute("INSERT INTO Clients VALUES('59', 'Mr. Bar', 'Exam', 100000)");
            
            // once the rows are inserted into the table, give a successful message
            System.out.println("Client table created successfully");
      }
      catch(SQLException ex)
      {
         // if there is an error, print the error
         System.out.println("ERROR: " + ex.getMessage());
      }
      
   } // end buildClientTable method
   
   // method to drop the client table if it already exists
   public static void dropTable(Connection conn)
   {
      // message to start the process
      System.out.println("Checking for existing tables. ");
      try
      {
         // statement object
         Statement stmt = conn.createStatement();
         stmt.execute("DROP TABLE Clients");
         // message to let the user know the table was dropped
         System.out.println("Clients table was dropped. ");
      }
      catch(SQLException ex)
      {
         // if there is an error, print the error
         System.out.println("ERROR: " + ex.getMessage());
      }
   
   } // end dropTable method
   
   // method will display all of the information from the Client table
   public static void displayAllClientsQuery(Connection conn)
   {
      System.out.println("\n\nAll Client Query: ");
      
      try
      {
         Statement stmt = conn.createStatement();
         
         ResultSet result = stmt.executeQuery("Select * from Clients");
         
         // display the results
         while(result.next())
         {
            System.out.print(result.getString("idNumber") + "\t");
            System.out.print(result.getString("firstName") + " ");
            System.out.print(result.getString("lastName") + " ");
            System.out.println(result.getDouble("accountBalance"));
         }
      }
      catch(SQLException ex)
      {
         // if there is an error, print the error
         System.out.println("ERROR: " + ex.getMessage());
      }
   }

} // end ClientDBDemo class