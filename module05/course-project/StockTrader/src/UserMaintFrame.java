/*
Chapter 11:	The UserMaint Class
Filename:	UserMaintFrame.java
Purpose:	Provides a user interface for a stock list to test the Password class
*/

import javax.swing.*;
import javax.swing.text.*;
import static javax.swing.SwingConstants.*;			// updated for v5.0
import java.awt.*;
import java.awt.event.*;

import java.util.*;
import java.io.*;
import java.sql.*;
import javax.swing.border.TitledBorder;
import static javax.swing.JOptionPane.*;			// updated for v5.0

public class UserMaintFrame extends JFrame
        implements ActionListener, ItemListener, WindowListener, STAction
{
    // Define components for label panel
    JPanel labelPanel = new JPanel();
    
    JLabel lblUserID = new JLabel( "User ID:", LEFT );		// updated for v5.0
    JLabel lblFName = new JLabel( "First Name:", LEFT );	// updated for v5.0
    JLabel lblLName = new JLabel( "Last Name:", LEFT );		// updated for v5.0
    JLabel lblPswd = new JLabel( "Password:", LEFT );		// updated for v5.0
    JLabel lblPswd2a = new JLabel( "Re-enter", LEFT );		// updated for v5.0
    JLabel lblPswd2b = new JLabel( "Password:", LEFT );		// updated for v5.0
    JLabel lblAutoExp = new JLabel( "Auto Expires:", LEFT );// updated for v5.0
    JLabel lblUses = new JLabel( "Uses Left: ", LEFT );		// updated for v5.0
    JLabel lblAdmin = new JLabel();
    
    // TextField panel
    JPanel textPanel = new JPanel();
    
    JTextField jtfUserID = new JTextField(20);
    JTextField jtfFirstName = new JTextField(20);
    JTextField jtfLastName = new JTextField(20);
    JPasswordField jpfPswd1 = new JPasswordField(20);
    JPasswordField jpfPswd2 = new JPasswordField(20);
    JTextField jtfExpires = new JTextField(5);
    
    JButton jbtAddUser = new JButton( "Add" );
    JButton jbtUpdUser = new JButton( "Update" );
    JButton jbtDelUser = new JButton( "Delete" );
    JButton jbtCancel = new JButton("Cancel");
    JButton jbtCloseUserList = new JButton( "Close" );
    
    JCheckBox jcbAdmin = new JCheckBox("Is Admin");
    
    JPanel expiresPanel = new JPanel();	// holds expires yes/no and # of times
    JRadioButton jrbExpiresYes = new JRadioButton("Yes");
    JRadioButton jrbExpiresNo = new JRadioButton("No");
    
    ButtonGroup bgExpires = new ButtonGroup();
    
    StockTrackerDB db;
    User user = null;
    Activator caller = null;
    boolean isAdmin = false;
    boolean autoExp = true;
    int uses = 0;
    int action;
    
    public UserMaintFrame(User userObj, int actionType, StockTrackerDB dbc,Activator callerObj)
            throws ClassNotFoundException,SQLException
    {
        super("Stock Tracker: User Maintenance"); // call super (JFrame) constructor
        
        int width = 330;
        int height = 260;
        
        caller = callerObj;		// save reference to caller object
        db = dbc;
        user = userObj;
        action = actionType;
        
        JPanel workPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        labelPanel.setLayout( new GridLayout(8,1));
        textPanel.setLayout(new GridLayout(8,1));
        expiresPanel.setLayout(new GridLayout(1,4));
        
        switch(action)
        {
            case ADDUSER: // add user
                workPanel = createAddPanel();
                buttonPanel.add(jbtAddUser);
                buttonPanel.add(jbtCancel);
                jbtAddUser.addActionListener(this);
                jbtCancel.addActionListener(this);
                jcbAdmin.addItemListener(this);
                jrbExpiresYes.addActionListener(this);
                jrbExpiresNo.addActionListener(this);
                break;
            case UPDUSER: // update user
                workPanel = createUpdPanel();
                buttonPanel.add(jbtUpdUser);
                buttonPanel.add(jbtCancel);
                jbtUpdUser.addActionListener(this);
                jbtCancel.addActionListener(this);
                jcbAdmin.addItemListener(this);
                jrbExpiresYes.addActionListener(this);
                jrbExpiresNo.addActionListener(this);
                break;
            case DELUSER: // delete user
                workPanel = createDelPanel();
                buttonPanel.add(jbtDelUser);
                buttonPanel.add(jbtCancel);
                jbtDelUser.addActionListener(this);
                jbtCancel.addActionListener(this);
                break;
            case LISTUSERS: // list users
                workPanel = createListPanel();
                buttonPanel.add(jbtCloseUserList);
                jbtCloseUserList.addActionListener(this);
                break;
            case LISTSTOCKSUSER: // list stocks user
                workPanel = createUserStockListPanel();
                buttonPanel.add(jbtCloseUserList);
                jbtCloseUserList.addActionListener(this);
                break;
            case LISTSTOCKS: // list stocks
                workPanel = createStockListPanel();
                buttonPanel.add(jbtCloseUserList);
                jbtCloseUserList.addActionListener(this);
                break;
        }
        
        JPanel p5 = new JPanel(new BorderLayout(10,10));
        p5.add(workPanel, BorderLayout.CENTER);				// must fully qualify as
        p5.add(buttonPanel, BorderLayout.SOUTH);			// SwingConstants and BorderLayout
        // both contain constants with
        JPanel p6 = new JPanel(new BorderLayout(10,10));	// same names but different
        p6.add(p5, BorderLayout.CENTER);					// values and even data types
        
        setContentPane(p6);
        p6.setPreferredSize(new Dimension(width, height));
        p6.setMinimumSize(new Dimension(330, 200));  // width, height
        
        // Register listeners
        addWindowListener(this);
        
        // Prepare for display
        pack();
        if( width < getWidth())				// prevent setting width too small
            width = getWidth();
        if(height < getHeight())			// prevent setting height too small
            height = getHeight();
        centerOnScreen(width, height);
    }
    
    
    private JPanel createAddPanel()
    {
        labelPanel.add(lblUserID);
        labelPanel.add(lblFName);
        labelPanel.add(lblLName);
        labelPanel.add(lblPswd);
        labelPanel.add(lblPswd2a);
        labelPanel.add(lblPswd2b);
        labelPanel.add(lblAutoExp);
        labelPanel.add(jcbAdmin);
        
        textPanel.add(jtfUserID);
        textPanel.add(jtfFirstName);
        textPanel.add(jtfLastName);
        textPanel.add(jpfPswd1);
        textPanel.add( new JLabel("") ); // for spacing
        textPanel.add(jpfPswd2);
        
        jrbExpiresYes.setSelected(true);
        jtfExpires.setEditable(true);
        bgExpires.add(jrbExpiresYes);
        bgExpires.add(jrbExpiresNo);
        expiresPanel.add(jrbExpiresYes);
        expiresPanel.add(jrbExpiresNo);
        expiresPanel.add(lblUses);
        expiresPanel.add(jtfExpires);
        textPanel.add(expiresPanel);
        
        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(new TitledBorder("Add New User"));
        contentPanel.setLayout(new BorderLayout(10,10));
        contentPanel.add(labelPanel, BorderLayout.WEST);
        contentPanel.add(textPanel, BorderLayout.EAST);
        
        return contentPanel;
    }
    
    private JPanel createUpdPanel()
    {
        labelPanel.add(lblUserID);
        labelPanel.add(lblFName);
        labelPanel.add(lblLName);
        labelPanel.add(lblPswd);
        labelPanel.add(lblPswd2a);
        labelPanel.add(lblPswd2b);
        labelPanel.add(lblAutoExp);
        labelPanel.add(lblUses);
        
        jtfUserID.setText(user.getUserID());
        jtfUserID.setEditable(false);
        jtfFirstName.setText(user.getFirstName());
        jtfLastName.setText(user.getLastName());
        jtfExpires.setEditable(false);
        
        bgExpires.add(jrbExpiresYes);
        bgExpires.add(jrbExpiresNo);
        
        autoExp = user.pswdAutoExpires();
        if(autoExp)
        {
            jrbExpiresYes.setSelected(true);
            jtfExpires.setText(String.valueOf(user.getPswdUses()));
        }
        else
        {
            jrbExpiresNo.setSelected(true);
        }
        
        isAdmin = user.isAdmin();
        if(isAdmin)
            jcbAdmin.setSelected(true);
        else
            jcbAdmin.setSelected(false);
        
        labelPanel.add(jcbAdmin);
        
        textPanel.add(jtfUserID);
        textPanel.add(jtfFirstName);
        textPanel.add(jtfLastName);
        textPanel.add(jpfPswd1);
        textPanel.add( new JLabel("") ); // for spacing
        textPanel.add(jpfPswd2);
        
        expiresPanel.add(jrbExpiresYes);
        expiresPanel.add(jrbExpiresNo);
        expiresPanel.add(lblUses);
        expiresPanel.add(jtfExpires);
        textPanel.add(expiresPanel);
        
        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(new TitledBorder("Update User"));
        contentPanel.setLayout(new BorderLayout(10,10));
        contentPanel.add(labelPanel, BorderLayout.WEST);
        contentPanel.add(textPanel, BorderLayout.EAST);
        
        return contentPanel;
    }
    
    private JPanel createDelPanel()
    {
        labelPanel.add(lblUserID);
        labelPanel.add(lblFName);
        labelPanel.add(lblLName);
        
        jtfUserID.setText(user.getUserID());
        jtfUserID.setEditable(false);
        jtfFirstName.setText(user.getFirstName());
        jtfLastName.setText(user.getLastName());
        
        isAdmin = user.isAdmin();
        if(isAdmin)
            lblAdmin.setText("Administrative user");
        else
            lblAdmin.setText("Non-administrative user");
        
        textPanel.add(jtfUserID);
        textPanel.add(jtfFirstName);
        textPanel.add(jtfLastName);
        textPanel.add(lblAdmin);
        
        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(new TitledBorder("Delete User"));
        contentPanel.setLayout(new BorderLayout(10,10));
        contentPanel.add(labelPanel, BorderLayout.WEST);
        contentPanel.add(textPanel, BorderLayout.EAST);
        
        return contentPanel;
    }
    
    private JPanel createListPanel()
    {
        String heading = "User ID\tFirst Name\tLast Name\tAdministrator\n"
                +"=======\t==========\t=========\t=============\n";
        
        ArrayList rs;
        
        //Create a text area.
        JTextArea textArea = new JTextArea(5,35); // rows, cols
        
        Insets margins = new Insets(5,5,2,2); // top,left,bottom,right margins
        textArea.setMargin(margins);
        textArea.setLineWrap(false);
        textArea.setEditable(false);
        JScrollPane areaScrollPane = new JScrollPane(textArea);
        
        textArea.append(heading);
        try {
            rs = db.listUsers();
            for(int i=0; i < rs.size(); ++i)
            {
                textArea.append(rs.get(i)+"\t");
                textArea.append(rs.get(++i)+"\t");
                textArea.append(rs.get(++i)+"\t");
                textArea.append(rs.get(++i)+"\n");
            }
        }
        catch(ClassNotFoundException ex) {}
        catch(IOException ex) {}
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null,
                    ex.getMessage(),
                    "SQL Exception Message",
                    ERROR_MESSAGE);						// updated for v5.0
        }
        
        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(new TitledBorder("List Users"));
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(areaScrollPane, BorderLayout.CENTER);
        
        return contentPanel;
    }

    private JPanel createUserStockListPanel()
    {
        String heading = "Stock Symbol\n"
                +"============\n";
        
        ArrayList rs;
        
        //Create a text area.
        JTextArea textArea = new JTextArea(5,35); // rows, cols
        
        Insets margins = new Insets(5,5,2,2); // top,left,bottom,right margins
        textArea.setMargin(margins);
        textArea.setLineWrap(false);
        textArea.setEditable(false);
        JScrollPane areaScrollPane = new JScrollPane(textArea);
        
        textArea.append(heading);
        try {
            rs = db.listUserStocks(user.getUserID());
            for(int i=0; i < rs.size(); ++i)
            {
                textArea.append(rs.get(i)+"\n");
            }
        }
        catch(ClassNotFoundException ex) {}
        catch(IOException ex) {}
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null,
                    ex.getMessage(),
                    "SQL Exception Message",
                    ERROR_MESSAGE);						// updated for v5.0
        }
        
        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(new TitledBorder("List User Stocks"));
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(areaScrollPane, BorderLayout.CENTER);
        
        return contentPanel;
    }

    private JPanel createStockListPanel()
    {
        String heading = "Stock Symbol\tStock Description\n"
                +"=========\t===============\n";
        
        ArrayList rs;
        
        //Create a text area.
        JTextArea textArea = new JTextArea(5,35); // rows, cols
        
        Insets margins = new Insets(5,5,2,2); // top,left,bottom,right margins
        textArea.setMargin(margins);
        textArea.setLineWrap(false);
        textArea.setEditable(false);
        JScrollPane areaScrollPane = new JScrollPane(textArea);
        
        textArea.append(heading);
        try {
            rs = db.listStocks();
            for(int i=0; i < rs.size(); ++i)
            {
                textArea.append(rs.get(i)+"\t");
                textArea.append(rs.get(++i)+"\n");
            }
        }
        catch(ClassNotFoundException ex) {}
        catch(IOException ex) {}
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null,
                    ex.getMessage(),
                    "SQL Exception Message",
                    ERROR_MESSAGE);						// updated for v5.0
        }
        
        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(new TitledBorder("Stocks List"));
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(areaScrollPane, BorderLayout.CENTER);
        
        return contentPanel;
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
    
    public void itemStateChanged( ItemEvent e )
    {
        if(e.getStateChange() == ItemEvent.SELECTED)
            isAdmin = true;
        else
            isAdmin = false;
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == jbtCancel)			// clicked Cancel button
        {
            closeUserMaint();
        }
        else if(e.getSource() == jbtAddUser)	// clicked Add button
        {
            try
            {
                if(addUser())
                    closeUserMaint();
            }
            catch(PasswordException ex)
            {
                JOptionPane.showMessageDialog(null,
                        ex.getMessage(),
                        "Password Exception Message",
                        ERROR_MESSAGE);					// updated for v5.0
            }
        }
        else if(e.getSource() == jbtUpdUser)	// clicked Update button
        {
            try
            {
                updUser();
                closeUserMaint();
            }
            catch(PasswordException ex)
            {
                JOptionPane.showMessageDialog(null,
                        ex.getMessage(),
                        "Password Exception Message",
                        ERROR_MESSAGE);					// updated for v5.0
            }
        }
        else if(e.getSource() == jbtDelUser)		// clicked Delete button
        {
            delUser();
            closeUserMaint();
        }
        else if(e.getSource() == jbtCloseUserList)	// clicked Close button on list
        {
            closeUserMaint();
        }
        else if(e.getSource() == jrbExpiresYes)		// clicked Yes for Expires
        {
            if(action == ADDUSER) // doing add user
                jtfExpires.setEditable(true);
            
            if(action == UPDUSER) // doing update user
                jtfExpires.setText(String.valueOf(user.getPswdUses()));
            
            autoExp = true;
        }
        else if(e.getSource() == jrbExpiresNo)
        {
            jtfExpires.setEditable(false);
            jtfExpires.setText("");
            autoExp = false;
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Please choose a valid action.");
        }
    }
    
    private void closeUserMaint()
    {
        this.setVisible(false);
        dispose();
        caller.activate();	// call activate method of caller object
    }
    
    public boolean addUser() throws PasswordException
    {
        boolean success = false;
        String pswd1, pswd2;
        
        pswd1 = new String(jpfPswd1.getPassword());
        pswd2 = new String(jpfPswd2.getPassword());
        
        if(jtfUserID.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null,
                    "Please enter a user id.",
                    "Missing User ID.",
                    ERROR_MESSAGE);					// updated for v5.0
            
            jtfUserID.requestFocus();
        }
        else
            if(jtfFirstName.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null,
                        "Please enter a first name.",
                        "Missing First Name.",
                        ERROR_MESSAGE);					// updated for v5.0
                jtfFirstName.requestFocus();
            }
            else
                if(jtfLastName.getText().equals(""))
                {
                    JOptionPane.showMessageDialog(null,
                            "Please enter a last name.",
                            "Missing Last Name.",
                            ERROR_MESSAGE);					// updated for v5.0
                    jtfLastName.requestFocus();
                }
                else
                    if(pswd1.equals(""))
                    {
                        JOptionPane.showMessageDialog(null,
                                "Please enter a password.",
                                "Missing Password.",
                                ERROR_MESSAGE);					// updated for v5.0
                        jpfPswd1.requestFocus();
                    }
                    else
                        if(pswd2.equals(""))
                        {
                            JOptionPane.showMessageDialog(null,
                                    "Please re-enter the password.",
                                    "Missing Password Re-entry.",
                                    ERROR_MESSAGE);					// updated for v5.0
                            jpfPswd2.requestFocus();
                        }
                        else
                            if(!pswd1.equals(pswd2))
                            {
                                JOptionPane.showMessageDialog(null,
                                        "Password re-entry does not match password.",
                                        "Invalid Password Re-entry.",
                                        ERROR_MESSAGE);					// updated for v5.0
                                jpfPswd2.requestFocus();
                            }
                            else
                                if(autoExp && jtfExpires.getText().equals(""))
                                {
                                    JOptionPane.showMessageDialog(null,
                                            "An automatically expiring password requires valid number of uses.",
                                            "Invalid Number of Uses.",
                                            ERROR_MESSAGE);					// updated for v5.0
                                    jtfExpires.requestFocus();
                                }
                                else
                                    try {
                                        success = true;
                                        if(autoExp)
                                            uses = Integer.parseInt(jtfExpires.getText());
                                        else
                                            uses = 0;
                                        
                                        user = new User(jtfUserID.getText(),jtfFirstName.getText(),jtfLastName.getText(),
                                                pswd1, autoExp, uses, isAdmin);
                                        user.expirePassword(); // cause new user to have to change password
                                        
                                        if(!db.addUser(user))
                                        {
                                            jtfUserID.setText( "" );
                                            jtfFirstName.setText( "" );
                                            jtfLastName.setText( "" );
                                            jpfPswd1.setText( "" );
                                            jpfPswd2.setText( "" );
                                            jtfExpires.setText( "" );
                                            JOptionPane.showMessageDialog(null,
                                                    "User not added.",
                                                    "Invalid Add.",
                                                    ERROR_MESSAGE);					// updated for v5.0
                                        }
                                        else
                                            JOptionPane.showMessageDialog(null,"User "+user.getUserID()
                                                    +", "+user.getFirstName()
                                                    +" "+user.getLastName()
                                                    +", added.",
                                                    "New User Added",
                                                    INFORMATION_MESSAGE);	// updated for v5.0
                                    }
                                    catch(ClassNotFoundException ex) {}
                                    catch(IOException ex) {}
                                    catch(SQLException sqlex) {}
        
        return success;
    }
    
    public void updUser() throws PasswordException
    {
        boolean okForUpdate = true;
        String pswd1, pswd2;
        
        pswd1 = new String(jpfPswd1.getPassword());
        pswd2 = new String(jpfPswd2.getPassword());
        
        if(jtfUserID.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null,
                    "Please enter a user id.",
                    "Missing User ID.",
                    ERROR_MESSAGE);					// updated for v5.0
            okForUpdate = false;
        }
        else
            if(jtfFirstName.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null,
                        "Please enter a first name.",
                        "Missing First Name.",
                        ERROR_MESSAGE);					// updated for v5.0
                okForUpdate = false;
            }
            else
                if(jtfLastName.getText().equals(""))
                {
                    JOptionPane.showMessageDialog(null,
                            "Please enter a last name.",
                            "Missing Last Name.",
                            ERROR_MESSAGE);					// updated for v5.0
                    okForUpdate = false;
                }
        
        if(okForUpdate)
            if(!pswd1.equals(""))		// entered a new password value in password1
            {
                if(pswd2.equals(""))	// did not enter a new password value in password2
                {
                    JOptionPane.showMessageDialog(null,
                            "Please re-enter the password.",
                            "Missing Password Re-entry.",
                            ERROR_MESSAGE);					// updated for v5.0
                    okForUpdate = false;
                }
                else
                    if(!pswd1.equals(pswd2))	// new password1 does not match password2
                    {
                        JOptionPane.showMessageDialog(null,
                                "Password re-entry does not match password.",
                                "Invalid Password Re-entry.",
                                ERROR_MESSAGE);					// updated for v5.0
                        okForUpdate = false;
                    }
                    else
                        user.adminChangePassword(pswd1);
            }
        
        if(okForUpdate)
            try {
                user.setFirstName(jtfFirstName.getText());
                user.setLastName(jtfLastName.getText());
                user.setAdmin(isAdmin);
                user.setAutoExpires(autoExp);
                
                if(db.updUser(user))
                    JOptionPane.showMessageDialog(this,"User "+user.getUserID()
                            +", "+user.getFirstName()
                            +" "+user.getLastName()
                            +", updated.",
                            "User Updated",
                            INFORMATION_MESSAGE);	// updated for v5.0
            }
            catch(ClassNotFoundException ex) {}
            catch(IOException ex) {}
            catch(SQLException ex)
            {
                JOptionPane.showMessageDialog(null,
                        ex.getMessage(),
                        "SQL Exception Message",
                        ERROR_MESSAGE);					// updated for v5.0
            }
    }
    
    public void delUser()
    {
        try {
            db.delUser(user);
            JOptionPane.showMessageDialog(this,"User "+user.getUserID()
                    +", "+user.getFirstName()
                    +" "+user.getLastName()
                    +", deleted.",
                    "User Deleted",
                    INFORMATION_MESSAGE);	// updated for v5.0
            
        }
        catch(ClassNotFoundException ex) {}
        catch(IOException ex) {}
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null,
                    ex.getMessage(),
                    "SQL Exception Message",
                    ERROR_MESSAGE);					// updated for v5.0
        }
    }
    
    
    // Handler for window opened event
    public void windowOpened(WindowEvent event)
    {
        switch(action)
        {
            case ADDUSER: // adding a new user
                jtfUserID.requestFocus();
                break;
            case UPDUSER: // updating an existing user
                jtfFirstName.requestFocus();
                break;
            case DELUSER: // deleting an existing user; no focus set on a field
                break;
            case LISTUSERS: // listing all users
                jbtCloseUserList.requestFocus();
                // Enable Enter key for JButton
                InputMap map;
                map = jbtCloseUserList.getInputMap();
                if (map != null){
                    map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,false), "pressed");
                    map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,true), "released");
                }
                break;
            case LISTSTOCKSUSER: // listing user stocks
                jbtCloseUserList.requestFocus();
                // Enable Enter key for JButton
                InputMap mapU;
                mapU = jbtCloseUserList.getInputMap();
                if (mapU != null){
                    mapU.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,false), "pressed");
                    mapU.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,true), "released");
                }
                break;
        }
    }
    // Handler for window closing event
    public void windowClosing(WindowEvent event)
    {
        closeUserMaint();
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