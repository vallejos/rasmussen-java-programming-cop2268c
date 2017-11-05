/*
	Chapter 10:	The User Class
	Filename:	User.java
	Purpose:	To provide a User class to test the Password class
*/

public class User
{
    private String name;
    private Password pswd;

    public User(String aName, String password) throws PasswordException
    {
		name = new String(aName);
		pswd = new Password(password);
	}

    public User(String aName, String password, int pswdUses) throws PasswordException
    {
		name = new String(aName);
		pswd = new Password(password,pswdUses);
	}

    public User(String aName, String password, boolean autoExpirePswd) throws PasswordException
    {
		name = new String(aName);
		pswd = new Password(password,autoExpirePswd);
	}

	public String getName()
	{
		return new String(name);
	}

	public boolean pswdAutoExpires()
	{
		return pswd.getAutoExpires();
	}

	public boolean pswdIsExpiring()
	{
		return pswd.isExpiring();
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
}