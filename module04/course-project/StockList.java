import java.util.*;

public class StockList {
    // private fields
    ArrayList<StockData> stocks;

    /**
     * Constructor
     */
    public StockList()
    {
        stocks = new ArrayList<StockData>();
    }

    /**
     * Adds a stock to the list
     * @param _stockData
     */
    public void add(StockData _stockData)
    {
        stocks.add(_stockData);
    }

    /**
     * Pre-loads stock information
     */
    public void preloadStocks()
    {
        stocks.add(new StockData("GOOG", "Google"));
        stocks.add(new StockData("FB", "Facebook"));
        stocks.add(new StockData("GDDY", "GoDaddy Inc."));
        stocks.add(new StockData("YELP", "Yelp Inc."));
    }

    /**
     * Prints the list of all stocks in the array
     */
    public void printAll()
    {
        for(Stock stock : stocks)
        {
            System.out.println(stock.toString());
        }
    }
}
