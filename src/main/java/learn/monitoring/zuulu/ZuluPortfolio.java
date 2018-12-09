package learn.monitoring.zuulu;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ZuluPortfolio {

    private String id;

    List<ZuluPosition> positions;

    Map<String, ZuluPosition> positionsMap = new HashMap<>();

    public ZuluPortfolio(String id) {
        this.id = id;
    }


    public void addToPosMap(ZuluPosition pos) {
        positionsMap.put(pos.getId(), pos);
    }
}
