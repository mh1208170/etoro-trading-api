package learn.scanning.zuulu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ZuluPosition {
    private String id;
    private String currencyName;
    private String tradeType;
    private Date dateTime;
    private Double stdLotds;

    @Override
    public int hashCode() {
        return Integer.parseInt(id);
    }

    @Override
    //TODO
    public boolean equals(Object obj) {
        ZuluPosition zp = (ZuluPosition) obj;
        return id.equals(zp.getId());
    }
}
