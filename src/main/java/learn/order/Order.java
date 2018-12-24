package learn.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private String name;
    private String type;
    private BigDecimal value;
    private BigDecimal open;
    private Integer leverage;

    @Override
    public String toString() {
        return String.format("Order: %s %s %s %s", name, type, value, leverage);
    }
}
