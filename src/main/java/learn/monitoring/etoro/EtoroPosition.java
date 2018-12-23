package learn.monitoring.etoro;

import learn.monitoring.Position;
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
public class EtoroPosition implements Position {

    String posId;
    String type;
    String name;
    String instrumentId;
    BigDecimal ammount;
    String leverage;
    Date openTime;
    BigDecimal sl;
    BigDecimal tp;
    String etoroRef;


    @Override
    public String toString() {
        return String.format("------- %s %s %s ------\n", name, type, ammount);
    }
}

