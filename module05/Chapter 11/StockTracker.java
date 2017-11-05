/*
	Chapter 11:	The StockTracker Class
	Filename:	StockTracker.java
	Purpose:
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import static java.awt.BorderLayout.*;				// updated for v5.0
import java.util.*;
import java.io.*;
import java.sql.*;
import javax.swing.border.TitledBorder;
import static javax.swing.JOptionPane.*;			// updated for v5.0

class StockTracker extends JFrame implements ActionListener, WindowListener, Activator, STAction
{
    String stockSymbol;
    StockTrackerDB db;
    User user = null;

    JTextField jtfStockSymbol;
    JButton jbtAddStock,jbtDelStock,jbtLogout;
    JButton jbtAddUser,jbtDelUser,jbtUpdUser,jbtListUsers;
    JComboBox jcbStockList;
    Activator caller;

    public StockTracker(User currentUser, Activator callerObj,StockTrackerDB dbc)
    							throws ClassNotFoundException,SQLException
    {
        super("Stock Tracker"); // call super (JFrame) constructor

		int width = 0; //330;
		int height = 0; //370;
		user = currentUser;
        caller = callerObj;		// save reference to caller object
        db = dbc;				// save reference to database (access class)

		// Define components for panel 1
        JLabel label1 = new JLabel("User: "+user.getFirstName()+" "+user.getLastName());
        jbtLogout = new JButton("Log out");

        JPanel p1= new JPanel();
        p1.setLayout(new BorderLayout());
    	p1.add(label1, WEST);				// updated for v5.0
    	p1.add(jbtLogout, EAST);			// updated for v5.0

        jbtAddStock = new JButton("Add Stock");
        jbtDelStock = new JButton("Delete Stock");
        jtfStockSymbol = new JTextField(4);
        jcbStockList = new JComboBox();
        buildStockList();

        JPanel p2= new JPanel();
        p2.setLayout(new GridLayout(3,2,5,5)); // rows, cols, hgap, vgap
    	p2.add(jbtAddStock);
        p2.add(jtfStockSymbol);
    	p2.add(jbtDelStock);
        p2.add(jcbStockList);
    	p2.setBorder(new TitledBorder("Stock Holdings"));

		// Define components for panel 3
        jbtAddUser = new JButton("Add new user");
        jbtDelUser = new JButton("Delete user");
        jbtUpdUser = new JButton("Update user");
        jbtListUsers = new JButton("List users");

		// Use nested panels for positioning
        JPanel p4= new JPanel();
        p4.setLayout(new BorderLayout());
    	p4.add(p1,NORTH);					// updated for v5.0
    	p4.add(p2,CENTER);					// updated for v5.0

        if(user.isAdmin())
        {
        	JPanel p3= new JPanel();
        	p3.setLayout(new GridLayout(2,2,5,5)); // rows, cols, hgap, vgap
    		p3.add(jbtAddUser);
    		p3.add(jbtUpdUser);
    		p3.add(jbtDelUser);
    		p3.add(jbtListUsers);
    		p3.setBorder(new TitledBorder("User Maintenance"));
    		p4.add(p3,SOUTH);				// updated for v5.0
		}

        JPanel p5 = new JPanel(new BorderLayout());
        p5.add(p4, WEST);					// updated for v5.0
        JPanel p6 = new JPanel(new BorderLayout(10,10));
        p6.add(p5, EAST);					// updated for v5.0

        setContentPane(p6);

		// Register listeners
        addWindowListener(this);

        jbtLogout.addActionListener(this);

        jbtAddStock.addActionListener(this);
        jbtDelStock.addActionListener(this);
	    jcbStockList.addActionListener(this);

        jbtAddUser.addActionListener(this);
        jbtUpdUser.addActionListener(this);
        jbtDelUser.addActionListener(this);
		jbtListUsers.addActionListener(this);


		// Prepare for display
		pack();
		if( width < getWidth())				// prevent setting width too small
		   width = getWidth();
		if(height < getHeight())			// prevent setting height too small
			height = getHeight();
		centerOnScreen(width, height);
	    jtfStockSymbol.setText("");
	    jtfStockSymbol.requestFocus();
   }

  	public void centerOnScreen(int width, int height)
  	{
  	  int top, left, x, y;

  	  // Get the screen dimension
  	  Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

  	  // Determine the location for the top left corner of the frame
  	  x = (screenSize.width - width)/2;
  	  y = (screenSize.height - height)/2;
  	  left = (x < 0) ? 0 : x;
  	  top = (y < 0) ? 0 : y;

	  this.setBounds(left, top, width, height);
  	}

	private boolean stockInList(String stockSymbol)
	{
		boolean inList = false;
		int numItems;

		numItems = jcbStockList.getItemCount();

        stockSymbol.trim(); // remove any leading, trailing whitespace

        if(numItems > 0) // at least one entry is in list
			for(int i=0; i < numItems && !inList; ++i)
			    if(stockSymbol.equals((String)jcbStockList.getItemAt(i)))
				    inList = true;

	    return inList;
	}

    private void addToStockList(String stockSymbol)
    {
		int count = jcbStockList.getItemCount();
		int index = 0;

        if(!stockInList(stockSymbol))
        {
			if(count > 0)
			   while( index < count
			   	   && stockSymbol.compareTo( (String)jcbStockList.getItemAt(index) ) >= 0)
			     ++index;

        	jcbStockList.insertItemAt(stockSymbol, index);

        	if(count == 0) // if 1st stock added, display
        	{
				jcbStockList.setSelectedItem(jcbStockList.getItemAt(0));
				jtfStockSymbol.setText((String)jcbStockList.getSelectedItem());
	        }
		}
	}

    private void delFromStockList(String stockSymbol)
    {
		int count = jcbStockList.getItemCount();
		int index = 0;

        if(stockInList(stockSymbol))
        {
    		delStock(stockSymbol);
			jcbStockList.removeItem(stockSymbol);
		}
	}

    private void addToUserStocks(String userID, String stockSymbol)
    				throws SQLException,IOException,ClassNotFoundException
    {
        if(!stockInList(stockSymbol))
		   db.addUserStocks(userID,stockSymbol);
		addToStockList(stockSymbol);
	}

    private void buildStockList()
    {
		ArrayList rs;
		String userID = user.getUserID();

		try {
		    rs = db.listUserStocks(userID);
   	     	for(int i=0; i < rs.size(); ++i)
        		addToStockList((String)rs.get(i));
		}
		catch(ClassNotFoundException ex) {}
		catch(IOException ex) {}
		catch(SQLException ex)
		{
			JOptionPane.showMessageDialog(null,
						ex.getMessage(),
						"SQL Exception Message",
						ERROR_MESSAGE);			// updated for v5.0
		}
		jcbStockList.setSelectedItem(jcbStockList.getItemAt(0));
		jtfStockSymbol.setText( (String)jcbStockList.getSelectedItem());
	}

    private void addStock(String stockSymbol)
    {
    	String stockDesc = null;

		String userID = user.getUserID();

		try
		{
			stockDesc = db.getStockDesc(stockSymbol);

    		if(stockDesc == null) // stock not in DB
    		{
        	    stockDesc = JOptionPane.showInputDialog(
								"Stock not in Database. Please enter stock description");
				if(stockDesc != null)
				   db.addStock(stockSymbol,stockDesc);
			}
			if(stockDesc != null)
        		addToUserStocks(userID, stockSymbol);
		}
		catch(ClassNotFoundException ex) {}
		catch(IOException ex) {}
		catch(SQLException ex)
		{
			JOptionPane.showMessageDialog(null,
						ex.getMessage(),
						"SQL Exception Message",
						ERROR_MESSAGE);			// updated for v5.0
		}
	}

    private void delStock(String stockSymbol)
    {
		String userID = user.getUserID();

		try
		{
			db.delUserStocks(userID, stockSymbol);
		}
		catch(ClassNotFoundException ex) {}
		catch(IOException ex) {}
		catch(SQLException ex)
		{
			JOptionPane.showMessageDialog(null,
						ex.getMessage(),
						"SQL Exception Message",
						ERROR_MESSAGE);			// updated for v5.0
		}
	}

    public void activate()
    {
        this.setVisible(true);
	}

    public void actionPerformed(ActionEvent e)
    {
		if(e.getSource() == jbtLogout)
        {
	  		logoutUser();
        }
        else if(e.getSource() == jbtAddStock)
        {
			stockSymbol = jtfStockSymbol.getText();
           	if(stockSymbol.equals(""))
           		JOptionPane.showMessageDialog(this,
                	"Please enter a stock ticket symbol",
                	"Missing stock symbol",
                	ERROR_MESSAGE);			// updated for v5.0
            else
            {
				stockSymbol.trim(); // remove any leading, trailing whitespace
                addStock(stockSymbol.toUpperCase());
				jtfStockSymbol.setText("");
			}
			jtfStockSymbol.requestFocus();

		}
        else if(e.getSource() == jbtDelStock)
        {
			stockSymbol = jtfStockSymbol.getText();
           	if(stockSymbol.equals(""))
           		JOptionPane.showMessageDialog(this,
                	"Please enter a stock ticket symbol",
                	"Missing stock symbol",
                	ERROR_MESSAGE);			// updated for v5.0
            else
            {
				stockSymbol.trim(); // remove any leading, trailing whitespace
        		if(stockInList(stockSymbol))
					delFromStockList(stockSymbol);
            	else
           			JOptionPane.showMessageDialog(this,
                		"Stock ticket symbol not in list",
                		"Invalid stock symbol",
                		ERROR_MESSAGE);			// updated for v5.0
			}
			jtfStockSymbol.setText("");
			jtfStockSymbol.requestFocus();
		}
		else if(e.getSource() == jcbStockList)
        {
			stockSymbol = (String)jcbStockList.getSelectedItem();
			jtfStockSymbol.setText(stockSymbol);
		}
		else if(e.getSource() == jbtAddUser)
        {
           	// userMaint(add);
           	try
           	{
            	UserMaintFrame f = new UserMaintFrame(null, ADDUSER, db, this); // do an Add
            	setVisible(false);
	            f.setVisible(true);
		    }
		    catch(ClassNotFoundException ex){}
		    catch(SQLException ex) {}
		}
		else if(e.getSource() == jbtUpdUser)
        {
            String userID = JOptionPane.showInputDialog(this,
                				"Please input ID of user to update",
                				"Enter User ID",
                				QUESTION_MESSAGE);			// updated for v5.0
			if(userID != null)
			{
	           	try
	           	{
			    	User updUser = db.getUser(userID); // read user from DB
			    	if(updUser != null)
			    	{	// do an Update
	            		UserMaintFrame um = new UserMaintFrame(updUser, UPDUSER, db, this);
	            		setVisible(false);
		            	um.setVisible(true);
			    	}
	           		else
	           			JOptionPane.showMessageDialog(this,
	                		"Please enter a valid user ID",
	                		"Invalid User ID",
	                		ERROR_MESSAGE);			// updated for v5.0
				}
			    catch(ClassNotFoundException ex){}
			    catch(IOException ex){}
			    catch(SQLException ex) {}
			}
		}
		else if(e.getSource() == jbtDelUser)
        {
            String userID = JOptionPane.showInputDialog(this,
                				"Please input ID of user to delete",
                				"Enter User ID",
                				QUESTION_MESSAGE);			// updated for v5.0
			if(userID != null)
			{
	           	try
	           	{
			    	User delUser = db.getUser(userID);
			    	if(delUser != null)
			    	{ 	// do a Delete
	            		UserMaintFrame um = new UserMaintFrame(delUser, DELUSER, db, this);
	            		setVisible(false);
		            	um.setVisible(true);
			    	}
	           		else
	           			JOptionPane.showMessageDialog(this,
	                		"Invalid User ID",
	                		"Please enter a valid user ID",
	                		ERROR_MESSAGE);			// updated for v5.0
				}
			    catch(ClassNotFoundException ex){}
			    catch(IOException ex){}
			    catch(SQLException ex) {}
			}
		}
		else if(e.getSource() == jbtListUsers)
        {
           	try
           	{
            	UserMaintFrame um = new UserMaintFrame(null, LISTUSERS, db, this); // do list
            	setVisible(false);
	            um.setVisible(true);
		    }
		    catch(ClassNotFoundException ex){}
		    catch(SQLException ex) {}
		}
   		else
        {
           	JOptionPane.showMessageDialog(this, "Please choose a valid action.");
		}
    }

    private void logoutUser()
    {
	  this.setVisible(false);
	  dispose();
	  caller.activate();	// call activate method of caller object
	}

    // Handler for window opened event
    public void windowOpened(WindowEvent event)
    {
		jtfStockSymbol.setText("");
		jtfStockSymbol.requestFocus();
    }
    // Handler for window closing event
    public void windowClosing(WindowEvent event)
    {
	  logoutUser();
    }
    // Handler for window closed event
    public void windowClosed(WindowEvent event)
    {
    }
    // Handler for window iconified event
    public void windowIconified(WindowEvent event)
    {
    }
    // Handler for window deiconified event
    public void windowDeiconified(WindowEvent event)
    {
    }
    // Handler for window activated event
    public void windowActivated(WindowEvent event)
    {
    }
    // Handler for window deactivated event
    public void windowDeactivated(WindowEvent event)
    {
    }
}