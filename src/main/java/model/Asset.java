package model;


public class Asset {

    private String name;
    private String invested;
    private String profit;


    public Asset(String name, String invested, String profit) {
        this.name = name;
        this.invested = invested;
        this.profit = profit;
    }

    @Override
    public String toString() {

        return String.format("------- %s %s %s ------\n", name, invested, profit);
    }
}
