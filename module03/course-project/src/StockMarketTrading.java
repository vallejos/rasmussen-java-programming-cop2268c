import java.util.*;

public class StockMarketTrading
{

    /**
     * Asks for Stock data and adds the stock to the list
     * @param stocks
     */
    public static void addStock(StockList stocks)
    {
        Scanner input = new Scanner(System.in);
        String name, description;

        System.out.print("Enter the Stock ID: ");
        name = input.nextLine();

        System.out.print("Enter the Stock Description: ");
        description = input.nextLine();

        // add the new stock to the array
        stocks.add(new StockData(name, description));

        System.out.println("Stock added.");
    }

    /**
     * main function
     * @param args
     */
    public static void main(String[] args)
    {
        // define some variables
        Scanner input = new Scanner(System.in);
        String repeat;
        StockList stocks = new StockList();

        // preload stocks
        stocks.preloadStocks();
        System.out.println("\nSome Stocks have been preloaded.");

        // ask to add more stocks or not
        System.out.print("Add more? <yes/no>: ");
        String addMore = input.nextLine();

        if (addMore.equalsIgnoreCase("yes"))
        {
            do
            {
                // ask for new stock data
                addStock(stocks);

                System.out.print("Add more? <yes/no>: ");
                repeat = input.nextLine();

            } while (repeat.equalsIgnoreCase("yes"));
        }

        // display loaded stock data
        System.out.println("\n---------------------");
        System.out.println("List of Stocks");
        System.out.println("---------------------");

        stocks.printAll();

    }

}
