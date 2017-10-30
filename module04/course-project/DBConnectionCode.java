import java.sql.*; // jdbc classes

public class DBConnectionCode
{
   // main method
   public static void main(String[] args)
   {
      // create a constant for the database URL
      final String DB_URL = "jdbc:derby:dbProject;create=true";
      
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


} // end class
