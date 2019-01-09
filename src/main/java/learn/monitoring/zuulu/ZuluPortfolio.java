package learn.monitoring.zuulu;

import learn.monitoring.AbstractPortfolio;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
@NoArgsConstructor
public class ZuluPortfolio extends AbstractPortfolio {

    protected Map<String, ZuluPosition> positionsMap = new ConcurrentHashMap<>();

    public ZuluPortfolio(String id) {
        this.id = id;
    }

}
