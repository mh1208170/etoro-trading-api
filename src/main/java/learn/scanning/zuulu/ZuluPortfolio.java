package learn.scanning.zuulu;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ZuluPortfolio {

    private String tradersId;

    List<ZuluPosition> positions;

    public ZuluPortfolio(String id) {
        this.tradersId = id;
    }


}
