package learn.monitoring;


public interface Monitor {
    boolean onClosePosition(AbstractPosition p, String trader) throws InterruptedException;
    boolean onOpenNewPosition(AbstractPosition p, String trader) throws InterruptedException;
    void scan() throws InterruptedException;
}
