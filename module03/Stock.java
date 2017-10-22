abstract class Stock
{
   // fields
   private String symbol;
   private String description;
   
   // constructors
   public Stock()
   {
      symbol = "";
      description = "";
   }
   
   public Stock(String s, String d)
   {
      symbol = s;
      description = d;
   }
   
   // abstract method stubs
   public abstract String getSymbol();
   public abstract String getDescription();
   public abstract void setSymbol(String s);
   public abstract void setDescription(String d);
}