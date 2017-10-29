public class Client
{
   // fields
     private String idNumber;  // id number
     private String firstName;  // first name
     private String lastName;   // last name 
     private double accountBalance; // account balance 
   
   // constructors
      public Client()
      {
         idNumber = "";
         firstName = "";
         lastName = "";
         accountBalance = 0.0;
      }
      
      public Client(String i, String f, String l, double a)
      {
         idNumber = i;
         firstName = f;
         lastName = l;
         accountBalance = a;
      }
   
   // accessors - getters
      public String getIDNumber()
      {
         return idNumber;
      }
      
      public String getFirstName()
      {
         return firstName;
      }
      
      public String getLastName()
      {
         return lastName;
      }
      
      public double getAccountBalance()
      {
         return accountBalance;
      }
   
   // mutators - setters
      public void setIDNumber(String i)
      {
         idNumber = i;
      }
      
      public void setFirstName(String f)
      {
         firstName = f;
      }
      
      public void setLastName(String l)
      {
         lastName = l;
      }
      
      public void setAccountBalance(double a)
      {
         accountBalance = a;
      }
   
   // other methods as needed
      public String toString()
      {
         return "ID Number: \t" + getIDNumber() +
                    "\nName: \t\t" + getFirstName() + " " + getLastName() +
                    "\nAccount Balance: $" + getAccountBalance() +"\n\n";
      }

} // end Client class