package model.portfolio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Position {
    String name;
    String info;
    BigDecimal ammount;
    BigDecimal units;
    BigDecimal open;
    LocalDateTime openTime;
    BigDecimal close;
    LocalDateTime closeTime;
    BigDecimal sl;
    BigDecimal tp;
    BigDecimal profitPr;
    BigDecimal profit$;

    public Position(String s, String s1) {
        this.name = s;
        this.info = s1;
    }

    @Override
    public String toString() {

        return String.format("------- %s %s ------\n", name, info);
    }
}

