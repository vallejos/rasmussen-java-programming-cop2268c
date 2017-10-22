// driver class for creating Person Objects

import java.util.*;

public class PersonDemo
{
   public static void main(String[] args)
   {
      // create a Scanner Object
      Scanner input = new Scanner(System.in);
      
      // create an empty ArrayList to hold Person objects
      ArrayList<Person> people = new ArrayList<Person>();
      
      // variables
      String name;
      int age, num;
      
      // Ask the user for the number of entries
      System.out.print("Enter the number of entries: ");
      num = input.nextInt();
      
      while(num < 1)
      {
          // Ask the user for the number of entries
          System.out.print("Enter the number of entries: ");
          num = input.nextInt(); 
      }
      
      // use a for loop to populate the arrayList
      for(int i = 0; i < num; i++)
      {
         // ask the user for a name
         System.out.print("Enter a Person's name: ");
         name = input.next();
         
         // ask the user for an age
         System.out.print("Enter " + name + "'s age: ");
         age = input.nextInt();
         
         // create an object of type Person
         Person p = new Person(name, age); // creates an instance of the Person Class using the name and age entered
         
         // add the the object to the ArrayList
         people.add(p);
      }
      
      // display the Person's information
      for(int i = 0; i < people.size(); i++)
      {
         System.out.println(people.get(i).display());
      }
   }
}