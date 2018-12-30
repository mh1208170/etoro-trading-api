package learn.monitoring.zuulu;

import learn.monitoring.AbstractPosition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ZuluPosition extends AbstractPosition {

    private String currencyName;
    private Double stdLotds;
    private Double entryRate;

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    //TODO
    public boolean equals(Object obj) {
        ZuluPosition zp = (ZuluPosition) obj;
        return id.equals(zp.getId());
    }

    @Override
    public String toString() {
        return String.format("ZuluPosition: %s %s %s %f",id, currencyName, tradeType, entryRate);
    }
}
