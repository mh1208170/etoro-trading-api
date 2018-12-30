package learn.user.units;

import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class TradeUnitsCounter {

    private String id;
    private Double stdLotSize = 200d;
    private String stdLeverage = "20";
    private int currentUnitsCount = 0;

    public void inc() {
        currentUnitsCount++;
    }

    public void dec() {
        currentUnitsCount--;
    }
}
