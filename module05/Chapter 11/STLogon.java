/*
	Chapter 11:	The STLogon Class
	Filename:	STLogon.java
	Purpose:	Provides a user interface to log on to the StockTracker application
*/

import javax.swing.*;
import javax.swing.border.TitledBorder;
import static javax.swing.JOptionPane.*;			// updated for v5.0
import java.awt.*;
import java.awt.event.*;
import static java.awt.BorderLayout.*;				// updated for v5.0
import java.util.*;
import java.io.*;
import java.sql.*;

public class STLogon extends JFrame implements ActionListener, Activator
{
    StockTrackerDB db;
    User user = null;
    String userID;
    String password;

    JTextField userIDField;
    JPasswordField passwordField;
    JButton jbtLogon;

    public STLogon()
    {
        super("Stock Tracker"); // call super (JFrame) constructor

		int width = 300;
		int height = 100;

        try{
        	db = new StockTrackerDB();
        }
        catch(ClassNotFoundException ex){
				JOptionPane.showMessageDialog(this,
                ex.getMessage(),
                "Class not found exception creating database object",
                ERROR_MESSAGE);					// updated for v5.0
   				System.exit(0);
		}
        catch(SQLException ex){
				JOptionPane.showMessageDialog(this,
                ex.getMessage(),
                "SQL exception creating database object",
                ERROR_MESSAGE);					// updated for v5.0
   				System.exit(0);
		}

		// define GUI components
        JLabel label1 = new JLabel("User ID: ");
        userIDField = new JTextField(20);

        JLabel label2 = new JLabel("Password:   ");
        passwordField = new JPasswordField(20);
        passwordField.setEchoChar('*');

        jbtLogon = new JButton("Log on");

		// set up GUI
        JPanel userPanel= new JPanel(new BorderLayout());
        userPanel.add(label1,CENTER);
        userPanel.add(userIDField,EAST);

        JPanel pswdPanel= new JPanel(new BorderLayout());
        pswdPanel.add(label2,CENTER);
        pswdPanel.add(passwordField,EAST);

        JPanel buttonPanel= new JPanel(new FlowLayout());
        buttonPanel.add(jbtLogon);

        JPanel contentPanel= new JPanel(new BorderLayout());
        contentPanel.add(userPanel, NORTH);
        contentPanel.add(pswdPanel, CENTER);
        contentPanel.add(buttonPanel, SOUTH);
    	contentPanel.setBorder(new TitledBorder("Log on"));

        setContentPane(contentPanel);

        // add listeners
        jbtLogon.addActionListener(this);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e)
            			{
							try {db.close();
            				}
            				catch(Exception ex)
            				{}
            				System.exit(0);
            			}
            });

		// Enable Enter key for each JButton
		InputMap map;
		map = jbtLogon.getInputMap();
		if (map != null){
			map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,false), "pressed");
			map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,true), "released");
		}

        pack();
		if( width < getWidth())				// prevent setting width too small
		   width = getWidth();
		if(height < getHeight())			// prevent setting height too small
			height = getHeight();
		centerOnScreen(width, height);
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

  	  // Set the frame to the specified location & size
	  this.setBounds(left, top, width, height);
  	}

	private boolean validUser(String userID,String password)
				throws PasswordException,SQLException,IOException,ClassNotFoundException
	{
		boolean userOK = true;
		user = db.getUser(userID); // get user object from DB for this ID
		if(user != null)
		{
			user.validate(password); // throws PasswordException
			userOK = true;
			if(user.pswdAutoExpires())	// if tracking uses
				db.updUser(user);		// update DB for this use
		}

	    return userOK;
	}

    private void doStockActivity()throws PasswordException,SQLException,
    									 IOException,ClassNotFoundException
    {
        StockTracker f = new StockTracker(user,this,db);
        f.pack();
        this.setVisible(false);
        f.setVisible(true);
    }

    public void activate()
    {
        this.setVisible(true);
        userIDField.setText("");
        userIDField.requestFocus();
        user = null;
	}

    public void actionPerformed(ActionEvent e)
    {
		try
        {
			userID = userIDField.getText();
            if(userID.equals(""))
            {
   				JOptionPane.showMessageDialog(this,
			    	"Please enter a valid user ID.",
			        "Missing User ID.",
			        ERROR_MESSAGE);					// updated for v5.0
			    userIDField.requestFocus();
			}
			else
			{
				password = new String(passwordField.getPassword());
            	if(password.equals(""))
            	{
   					JOptionPane.showMessageDialog(this,
				    	"Please enter a valid password.",
				        "Missing Password.",
				        ERROR_MESSAGE);					// updated for v5.0
				    passwordField.requestFocus();
				}
				else
				{
					try
					{
						// See if userID exists and validate password
						if(validUser(userID,password))
			        	{
		            	    if(user.pswdIsExpiring())
		 		    	       	JOptionPane.showMessageDialog(this,
		 		    	       				user.getUserID()+" logon successful; "
                    	                   +user.getPswdUses()+" use(s) remaining.");
            				if(e.getSource() == jbtLogon)
		           		       	doStockActivity();
						}
    		        	else
    		        		JOptionPane.showMessageDialog(this, "Invalid user.");
					}
					catch (PasswordExpiredException ex)
					{
						JPasswordField pf1 = new JPasswordField();
						JPasswordField pf2 = new JPasswordField();
						Object[] message1 = new Object[]
								{"Password has expired. Please enter a new password.", pf1};
						Object[] options = new String[] {"OK", "Cancel"};
						JOptionPane op1 = new JOptionPane(message1,
											  WARNING_MESSAGE,				// updated for v5.0
						                      OK_CANCEL_OPTION, null, options);	// updated for v5.0
						JDialog dialog1 = op1.createDialog(null, "Change Password");
						dialog1.setVisible(true);	// updated for v5.0

						if(op1.getValue() != null && options[0].equals(op1.getValue()))
						{
							String pswd1 = new String(pf1.getPassword());
							if(pswd1 != null)
							{
							    Object[] message2 = new Object[]
							    				{"Please verify new password.", pf2};
								JOptionPane op2 = new JOptionPane(message2,
													  WARNING_MESSAGE,  // updated for v5.0
							                          OK_CANCEL_OPTION,	// updated for v5.0
							                                null, options);
								JDialog dialog2 = op2.createDialog(null, "Verify Password");
								dialog2.setVisible(true);	// updated for v5.0
								if(op2.getValue() != null && options[0].equals(op2.getValue()))
								{
									String pswd2 = new String(pf2.getPassword());
									if(pswd2 != null)
									{
										if(pswd1.equals(pswd2))
										{
						        		    user.changePassword(password, pswd1);
											db.updUser(user);
											doStockActivity();
										}
										else
											JOptionPane.showMessageDialog(this,
			    							"Both passwords are not identical.",
									        "Password not changed",
									        ERROR_MESSAGE);					// updated for v5.0
									}
								}
							}
						}
			        }
    		    }
			}
	        userIDField.setText("");
            passwordField.setText("");
            userIDField.requestFocus();
        }// end of try
        catch (PasswordUsedException ex)
        {
            JOptionPane.showMessageDialog(this,
                ex.getMessage(),
                "Password Previously Used. Try again.",
                ERROR_MESSAGE);					// updated for v5.0
        }
        catch (PasswordSizeException ex)
        {
            JOptionPane.showMessageDialog(this,
                ex.getMessage(),
                "Invalid password size. Try again.",
                ERROR_MESSAGE);					// updated for v5.0
        }
        catch (PasswordInvalidFormatException ex)
        {
            if(ex.getCount() > 2) // allows only 3 tries, then exits program
            	System.exit(0);
            else
            	JOptionPane.showMessageDialog(this,ex.getMessage()+", count:"+ex.getCount(),
            	                                   "Invalid password format. Try again.",
            	    						       ERROR_MESSAGE);	// updated for v5.0
		}
        catch (PasswordInvalidException ex)
        {
            if(ex.getCount() > 2) // allows only 3 tries, then exits program
            	System.exit(0);
            else
            	JOptionPane.showMessageDialog(this,ex.getMessage()+", count:"+ex.getCount(),
            	                                   "Invalid password. Try again.",
            	    						       ERROR_MESSAGE);	// updated for v5.0
		}
        catch (PasswordException ex)
        {
            JOptionPane.showMessageDialog(this,
                ex.getMessage(),
                "PasswordException.",
                ERROR_MESSAGE);					// updated for v5.0
        }

        catch (IOException ex)
        {
            JOptionPane.showMessageDialog(this,
                ex.getMessage(),
                "IOException.",
                ERROR_MESSAGE);					// updated for v5.0
        }
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(this,
                ex.getMessage(),
                "SQLException.",
                ERROR_MESSAGE);					// updated for v5.0
        }
        catch (ClassNotFoundException ex)
        {
            JOptionPane.showMessageDialog(this,
                ex.getMessage(),
                "ClassNotFoundException.",
                ERROR_MESSAGE);					// updated for v5.0
        }
    }

    public static void main(String[] argv)
    {
		final STLogon f = new STLogon();
		f.setVisible(true);
    }
}