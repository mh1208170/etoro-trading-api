package learn.monitoring.zuulu;

import learn.monitoring.Portfolio;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class ZuluPortfolio implements Portfolio {

    private String id;

    Map<String, ZuluPosition> positionsMap = new HashMap<>();

    public ZuluPortfolio(String id) {
        this.id = id;
    }

    public void addToPosMap(ZuluPosition pos) {
        positionsMap.put(pos.getId(), pos);
    }
}
