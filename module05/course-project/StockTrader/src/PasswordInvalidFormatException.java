/*
Chapter 10:	The PasswordInvalidFormatException Class
Filename:	PasswordInvalidFormatException.java
Purpose:	To provide an exception for improperly formatted passwords
*/

public class PasswordInvalidFormatException extends PasswordInvalidException
{
    public PasswordInvalidFormatException()
    {
        super("Invalid password format.");
    }
    
    public PasswordInvalidFormatException(String msg)
    {
        super(msg);
    }
    
    public String usage()
    {
        return new String("This password is not formatted properly.");
    }
}