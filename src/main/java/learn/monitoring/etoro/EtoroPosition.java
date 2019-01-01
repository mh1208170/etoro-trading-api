package learn.monitoring.etoro;

import learn.monitoring.AbstractPosition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class EtoroPosition extends AbstractPosition {

    private String name;
    private String instrumentId;
    private BigDecimal ammount;
    private BigDecimal openRate;
    private String leverage;
    private Date openTime;

    @Override
    public String toString() {
        return String.format("Etoro Position: %s %s %s %s", id, instrumentId, tradeType, leverage);
    }
}

