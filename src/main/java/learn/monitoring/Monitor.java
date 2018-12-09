package learn.monitoring;


public interface Monitor {
    void onClosePosition(Position p, String trader) throws InterruptedException;
    void onOpenNewPosition(Position p, String trader) throws InterruptedException;
    void scan() throws InterruptedException;
}
