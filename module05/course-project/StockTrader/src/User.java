/*
Chapter 11:	The User Class
Filename:	User.java
Purpose:	To provide a User class for the the Stock Tracker application
*/

public class User
{
    private String userID;
    private String lastName;
    private String firstName;
    private Password pswd;
    private boolean admin;
    
    public User(String aUserID, String aFirstName, String aLastName,
            String password, boolean autoExpirePswd, int pswdUses, boolean isAdmin)
            throws PasswordException
    {
        userID = new String(aUserID);
        firstName = new String(aFirstName);
        lastName = new String(aLastName);
        if(pswdUses > 0)
            pswd = new Password(password,pswdUses,autoExpirePswd);
        else
            pswd = new Password(password,autoExpirePswd);
        admin = isAdmin;
    }
    
    public User(String aUserID, String aFirstName, String aLastName,
            Password password, boolean isAdmin)
    {
        userID = new String(aUserID);
        firstName = new String(aFirstName);
        lastName = new String(aLastName);
        pswd = password;
        admin = isAdmin;
    }
    
    
    public String getUserID()
    {
        return new String(userID);
    }
    
    public String getFirstName()
    {
        return new String(firstName);
    }
    
    public void setFirstName(String aFirstName)
    {
        firstName = new String(aFirstName);
    }
    
    public String getLastName()
    {
        return new String(lastName);
    }
    
    public void setLastName(String aLastName)
    {
        lastName = new String(aLastName);
    }
    
    public String getName()
    {
        return new String(firstName+" "+lastName);
    }
    
    public boolean isAdmin()
    {
        return admin;
    }
    
    public void setAdmin(boolean isAdmin)
    {
        admin = isAdmin;
    }
    
    public Password getPassword()
    {
        return pswd;
    }
    
    public boolean pswdAutoExpires()
    {
        return pswd.getAutoExpires();
    }
    
    public void setAutoExpires(boolean autoExpires)
    {
        pswd.setAutoExpires(autoExpires);
    }
    
    public boolean pswdIsExpiring()
    {
        return pswd.isExpiring();
    }
    
    public boolean pswdIsExpired()
    {
        return pswd.isExpired();
    }
    
    public void expirePassword()
    {
        pswd.setExpired(true);
    }
    
    public int getPswdUses()
    {
        return pswd.getRemainingUses();
    }
    
    public void validate(String password) throws PasswordException
    {
        pswd.validate(password);
    }
    
    public void changePassword(String oldPassword, String newPassword) throws PasswordException
    {
        try
        {
            pswd.validate(oldPassword);
        }
        catch(PasswordExpiredException ex)
        {}
        pswd.set(newPassword);
    }
    
    public void adminChangePassword(String newPassword) throws PasswordException
    {
        pswd.set(newPassword);	// set a new password
        pswd.setExpired(true);	// but make it expired so user must reset
    }
}