/**
 * Author: Leonardo Vallejos <leonardofabian.hernandezvallej@smail.rasmussen.edu>
 * Date: October 29, 2017
 * Purpose: Module 04 Lab Assignment for COP2268C Java Programming course at Rasmussen College.
 * Description: Client class.
 */

public class Client
{
    // fields
    private int id;  // id
    private String firstName;  // first name
    private String lastName;   // last name 
    private double balance; // account balance 

    /**
     * Constructor
     */
    public Client()
    {
        id = 0;
        firstName = "";
        lastName = "";
        balance = 0.0;
    }

    /**
     * Constructor
     * @param _id
     * @param _firstName
     * @param _lastName
     * @param _balance 
     */
    public Client(int _id, String _firstName, String _lastName, double _balance)
    {
        id = _id;
        firstName = _firstName;
        lastName = _lastName;
        balance = _balance;
    }

    /**
     * returns id
     * @return 
     */
    public int getId()
    {
        return id;
    }

    /**
     * returns first name
     * @return 
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * returns last name
     * @return 
     */
    public String getLastName()
    {
        return lastName;
    }

    /**
     * returns balance
     * @return 
     */
    public double getBalance()
    {
        return balance;
    }

    /**
     * sets id
     * @param _id 
     */
    public void setId(int _id)
    {
        id = id;
    }

    /**
     * sets first name
     * @param _firstName 
     */
    public void setFirstName(String _firstName)
    {
       firstName = _firstName;
    }

    /**
     * sets last name
     * @param _lastName 
     */
    public void setLastName(String _lastName)
    {
       lastName = _lastName;
    }

    /**
     * sets balance
     * @param _balance 
     */
    public void setBalance(double _balance)
    {
        balance = _balance;
    }

} // end class
