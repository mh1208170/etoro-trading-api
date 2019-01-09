package learn.monitoring;

public class PositionError {
    String id;
    AbstractPosition position;

    public PositionError(String id, AbstractPosition position) {
        this.id = id;
        this.position = position;
    }
}
