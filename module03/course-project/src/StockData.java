
public class StockData extends Stock {
    private String symbol;
    private String description;

    /**
     * Constructor
     */
    public StockData()
    {
        super();
    }

    /**
     * Constructor
     * @param _symbol
     * @param _description
     */
    public StockData(String _symbol, String _description)
    {
        symbol = _symbol;
        description = _description;
    }

    /**
     * Returns stock symbol
     * @return {string}
     */
    public String getSymbol()
    {
        return symbol;
    }

    /**
     * Returns stock description
     * @return {string}
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Sets stock symbol
     * @param _symbol
     */
    public void setSymbol(String _symbol)
    {
        symbol = _symbol;
    }

    /**
     * Sets stock description
     * @param _description
     */
    public void setDescription(String _description)
    {
        description = _description;
    }

    /**
     * Returns stock data as a string
     * @return {string}
     */
    public String toString()
    {
        return "\nStock ID: " + getSymbol() +
                "\nDescription: " + getDescription() +
                "\n";
    }

}
