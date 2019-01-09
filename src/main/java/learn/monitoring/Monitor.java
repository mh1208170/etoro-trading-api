package learn.monitoring;


public interface Monitor {
    boolean onClosePosition(AbstractPosition p, AbstractPortfolio trader) throws InterruptedException;
    boolean onOpenNewPosition(AbstractPosition p, AbstractPortfolio trader) throws InterruptedException;
    void scan() throws InterruptedException;
}
