/*
	Chapter 10:	The LogonFrame Class
	Filename:	LogonFrame.java
	Purpose:	Provides a user interface for a log on to test the Password class
*/

import javax.swing.*;
import javax.swing.border.TitledBorder;
import static javax.swing.JOptionPane.*;				
import java.awt.*;
import java.awt.event.*;
import static java.awt.BorderLayout.*;					
import java.util.*;
import java.io.*;

public class LogonFrame extends JFrame implements ActionListener, Activator
{
    int maxUsers = 3;
    ArrayList<User> userList = new ArrayList<User>();	
    User user = null;
    String userName;
    String password;

    JTextField userNameField;
    JPasswordField passwordField;
    JButton jbtAddUser, jbtChgPswd, jbtLogon;

    public LogonFrame()
    {
        super("Log on or Maintain User"); // call super (JFrame) constructor

		int width = 330;
		int height = 170;

		// define GUI components
        JLabel label1 = new JLabel("User Name: ");
        userNameField = new JTextField(20);

        JLabel label2 = new JLabel("Password:   ");
        passwordField = new JPasswordField(20);
        passwordField.setEchoChar('*');

        jbtAddUser = new JButton("Add new user");
        jbtChgPswd = new JButton("Change user pswd");
        jbtLogon = new JButton("Log on");

		// set up GUI
        JPanel userPanel= new JPanel(new BorderLayout());
        userPanel.add(label1,WEST);						// updated for v5.0
        userPanel.add(userNameField,CENTER);			// updated for v5.0

        JPanel pswdPanel= new JPanel(new BorderLayout());
        pswdPanel.add(label2,WEST);						// updated for v5.0
        pswdPanel.add(passwordField,CENTER);			// updated for v5.0

        JPanel logonButtonPanel= new JPanel(new FlowLayout());
        logonButtonPanel.add(jbtLogon);

        JPanel maintButtonPanel= new JPanel(new FlowLayout());
        maintButtonPanel.add(jbtAddUser);
        maintButtonPanel.add(jbtChgPswd);

        JPanel contentPanel= new JPanel(new BorderLayout());
        contentPanel.add(userPanel, NORTH);				// updated for v5.0
        contentPanel.add(pswdPanel, CENTER);			// updated for v5.0
        contentPanel.add(logonButtonPanel, SOUTH);		// updated for v5.0
    	contentPanel.setBorder(new TitledBorder("Enter user info"));

        JPanel p2 = new JPanel(new BorderLayout());
        p2.add(contentPanel, NORTH);					// updated for v5.0
		p2.add(maintButtonPanel, SOUTH);				// updated for v5.0

        JPanel p3 = new JPanel(new BorderLayout(10,10));
        p3.add(p2, WEST);								// updated for v5.0
        JPanel p4 = new JPanel(new BorderLayout(10,10));
        p4.add(p3, EAST);								// updated for v5.0

        setContentPane(p4);

        // add listeners
        jbtAddUser.addActionListener(this);
        jbtChgPswd.addActionListener(this);
        jbtLogon.addActionListener(this);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { System.exit(0); }
            });

		// Enable Enter key for each JButton
		InputMap map;
		map = jbtAddUser.getInputMap();
		if (map != null){
			map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,false), "pressed");
			map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,true), "released");
		}
		map = jbtChgPswd.getInputMap();
		if (map != null){
			map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,false), "pressed");
			map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,true), "released");
		}
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

	private boolean userExists(String userName)
	{
		boolean userInList = false;

        userName.trim(); // remove any leading, trailing whitespace

        if(userList.size()>0) // at least one entry is in list
        {								// updated for v5.0
			for(User user : userList)	// for each User in userList
				if(userName.equals(user.getName()))
				{
					userInList = true;
					break;				// unstructured exit loop when user found
				}
/*
			for(int i=0; i < userList.size() && !userInList; ++i)
			{
				user = (User)userList.get(i);
			    if(userName.equals(user.getName()))
				    userInList = true;
			}
*/
		}

	    return userInList;
	}

    private boolean addToList(User user)
    {
		boolean success = false;
        if(userList.size() < maxUsers)
            success = userList.add(user);

       return success;
    }

    private void doStockActivity()
    {
        StockFrame frame = new StockFrame(user,this);
        frame.pack();
        setVisible(false);
        frame.setVisible(true);
    }

    public void activate()
    {
        setVisible(true);
        userNameField.setText("");
        userNameField.requestFocus();
	}

    public void actionPerformed(ActionEvent e)
    {
		try
        {
			userName = userNameField.getText();
            if(userName.equals(""))
            {
   				JOptionPane.showMessageDialog(this,
			    	"Please enter a valid user name.",
			        "Missing User Name.",
			        ERROR_MESSAGE);							// updated for v5.0
			    userNameField.requestFocus();
			}
			else
			{
				password = new String(passwordField.getPassword());
            	if(password.equals(""))
            	{
   					JOptionPane.showMessageDialog(this,
				    	"Please enter a valid password.",
				        "Missing Password.",
				        ERROR_MESSAGE);						// updated for v5.0
				    passwordField.requestFocus();
				}
				else
				{
		            if(e.getSource() == jbtAddUser)
		            {
			            if(!userExists(userName))
			            {
  			  				user = new User(userName,password,4);  // auto expires after 4 uses
  		                	if(addToList(user))
     	       	        		JOptionPane.showMessageDialog(this, "Success! "+user.getName()+" has been added.");
							else
								JOptionPane.showMessageDialog(this, "Could not add new user "+user.getName());
						}
                		else
							JOptionPane.showMessageDialog(this, "User "+user.getName()+" already exists!");
	        		}
            		else if(e.getSource() == jbtLogon)
            		{
			            if(userExists(userName))
			            {
							user.validate(password);

		                	if(user.pswdIsExpiring())
		 		               	JOptionPane.showMessageDialog(this, user.getName()+" logon successful; "
                                                       +user.getPswdUses()+" use(s) remaining.");
		           	        doStockActivity();

					    }
    		            else
    		        	    JOptionPane.showMessageDialog(this, "Invalid user.");
    		        }
            		else if(e.getSource() == jbtChgPswd)
            		{
			            if(userExists(userName))
			            {
							user.validate(password);
							JLabel passwd=new JLabel("New password");
							JPasswordField pword=new JPasswordField(20);
							Object[] ob={passwd,pword};
							JOptionPane.showMessageDialog(this, ob);
							String newPassword = new String(pword.getPassword());

					    	user.changePassword(password,newPassword);
            		    	JOptionPane.showMessageDialog(this, "Success, "+user.getName()+
                        	"! Your password has been changed.");
	           	        	doStockActivity();
					    }
    		            else
    		        	    JOptionPane.showMessageDialog(this, "Invalid user.");
            		}
		            else
		            	JOptionPane.showMessageDialog(this, "Please choose a valid action.");
				}
			}
	        userNameField.setText("");
            passwordField.setText("");
            userNameField.requestFocus();
        }// end of try
        catch (PasswordUsedException ex)
        {
            JOptionPane.showMessageDialog(this,
                ex.getMessage()+'\n'+ex.usage(),
                "Invalid password. Try again.",
                ERROR_MESSAGE);						// updated for v5.0
        }
        catch (PasswordSizeException ex)
        {
            JOptionPane.showMessageDialog(this,
                ex.getMessage()+'\n'+ex.usage(),
                "Invalid password. Try again.",
                ERROR_MESSAGE);						// updated for v5.0
        }
        catch (PasswordInvalidFormatException ex)
        {
            if(ex.getCount() > 2) // allows only 3 tries, then exits program
            	System.exit(0);
            else
            	JOptionPane.showMessageDialog(this,ex.getMessage()
            	                                   +"\n"+ex.usage()
            	                                   +"\nNumber of invalid attempts: "+ex.getCount(),
            	                                   "Invalid password format. Try again.",
            	    						       ERROR_MESSAGE);	// updated for v5.0
		}
        catch (PasswordInvalidException ex)
        {
            if(ex.getCount() > 2) // allows only 3 tries, then exits program
            	System.exit(0);
            else
            	JOptionPane.showMessageDialog(this,ex.getMessage()
            	                                   +"\n"+ex.usage()
            	                                   +"\nNumber of invalid attempts: "+ex.getCount(),
            	                                   "Invalid password. Try again.",
            	    						       ERROR_MESSAGE);	// updated for v5.0
		}
        catch (PasswordException ex)
        {
            JOptionPane.showMessageDialog(this,
                ex.getMessage()+'\n'+ex.usage(),
                "Invalid password. Try again.",
                ERROR_MESSAGE);						// updated for v5.0
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(this,
                ex.getMessage(),
                "Unspecified exception.",
                ERROR_MESSAGE);						// updated for v5.0
        }
    }
}