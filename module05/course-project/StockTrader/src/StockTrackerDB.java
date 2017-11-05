/*
Chapter 11:	The StockTrackerDB Class
Filename:	StockTrackerDB.java
Purpose:	To provide a data access class for the StockTracker database
*/

import java.io.*;
import java.sql.*;
import java.util.*;

public class StockTrackerDB
{
    private Connection con = null;
    
    // Constructor; makes database connection
    public StockTrackerDB() throws ClassNotFoundException,SQLException
    {
        if(con == null)
        {
            //String url = "jdbc:odbc:StockTracker";
            String url = "jdbc:derby:StockTrackerDB;create=true";
            
            /*try
            {
            //Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Class.forName("jdbc:derby:StockTracker;create=true");
            }
            catch(ClassNotFoundException ex)
            {
            throw new ClassNotFoundException(ex.getMessage() +
            "\nCannot locate sun.jdbc.odbc.JdbcOdbcDriver");
            }*/
            
            try
            {
                con = DriverManager.getConnection(url);
            }
            catch(SQLException ex)
            {
                throw new SQLException(ex.getMessage()+
                        "\nCannot open database connection for "+url);
            }
        }
    }
    
    // Close makes database connection; null reference to connection
    public void close() throws SQLException,IOException,ClassNotFoundException
    {
        con.close();
        con = null;
    }
    
    // Method to serialize object to byte array
    private byte[] serializeObj(Object obj) throws IOException
    {
        ByteArrayOutputStream baOStream = new ByteArrayOutputStream();
        ObjectOutputStream objOStream = new ObjectOutputStream(baOStream);
        
        objOStream.writeObject(obj); // object must be Serializable
        objOStream.flush();
        objOStream.close();
        return baOStream.toByteArray(); // returns stream as byte array
    }
    
    // Method to deserialize bytes from a byte array into an object
    private Object deserializeObj(byte[] buf)
            throws IOException, ClassNotFoundException
    {
        Object obj = null;
        
        if (buf != null)
        {
            ObjectInputStream objIStream =
                    new ObjectInputStream(new ByteArrayInputStream(buf));
            
            obj = objIStream.readObject(); //IOException, ClassNotFoundException
        }
        return obj;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    // Methods for adding a record to a table
    ////////////////////////////////////////////////////////////////////////////
    // add to the Stocks Table
    public void addStock(String stockSymbol, String stockDesc)
            throws SQLException, IOException, ClassNotFoundException
    {
        Statement stmt = con.createStatement();
        stmt.executeUpdate("INSERT INTO Stocks VALUES ('"
                +stockSymbol+"'"
                        +",'"+stockDesc+"')");
        stmt.close();
    }
    
    // add to the Users table
    public boolean addUser(User user) throws SQLException,IOException,
            ClassNotFoundException
    {
        boolean result = false;
        
        String dbUserID;
        String dbLastName;
        String dbFirstName;
        Password dbPswd;
        boolean isAdmin;
        
        dbUserID = user.getUserID();
        
        if(getUser(dbUserID) == null)
        {
            dbLastName = user.getLastName();
            dbFirstName = user.getFirstName();
            Password pswd = user.getPassword();
            isAdmin = user.isAdmin();
            
            PreparedStatement pStmt = con.prepareStatement(
                    "INSERT INTO Users VALUES (?,?,?,?,?)");
            
            pStmt.setString(1, dbUserID);
            pStmt.setString(2, dbLastName);
            pStmt.setString(3, dbFirstName);
            pStmt.setBytes(4, serializeObj(pswd));
            pStmt.setBoolean(5, isAdmin);
            pStmt.executeUpdate();
            pStmt.close();
            result = true;
        }
        else
            throw new IOException("User exists - cannot add.");
        
        return result;
    }
    
    // add to the UserStocks table
    public void addUserStocks(String userID, String stockSymbol)
            throws SQLException,IOException,ClassNotFoundException
    {
        Statement stmt = con.createStatement();
        
        stmt.executeUpdate("INSERT INTO UserStocks VALUES ('"
                +userID+"'"
                        +",'"+stockSymbol+"')");
        stmt.close();
    }
    
    ////////////////////////////////////////////////////////////////////////////
    // Methods for updating a record in a table
    ////////////////////////////////////////////////////////////////////////////
    
    // updating the Users Table
    public boolean updUser(User user) throws SQLException,IOException,
            ClassNotFoundException
    {
        boolean result = false;
        
        String dbUserID;
        String dbLastName;
        String dbFirstName;
        Password dbPswd;
        boolean isAdmin;
        
        dbUserID = user.getUserID();
        
        if(getUser(dbUserID) != null)
        {
            dbLastName = user.getLastName();
            dbFirstName = user.getFirstName();
            Password pswd = user.getPassword();
            isAdmin = user.isAdmin();
            
            PreparedStatement pStmt = con.prepareStatement("UPDATE Users SET lastName = ?,"
                    +" firstName = ?, pswd = ?, admin = ? WHERE userID = ?");
            
            pStmt.setString(1, dbLastName);
            pStmt.setString(2, dbFirstName);
            pStmt.setBytes(3, serializeObj(pswd));
            pStmt.setBoolean(4, isAdmin);
            pStmt.setString(5, dbUserID);
            
            pStmt.executeUpdate();
            pStmt.close();
            result = true;
        }
        else
            throw new IOException("User does not exist - cannot update.");
        
        return result;
    }
    
    
    ////////////////////////////////////////////////////////////////////////////
    // Methods for deleting a record from a table
    ////////////////////////////////////////////////////////////////////////////
    
    // delete a record from the Stocks Table
    private void delStock(String stockSymbol)
            throws SQLException,IOException,ClassNotFoundException
    {
        Statement stmt = con.createStatement();
        stmt.executeUpdate("DELETE FROM Stocks WHERE "
                +"symbol = '"+stockSymbol+"'");
        stmt.close();
    }
    
    // delete a record from the Users Table
    public void delUser(User user) throws SQLException,IOException,
            ClassNotFoundException
    {
        String dbUserID;
        String stockSymbol;
        
        Statement stmt = con.createStatement();
        
        try {
            con.setAutoCommit(false);
            
            dbUserID = user.getUserID();
            if(getUser(dbUserID) != null)  // verify user exists in database
            {
                ResultSet rs1 = stmt.executeQuery("SELECT userID, symbol "
                        +"FROM UserStocks WHERE userID = '"+dbUserID+"'");
                while(rs1.next())
                {
                    try
                    {
                        stockSymbol = rs1.getString("symbol");
                        delUserStocks(dbUserID, stockSymbol);
                    }
                    catch(SQLException ex)
                    {
                        throw new SQLException("Deletion of user stock holding failed: "
                                +ex.getMessage());
                    }
                } // end of loop thru UserStocks
                
                try
                {  // holdings deleted, now delete user
                    stmt.executeUpdate("DELETE FROM Users WHERE "
                            +"userID = '"+dbUserID+"'");
                }
                catch(SQLException ex)
                {
                    throw new SQLException("User deletion failed: "+ex.getMessage());
                }
            }
            else
                throw new IOException("User not found in database - cannot delete.");
            
            try
            {
                con.commit();
            }
            catch(SQLException ex)
            {
                throw new SQLException("Transaction commit failed: "+ex.getMessage());
            }
        }
        catch (SQLException ex)
        {
            try
            {
                con.rollback();
            }
            catch (SQLException sqx)
            {
                throw new SQLException("Transaction failed then rollback failed: "
                        +sqx.getMessage());
            }
            
            // Transaction failed, was rolled back
            throw new SQLException("Transaction failed; was rolled back: "
                    +ex.getMessage());
        }
        
        stmt.close();
    }
    
    // delete a record from the UserStocks Table
    public void delUserStocks(String userID, String stockSymbol)
            throws SQLException,IOException,ClassNotFoundException
    {
        Statement stmt = con.createStatement();
        ResultSet rs;
        
        stmt.executeUpdate("DELETE FROM UserStocks WHERE "
                +"userID = '"+userID+"'"
                        +"AND symbol = '"+stockSymbol+"'");
        
        rs = stmt.executeQuery("SELECT symbol FROM UserStocks "
                +"WHERE symbol = '"+stockSymbol+"'");

        if(!rs.next()) // no users have this stock
            delStock(stockSymbol);
        
        stmt.close();
        
    }
    
    ////////////////////////////////////////////////////////////////////////////
    // Methods for listing record data from a table
    // Ordered by:
    // 		methods that obtain individual field(s),
    // 		methods that obtain a complete record, and
    // 		methods that obtain multiple records
    ////////////////////////////////////////////////////////////////////////////
    
    // Methods to access one or more individual fields
    
    // get a stock description from the Stocks Table
    public String getStockDesc(String stockSymbol)
            throws SQLException, IOException, ClassNotFoundException
    {
        Statement stmt = con.createStatement();
        String stockDesc = null;
        
        ResultSet rs = stmt.executeQuery("SELECT symbol, name FROM Stocks "
                +"WHERE symbol = '"+stockSymbol+"'");
        if(rs.next())
            stockDesc = rs.getString("name");
        rs.close();
        stmt.close();
        
        return stockDesc;
    }
    
    
    // Methods to access a complete record
    
    // get User data from the Users Table
    public User getUser(String userID) throws SQLException, IOException,
            ClassNotFoundException
    {
        Statement stmt = con.createStatement();
        
        String dbUserID;
        String dbLastName;
        String dbFirstName;
        Password dbPswd;
        boolean isAdmin;
        
        byte[] buf = null;
        User user = null;
        ResultSet rs = stmt.executeQuery("SELECT * FROM Users WHERE userID = '"
                +userID+"'");
        if(rs.next())
        {
            dbUserID = rs.getString("userID");
            dbLastName = rs.getString("lastName");
            dbFirstName = rs.getString("firstName");
            
            // Do NOT use with JDK 1.2.2 using JDBC-ODBC bridge as
            // SQL NULL data value is not handled correctly.
            buf = rs.getBytes("pswd");
            dbPswd = (Password)deserializeObj(buf);
            
            isAdmin = rs.getBoolean("admin");
            user = new User(dbUserID,dbFirstName,dbLastName,dbPswd,isAdmin);
        }
        rs.close();
        stmt.close();
        
        return user; // User object created for userID
    }
    
    
    // Methods to access a list of records
    
    // get list of selected fields for all records from the Users Table
    // updated for v5.0
    public ArrayList<Object> listUsers() throws SQLException,IOException,
            ClassNotFoundException
    {
        ArrayList<Object> aList = new ArrayList<Object>(); // updated for v5.0
        Statement stmt = con.createStatement();
        
        ResultSet rs = stmt.executeQuery("SELECT userID, firstName, lastName, admin "
                +"FROM Users ORDER BY userID");
        
        while(rs.next())
        {
            aList.add(rs.getString("userID"));
            aList.add(rs.getString("firstName"));
            aList.add(rs.getString("lastName"));
            aList.add(new Boolean(rs.getBoolean("admin")));
        }
        
        rs.close();
        stmt.close();
        
        return aList;
    }
    
    // get all fields in all records for a given user from the Userstocks Table
    // updated for v5.0
    public ArrayList<String> listUserStocks(String userID) throws SQLException,IOException,
            ClassNotFoundException
    {
        ArrayList<String> aList = new ArrayList<String>(); // updated for v5.0
        Statement stmt = con.createStatement();
        
        ResultSet rs = stmt.executeQuery("SELECT * FROM UserStocks "
                +"WHERE userID = '"+userID+"' ORDER BY symbol");
        while(rs.next()) {
            aList.add(rs.getString("symbol"));
        }
        
        rs.close();
        stmt.close();
        
        return aList;
    }

    public ArrayList<String> listStocks() throws SQLException,IOException,
            ClassNotFoundException
    {
        ArrayList<String> aList = new ArrayList<String>(); // updated for v5.0
        Statement stmt = con.createStatement();
        
        ResultSet rs = stmt.executeQuery("SELECT * FROM Stocks ORDER BY symbol");
        while(rs.next()) {
            aList.add(rs.getString("symbol"));
            aList.add(rs.getString("name"));
        }
        rs.close();
        stmt.close();
        
        return aList;
    }

}