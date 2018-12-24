package learn.monitoring.zuulu;

import learn.monitoring.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ZuluPosition implements Position {

    private String id;
    private String currencyName;
    private String tradeType;
    private Date dateTime;
    private Double stdLotds;
    private String etoroRef;
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
