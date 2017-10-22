/*
   Class definition Structure
   
   (access specifier) class ClassName
   {
      // fields
      
      // constructor(s)
      
      // accessors
      
      // mutators
      
      // other methods as needed
   }
   
*/

// class definition for a person
public class Person
{
   // fields
      // private fields can only be accessed once an instance of the class is created
      // public fields can be accessed with or without an instance of the class being created
      private String name; // field to hold the Person's name
      private int age; // field to hold the Person's age
   
   // constructors
      // methods that will construct the instances of the object by initializing the fields
      // ALWAYS have the same name as the class
      public Person()
      {
         // default no argument constructor
         name = "";
         age = 0;
      }
      
      // overloaded constructor will initialize the values of the fields to the values of the parameters
       public Person(String n, int a)
      {
         name = n; // also can use setName(n);
         age = a;
      }
   
   // accessors
      // "getter" methods - used to retrieve the value of the private fields when called
      public String getName()
      {
         return name;
      }
      
      public int getAge()
      {
         return age;
      } 
   
   // mutators
      // setter methods
       public void setName(String n)
      {
         name = n;
      }
      
       public void setAge(int a)
      {
         age = a;
      }
   
   // other methods
      public String display()
      {
         return "\nName: " + getName() + "\nAge: " + getAge() + "\n\n";
      }
}