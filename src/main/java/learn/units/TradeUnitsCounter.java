package learn.units;

import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class TradeUnitsCounter {

    private String id;
    private int currentUnitsCount = 0;

    public void inc() {
        currentUnitsCount++;
    }

    public void dec() {
        currentUnitsCount--;
    }
}
