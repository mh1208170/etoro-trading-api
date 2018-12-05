package learn.scanning.zuulu;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ZuluPortfolio {

    private String tradersId;

    List<ZuluPosition> positions;

    Map<String, ZuluPosition> positionsMap = new HashMap<>();

    public ZuluPortfolio(String id) {
        this.tradersId = id;
    }


    public void addToPosMap(ZuluPosition pos) {
        positionsMap.put(pos.getId(), pos);
    }
}
