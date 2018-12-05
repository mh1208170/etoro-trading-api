package learn.trades;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EtoroTrade {

    private String tradeId;
    private String info;

    public EtoroTrade(String positionId, String info) {
        this.tradeId = positionId;
        this.info = info;
    }
}
