/*
Chapter 10:	The PasswordInternalException Class
Filename:	PasswordInternalException.java
Purpose:	To provide an exception when an error occurs in the
collection used for maintaining the password history.
Such an error never should occur.
*/

public final class PasswordInternalException extends PasswordException
{
    public PasswordInternalException(String msg)
    {
        super(msg);
    }
    
    public String usage()
    {
        return new String("Internal error in the collection "+
                "containing the password history. This never should occur.");
    }
}