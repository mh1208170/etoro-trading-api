package learn.portfolio;


public class CopiedPortfolio {

    private String name;
    private String invested;
    private String profit;


    public CopiedPortfolio(String name, String invested, String profit) {
        this.name = name;
        this.invested = invested;
        this.profit = profit;
    }

    @Override
    public String toString() {

        return String.format("------- %s %s %s ------\n", name, invested, profit);
    }

    public enum AssetType {
        Stock,
    }
}
