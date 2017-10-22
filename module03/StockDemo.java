import java.util.*;
public class StockDemo
{
   public static void main(String[] args)
   {
      Scanner input = new Scanner(System.in);
      String name, description, repeat;
      ArrayList<StockData> stocks = new ArrayList<StockData>();
      
      do
      {
         System.out.print("Enter the Stock ID: ");
         name = input.nextLine();
         
         System.out.print("Enter the Stock Description: ");
         description = input.nextLine();
         
         stocks.add(new StockData(name, description));
         
         System.out.print("\nEnter 'yes' to add another stock, anything else to exit: ");
         repeat = input.nextLine();
      }while(repeat.equalsIgnoreCase("Yes"));
      
      System.out.print("Your list of Stocks: ");
      for(Stock s : stocks)
      {
         System.out.println(s.toString());
      }
      
   }
}