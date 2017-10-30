
abstract class Stock {
    // private fields
    private String symbol;
    private String description;

    /**
     * Constructor
     */
    public Stock()
    {
        symbol = "";
        description = "";
    }

    /**
     * Constructor
     * @param _symbol
     * @param _description
     */
    public Stock(String _symbol, String _description)
    {
        symbol = _symbol;
        description = _description;
    }

    // abstract methods
    public abstract String getSymbol();
    public abstract String getDescription();
    public abstract void setSymbol(String _symbol);
    public abstract void setDescription(String _description);

}
