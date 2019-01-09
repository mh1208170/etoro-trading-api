package learn.monitoring;

import learn.monitoring.zuulu.ZuluPosition;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Getter
@Setter
@NoArgsConstructor
public abstract class AbstractPortfolio {

    protected String id;

    protected double factor = 1d;

    protected int skipFactor = 0;
    protected int skipCounter = skipFactor;

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": " + id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
