/*
	Chapter 10:	The PasswordUsedException Class
	Filename:	PasswordUsedException.java
	Purpose:	To provide an exception for passwords recently used
*/

public class PasswordUsedException extends PasswordException
{
	public PasswordUsedException()
	{
        super("Password recently used.");
	}

	public PasswordUsedException(String msg)
	{
        super(msg);
	}

	public String usage()
	{
		return new String("This password cannot be reused at this time.");
    }
}