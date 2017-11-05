/*
	Chapter 10:	The PasswordException Class
	Purpose:	To provide an abstract base class for password exceptions
*/

public abstract class PasswordException extends Exception
{
	public PasswordException()
	{
	   super("Password exception");
}

	public PasswordException(String msg)
	{
	   super(msg);
	}

	public abstract String usage();
}