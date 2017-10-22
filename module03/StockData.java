public class StockData extends Stock
{
   private String symbol;
   private String description;
   
   public StockData()
   {
      super();
   }
   
   public StockData(String s, String d)
   {
      symbol = s; 
      description = d;
   }
   
   public String getSymbol()
   {
      return symbol;
   }
   
   public String getDescription()
   {
      return description;
   }
   
   public void setSymbol(String s)
   {
      symbol = s;
   }
   
   public void setDescription(String d)
   {
      description = d;
   }
   
   public String toString()
   {
      return "Stock ID: " + getSymbol() + "\nDescription: " + getDescription() + "\n\n";
   }
}